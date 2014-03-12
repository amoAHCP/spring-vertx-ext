package org.jacpfx.vertx.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Andy Moncsek on 05.03.14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringVertx {
    /**
     * Defines the Spring Configuration class for the Spring Verticle
     * @return a Spring configuration class
     */
    public Class<?> springConfig();

    /**
     *  Defines if other Spring verticles in the same spring context will be removed.
     * @return true if other Spring verticles should be removed from current context
     */
    public boolean autoremoveOtherSpringVerticles() default true;
}
