package org.jacpfx.test.vertx.spring;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectionTestConfiguration {

    @Autowired Vertx vertx;


    @Bean
    public InjectionTestService testService() {
        return new InjectionTestService();
    }



}