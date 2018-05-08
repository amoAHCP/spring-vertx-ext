package org.jacpfx.test.vertx.spring;

import static org.junit.Assert.assertNotNull;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.jacpfx.vertx.spring.SpringVerticleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SpringVerticle(springConfig = InjectionTestConfiguration.class)
public class SpringInjectionTestVerticleStatic extends AbstractVerticle {

    @Autowired
    private InjectionTestService bean;

    @Autowired
    public ApplicationContext context;

    @Override
    public void start() {
        SpringVerticleFactory.initSpring(this);
        System.out.println("Started");
        assertNotNull(bean);
        assertNotNull(bean.vertx());
        assertNotNull(vertx);
        System.out.println("injected: "+ bean+" "+context);

        vertx.createHttpServer(new HttpServerOptions().setPort(8089)).requestHandler(rc -> {
            rc.response().end("You requested: " + rc.path());
        }).listen();

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Stoped");
    }

    public InjectionTestService getBean() {
        return bean;
    }

    public ApplicationContext getSpringContext() {
        return context;
    }
}
