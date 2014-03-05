package org.jacpfx.vertx.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;

/**
 * Created by amo on 04.03.14.
 */
public class SpringVerticleInitialisationPostProcessor implements BeanPostProcessor {

    private final Container container;
    private final Vertx vertx;

    public SpringVerticleInitialisationPostProcessor(final Vertx vertx, final Container container) {
        this.vertx = vertx;
        this.container = container;
    }



    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        // set vertex

        if(Verticle.class.isAssignableFrom(o.getClass())){
            final Verticle springVerticle = Verticle.class.cast(o);
            springVerticle.setContainer(container);
            springVerticle.setVertx(vertx);
            return springVerticle;
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {

        return o;
    }
}


