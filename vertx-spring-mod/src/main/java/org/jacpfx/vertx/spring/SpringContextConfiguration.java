package org.jacpfx.vertx.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * This Spring configuration adds a verticle lifecycle aspect to ensure that all spring verticle closes the spring context when undeployed.
 * Created by Andy Moncsek on 05.03.14.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class SpringContextConfiguration {

    @Bean
    public VertxLifecycleAspect vertxLifecycleAspect() {
        return new VertxLifecycleAspect();
    }

}
