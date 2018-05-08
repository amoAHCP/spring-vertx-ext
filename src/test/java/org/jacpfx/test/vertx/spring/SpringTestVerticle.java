package org.jacpfx.test.vertx.spring;

import static org.junit.Assert.assertNotNull;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;

import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SpringVerticle(springConfig = TestConfiguration.class)
public class SpringTestVerticle extends AbstractVerticle {

    @Autowired
    private TestService bean;

    @Autowired
    public ApplicationContext context;

    @Override
    public void start() {
        System.out.println("Started");
        assertNotNull(bean);
        assertNotNull(vertx);

        vertx.createHttpServer(new HttpServerOptions().setPort(8089)).requestHandler(rc -> {
            rc.response().end("You requested: " + rc.path());
        }).listen();

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stoped");
    }

    public TestService getBean() {
        return bean;
    }

    public ApplicationContext getSpringContext() {
        return context;
    }
}
