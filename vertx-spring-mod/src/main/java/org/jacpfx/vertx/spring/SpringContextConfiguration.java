package org.jacpfx.vertx.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by Andy Moncsek on 05.03.14.
 */
@Configuration
@EnableAspectJAutoProxy
public class SpringContextConfiguration {

    @Bean
    public VertxLifecycleAspect vertxLifecycleAspect() {
        return new VertxLifecycleAspect();
    }
}
