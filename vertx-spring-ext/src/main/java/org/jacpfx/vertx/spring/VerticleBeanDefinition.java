package org.jacpfx.vertx.spring;

import org.springframework.beans.factory.support.GenericBeanDefinition;

public class VerticleBeanDefinition extends GenericBeanDefinition {

    private static final long serialVersionUID = 7064128536375703454L;

    public VerticleBeanDefinition(Class<?> currentSpringVerticleClass) {
        this.setBeanClass(currentSpringVerticleClass);
        this.setBeanClassName(currentSpringVerticleClass.getCanonicalName());
        this.setDescription("Bean for the verticle class {" + currentSpringVerticleClass + "}");
    }

}
