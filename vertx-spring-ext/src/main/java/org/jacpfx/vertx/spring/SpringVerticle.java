
/*
 * Copyright (C) 2014
 *
 * [SpringVerticle.java]
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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * With this annotation you declare a Verticle as a spring verticle. Define the configuration class for each verticle.
 * You can also reuse spring configurations for different verticles. The autoremoveOtherSpringVerticles defines if you Spring verticle should be the only one in spring context.
 * If you set it to false the other Spring Verticles are simple Spring beans with references to Vertx context.
 * @author Andy Moncsek , Johannes Schüth
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringVerticle {
    /**
     * Defines the Spring Configuration class for the Spring Verticle
     * @return a Spring configuration class
     */
    Class<?> springConfig() default Object.class;

    /**
     *  Defines if other Spring verticles in the same spring context will be removed.
     * @return true if other Spring verticles should be removed from current context
     */
    boolean autoremoveOtherSpringVerticles() default true;
}
