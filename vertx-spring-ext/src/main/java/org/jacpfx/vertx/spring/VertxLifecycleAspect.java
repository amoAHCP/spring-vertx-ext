
/*
 * Copyright (C) 2014
 *
 * [VertxLifecycleAspect.java]
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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.logging.Logger;

/**
 * This aspect closes the spring context in case the spring verticle is stopped
 */
@Aspect
public class VertxLifecycleAspect {

    private static Logger LOG = Logger.getLogger(VertxLifecycleAspect.class.getName());

    @Autowired
    public ApplicationContext context;

    /**
     * When a verticle will be stopped the stop() method will be executed.
     * In this case check if there is a running spring context, if so close it.
     * @param joinPoint the verticle stop method
     */
    @After(value = "execution(* io.vertx.core.Verticle+.stop())")
    public void afterStop(JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();
        LOG.info("STOP spring verticle");
        if (target.getClass().isAnnotationPresent(SpringVerticle.class)) {
            if (AnnotationConfigApplicationContext.class.isAssignableFrom(context.getClass())) {
                final ApplicationContext parent = AnnotationConfigApplicationContext.class.cast(context).getParent();
                if (parent == null) {
                    AnnotationConfigApplicationContext.class.cast(context).stop();
                } else {
                    if (GenericApplicationContext.class.isAssignableFrom(parent.getClass())) {
                        GenericApplicationContext.class.cast(parent).stop();
                    }
                }
            }
        }

    }

    @After(value = "execution(* io.vertx.core.Verticle+.start(..))")
    public void afterStart(JoinPoint joinPoint) {
        LOG.info("START spring verticle");
    }
}
