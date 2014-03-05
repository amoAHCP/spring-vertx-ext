package org.jacpfx.vertx.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jochen Mader
 */
@Configuration
@ComponentScan
public class TestConfiguration {

    @Bean
    public HttpVerticle httpVerticle() {
        return new HttpVerticle();
    }

}
