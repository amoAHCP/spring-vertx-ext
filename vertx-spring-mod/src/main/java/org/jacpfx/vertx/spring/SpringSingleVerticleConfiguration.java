package org.jacpfx.vertx.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.vertx.java.core.logging.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Removes all Spring verticles except the one that has been started.
 * This should avoid missconfigurations because all other spring verticles in the context are not started as a verticle.
 * Each spring deployed verticle creates his own spring context and must be deployed on it's own.
 * Created by Andy Moncsek on 12.03.14.
 */
public class SpringSingleVerticleConfiguration implements BeanFactoryPostProcessor {
    private final Class currentSpringVerticleClass;
    public SpringSingleVerticleConfiguration(final Class currentSpringVerticleClass) {
        this.currentSpringVerticleClass = currentSpringVerticleClass;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //final Map<String,Object> allSpringVerticle = beanFactory.getBeansWithAnnotation(SpringVertx.class);
        final String[] verticleBeanNames = beanFactory.getBeanNamesForAnnotation(SpringVertx.class);
        if(verticleBeanNames.length>1) {
            final List<String> beansToRemove = new ArrayList<>();
            for(String name: verticleBeanNames) {
                final Class<?> verticleClass = beanFactory.getType(name);
                if(currentSpringVerticleClass.equals(verticleClass)) continue;
                beansToRemove.add(name);
            }

            for(final String name: beansToRemove) {
                ((BeanDefinitionRegistry) beanFactory).removeBeanDefinition(name);
            }
        }
    }
}
