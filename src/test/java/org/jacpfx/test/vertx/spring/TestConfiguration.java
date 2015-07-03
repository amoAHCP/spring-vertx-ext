package org.jacpfx.test.vertx.spring;

import org.jacpfx.test.vertx.spring.factory.SpringTestVerticle;
import org.jacpfx.test.vertx.spring.factory.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @Bean
    public TestService testService() {
        return new TestService();
    }

    @Bean
    public SpringTestVerticle springTestVerticle() {
        return new SpringTestVerticle();
    }

}