package org.jacpfx.test.vertx.spring;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import org.jacpfx.test.vertx.spring.factory.InjectionTestService;
import org.jacpfx.test.vertx.spring.factory.SpringInjectionTestVerticle;
import org.jacpfx.test.vertx.spring.factory.SpringTestVerticle;
import org.jacpfx.test.vertx.spring.factory.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class InjectionTestConfiguration {

    @Autowired Vertx vertx;


    @Bean
    public InjectionTestService testService() {
        return new InjectionTestService();
    }



}