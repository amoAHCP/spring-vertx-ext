package org.jacpfx.test.vertx.spring;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SpringVerticle(springConfig = TestConfiguration.class)
public class SpringTestVerticle2 extends AbstractVerticle {

    @Autowired
    private SayHelloBean bean;

    @Override
    public void start() {
        System.out.println("START SpringVerticle: " + bean.sayHello() + "  THREAD: " + Thread.currentThread() + "  this:" + this);
        vertx.createHttpServer(new HttpServerOptions().setPort(8072)).requestHandler(rc -> {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> header : rc.headers().entries()) {
                sb.append(header.getKey()).append(": ").append(header.getValue()).append("\n").append(bean.sayHello());
            }
            rc.response().putHeader("content-type", "text/plain");
            rc.response().end(sb.toString());
        }).listen();
        vertx.deployVerticle("spring:org.jacpfx.test.vertx.spring.SpringTestVerticle2");
        vertx.deployVerticle("org.jacpfx.vertx.spring.SimpleVerticle");
    }

    @Override
    public void stop() {
        System.out.println("STOP");
    }
}
