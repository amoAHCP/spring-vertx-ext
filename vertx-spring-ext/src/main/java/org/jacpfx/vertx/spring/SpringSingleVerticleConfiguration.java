/*
 * Copyright (C) 2014
 *
 * [SpringSingleVerticleConfiguration.java]
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
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.List;


/**
 * Removes all Spring verticles except the one that has been started.
 * This should avoid missconfigurations because all other spring verticles in the context are not started as a verticle.
 * Each spring verticle creates his own spring context and must be deployed on it's own.
 * Created by Andy Moncsek on 12.03.14.
 */
public class SpringSingleVerticleConfiguration implements BeanFactoryPostProcessor {
    private final Class currentSpringVerticleClass;

    public SpringSingleVerticleConfiguration(final Class currentSpringVerticleClass) {
        this.currentSpringVerticleClass = currentSpringVerticleClass;
    }

    @Override
    /**
    * Ensure that only one spring verticle per spring context will be deployed, in case the "autoremovetherSpringVerticles" is set to true
    *
    */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // assume the annotation is present, otherwise hew would never call this
        final SpringVerticle annotation = (SpringVerticle) currentSpringVerticleClass.getAnnotation(SpringVerticle.class);
        if (annotation.autoremoveOtherSpringVerticles()) {
            final String[] verticleBeanNames = beanFactory.getBeanNamesForAnnotation(SpringVerticle.class);
            if (verticleBeanNames.length > 1) {
                final List<String> beansToRemove = getBeanNamesToRemove(verticleBeanNames, beanFactory);
                for (final String name : beansToRemove) {
                    ((BeanDefinitionRegistry) beanFactory).removeBeanDefinition(name);
                }
            }
        }

    }

    private List<String> getBeanNamesToRemove(final String[] verticleBeanNames, final ConfigurableListableBeanFactory beanFactory) {
        final List<String> beansToRemove = new ArrayList<>();
        for (final String name : verticleBeanNames) {
            final Class<?> verticleClass = beanFactory.getType(name);
            if (currentSpringVerticleClass.equals(verticleClass)) continue;
            beansToRemove.add(name);
        }
        return beansToRemove;
    }
}