package org.jacpfx.vertx.spring.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by amo on 26.09.14.
 */
@Configuration
@ComponentScan(basePackages = {"org.jacpfx.vertx.spring.services","org.jacpfx.vertx.spring.repository"})
public class SpringConfiguration {
}
