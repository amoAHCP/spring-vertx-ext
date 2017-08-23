
/*
 * Copyright (C) 2014
 *
 * [SpringVerticleFactory.java]
 * vertx-spring-mod (https://github.com/amoAHCP/spring-vertx-mod)
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.jacpfx.vertx.spring;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.verticle.CompilingClassLoader;
import io.vertx.core.spi.VerticleFactory;
import java.security.InvalidParameterException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * A Vertx Spring factory that creates a spring verticle and loads for each verticle instance a spring context
 * @author Andy Moncsek , Johannes Schüth
 *
 */
public class SpringVerticleFactory implements VerticleFactory {
    private Vertx vertx;

    public static final String PREFIX = "java-spring";
    public static final String SUFFIX = ".java";

    private static GenericApplicationContext parentContext = null;

    @Override
    public String prefix() {
        return PREFIX;
    }

    
    @Override
    public void init(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public synchronized Verticle createVerticle(final String verticleName, final ClassLoader classLoader) throws Exception {
        final String className = VerticleFactory.removePrefix(verticleName);
        Class<?> clazz;
        if (className.endsWith(SUFFIX)) {
            CompilingClassLoader compilingLoader = new CompilingClassLoader(classLoader, className);
            clazz = compilingLoader.loadClass(compilingLoader.resolveMainClassName());
        } else {
            clazz = classLoader.loadClass(className);
        }
        return createVerticle(clazz, classLoader);
    }

    private Verticle createVerticle(final Class<?> clazz, ClassLoader classLoader) throws Exception {

        if (clazz.isAnnotationPresent(SpringVerticle.class)) {
            return createSpringVerticle(clazz, classLoader);
        } else if (Verticle.class.isAssignableFrom(clazz)) {
            // init a non spring verticle, but this should not happen
            final Verticle verticle = Verticle.class.cast(clazz.newInstance());
            verticle.init(vertx, vertx.getOrCreateContext());
            return verticle;
        }

        return null;
    }

    private Verticle createSpringVerticle(final Class<?> currentVerticleClass, ClassLoader classLoader) {
        final SpringVerticle annotation = currentVerticleClass.getAnnotation(SpringVerticle.class);
        final Class<?> springConfigClass = annotation.springConfig();
        
        // Create the parent context  
        final GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.setClassLoader(classLoader);
        if (parentContext != null) {
            genericApplicationContext.setParent(parentContext);
        }
        genericApplicationContext.refresh();
        genericApplicationContext.start();

        // 1. Create a new context for each verticle and use the specified spring configuration class if possible
        AnnotationConfigApplicationContext annotationConfigApplicationContext = getAnnotationConfigApplicationContext(
            springConfigClass, genericApplicationContext);

        // 2. Register the Vertx instance as a singleton in spring context
        annotationConfigApplicationContext.getBeanFactory().registerSingleton(vertx.getClass().getSimpleName(), vertx);

        // 3. Register a bean definition for this verticle
        annotationConfigApplicationContext.registerBeanDefinition(currentVerticleClass.getSimpleName(), new VerticleBeanDefinition(currentVerticleClass));
        
        // 4. Add a bean factory post processor to avoid configuration issues
        addPostprocessorAndUpdateContext(currentVerticleClass, annotationConfigApplicationContext);
        
        // 5. Return the verticle by fetching the bean from the context
        return (Verticle) annotationConfigApplicationContext.getBeanFactory().getBean(currentVerticleClass.getSimpleName());
    }


    /**
     * Initialize a Spring Context for given Verticle instance. A Verticle MUST be annotated with {@link SpringVerticle}
     * @param verticle The Verticle Instance where to start the Spring Context
     */
    public static void initSpring(AbstractVerticle verticle) {
        final Class<?> currentVerticleClass = verticle.getClass();
        final Vertx vertx = verticle.getVertx();
        if(!currentVerticleClass.isAnnotationPresent(SpringVerticle.class)) {
            throw new InvalidParameterException("no @SpringVerticle annotation found");
        }
        final SpringVerticle annotation = currentVerticleClass.getAnnotation(SpringVerticle.class);
        final Class<?> springConfigClass = annotation.springConfig();

        // Create the parent context
        final GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.setClassLoader(vertx.getClass().getClassLoader());
        if (parentContext != null) {
            genericApplicationContext.setParent(parentContext);
        }
        genericApplicationContext.refresh();
        genericApplicationContext.start();

        // 1. Create a new context for each verticle and use the specified spring configuration class if possible
        AnnotationConfigApplicationContext annotationConfigApplicationContext = getAnnotationConfigApplicationContext(
            springConfigClass, genericApplicationContext);

        // 2. Register the Vertx instance as a singleton in spring context
        annotationConfigApplicationContext.getBeanFactory().registerSingleton(vertx.getClass().getSimpleName(), vertx);

        // 3. Register a bean definition for this verticle
        annotationConfigApplicationContext.getBeanFactory().registerSingleton(verticle.getClass().getSimpleName(), verticle);

        // 4. Add a bean factory post processor to avoid configuration issues
        addPostprocessorAndUpdateContext(currentVerticleClass, annotationConfigApplicationContext);

        // 5. perform autowiring
        annotationConfigApplicationContext.getAutowireCapableBeanFactory().autowireBean(verticle);

    }

    private static void addPostprocessorAndUpdateContext(Class<?> currentVerticleClass,
        AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        annotationConfigApplicationContext.addBeanFactoryPostProcessor(new SpringSingleVerticleConfiguration(currentVerticleClass));
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.start();
        annotationConfigApplicationContext.registerShutdownHook();
    }

    private static AnnotationConfigApplicationContext getAnnotationConfigApplicationContext(
        Class<?> springConfigClass, GenericApplicationContext genericApplicationContext) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setParent(genericApplicationContext);
        annotationConfigApplicationContext.register(SpringContextConfiguration.class, springConfigClass);
        return annotationConfigApplicationContext;
    }

    public void close() {
    }

    public static void setParentContext(GenericApplicationContext ctx) {
        parentContext = ctx;
    }

}
