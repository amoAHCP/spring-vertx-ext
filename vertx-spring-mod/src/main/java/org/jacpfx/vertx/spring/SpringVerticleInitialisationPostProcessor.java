
/*
 * Copyright (C) 2014
 *
 * [SpringVerticleInitialisationPostProcessor.java]
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


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;

/**
 * This spring bean postprocessor sets the vert.x and container reference to each spring verticle.
 * Created by Andy Moncsek on 04.03.14.
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


