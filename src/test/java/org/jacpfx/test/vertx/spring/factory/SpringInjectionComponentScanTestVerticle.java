package org.jacpfx.test.vertx.spring.factory;

import static org.junit.Assert.assertNotNull;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import org.jacpfx.test.vertx.spring.InjectionTestConfiguration;
import org.jacpfx.test.vertx.spring.SayHelloBean;
import org.jacpfx.test.vertx.spring.TestConfiguration;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SpringVerticle(springConfig = TestConfiguration.class)
public class SpringInjectionComponentScanTestVerticle extends AbstractVerticle {

    @Autowired
    private InjectionTestService bean;

    @Autowired
    public ApplicationContext context;

    @Autowired
    private SayHelloBean sayHello;


    @Override
    public void start() {
        System.out.println("Started");
        assertNotNull(bean);
        assertNotNull(bean.vertx());
        assertNotNull(vertx);
        assertNotNull(sayHello);
        System.out.println(sayHello.sayHello());
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
