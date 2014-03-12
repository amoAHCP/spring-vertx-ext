package org.jacpfx.vertx.spring;

/**
 * Created by amo on 03.03.14.
 */

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;
import org.vertx.java.platform.VerticleFactory;

/**
 * A Vertx Spring factory that creates a spring verticle and loads for each verticle instance a spring context
 *
 * @author Andy Moncsek
 */
public class SpringVerticleFactory implements VerticleFactory {
    private ClassLoader cl;
    private Vertx vertx;
    private Container container;

    @Override
    public void init(Vertx vertx, Container container, ClassLoader cl) {
        this.cl = cl;
        this.vertx = vertx;
        this.container = container;
    }

    public Verticle createVerticle(String main) throws Exception {
        if (container.logger() != null)
            container.logger().info("LOAD: " + main + " in THREAD: " + Thread.currentThread() + "  in Factory:" + this);
        Class clazz;
        try {
            clazz = cl.loadClass(main);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Maybe you are trying to register a Spring-Verticle before the context is up?", e);
        }
        if (clazz.isAnnotationPresent(SpringVertx.class)) {
            SpringVertx annotation = (SpringVertx) clazz.getAnnotation(SpringVertx.class);
            final Class<?> springConfigClass = annotation.springConfig();
            final GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
            genericApplicationContext.setClassLoader(cl);

            registerVertxBeans(genericApplicationContext.getBeanFactory());

            genericApplicationContext.refresh();
            genericApplicationContext.start();
            // TODO remove all other Spring verticles from bean context to ensure that only one spring verticle is loaded per context
            AnnotationConfigApplicationContext annotationConfigApplicationContext = createConfigContext(genericApplicationContext, springConfigClass);
            annotationConfigApplicationContext.start();
            annotationConfigApplicationContext.registerShutdownHook();

            return (Verticle) annotationConfigApplicationContext.getBeanFactory().getBean(clazz);
        } else {
            // TODO init non spring verticle
        }


        return null;
    }

    private AnnotationConfigApplicationContext createConfigContext(final GenericApplicationContext genericApplicationContext, final Class<?> springConfigClass) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setParent(genericApplicationContext);
        annotationConfigApplicationContext.register(SpringContextConfiguration.class, springConfigClass);
        annotationConfigApplicationContext.getBeanFactory().addBeanPostProcessor(new SpringVerticleInitialisationPostProcessor(this.vertx, this.container));
        annotationConfigApplicationContext.refresh();
        return annotationConfigApplicationContext;
    }


    private void registerVertxBeans(final ConfigurableListableBeanFactory beanFactory) {
        beanFactory.registerSingleton(Vertx.class.getName(), vertx);
        beanFactory.registerAlias(Vertx.class.getName(), "vertx");
        beanFactory.registerSingleton(Container.class.getName(), container);
        beanFactory.registerAlias(Container.class.getName(), "container");
        beanFactory.registerSingleton(EventBus.class.getName(), vertx.eventBus());
        beanFactory.registerAlias(EventBus.class.getName(), "eventBus");
    }


    public void reportException(Logger logger, Throwable t) {
        logger.error("Exception in Spring verticle", t);
    }

    public void close() {
    }
}
