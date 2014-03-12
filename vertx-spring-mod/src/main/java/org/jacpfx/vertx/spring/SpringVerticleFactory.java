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
        final Class currentVerticleClass = cl.loadClass(main);

        if (currentVerticleClass.isAnnotationPresent(SpringVertx.class)) {
            return createSpringVerticle(currentVerticleClass);
        } else if (Verticle.class.isAssignableFrom(currentVerticleClass)) {
            // init a non spring verticle, but this should not happen
            final Verticle verticle = Verticle.class.cast(currentVerticleClass.newInstance());
            verticle.setContainer(this.container);
            verticle.setVertx(this.vertx);
            return verticle;
        }


        return null;
    }

    private Verticle createSpringVerticle(final Class currentVerticleClass) {
        final SpringVertx annotation = (SpringVertx) currentVerticleClass.getAnnotation(SpringVertx.class);
        final Class<?> springConfigClass = annotation.springConfig();
        final GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.setClassLoader(cl);

        registerVertxBeans(genericApplicationContext.getBeanFactory());

        genericApplicationContext.refresh();
        genericApplicationContext.start();

        AnnotationConfigApplicationContext annotationConfigApplicationContext = createConfigContext(genericApplicationContext, springConfigClass,currentVerticleClass);
        annotationConfigApplicationContext.start();
        annotationConfigApplicationContext.registerShutdownHook();

        return (Verticle) annotationConfigApplicationContext.getBeanFactory().getBean(currentVerticleClass);
    }

    private AnnotationConfigApplicationContext createConfigContext(final GenericApplicationContext genericApplicationContext, final Class<?> springConfigClass, final Class currentSpringVerticleClass) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.setParent(genericApplicationContext);
        annotationConfigApplicationContext.register(SpringContextConfiguration.class, springConfigClass);
        annotationConfigApplicationContext.addBeanFactoryPostProcessor(new SpringSingleVerticleConfiguration(currentSpringVerticleClass));
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
