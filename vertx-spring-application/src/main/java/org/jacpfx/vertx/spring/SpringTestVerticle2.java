package org.jacpfx.vertx.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import java.util.Map;

/**
 * Created by amo on 04.03.14.
 */
@Component
@SpringVerticle(springConfig=TestConfiguration.class)
public class SpringTestVerticle2 extends Verticle {

    @Autowired
    private SayHelloBean bean;

    @Override
    public void start() {
        getContainer();
        System.out.println("START SpringVerticle2: "+bean.sayHello()+"  THREAD: "+Thread.currentThread()+"  this:"+this);
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> header : req.headers().entries()) {
                    sb.append(header.getKey()).append(": ").append(header.getValue()).append("\n").append(bean.sayHello());
                }
                req.response().putHeader("content-type", "text/plain");
                req.response().end(sb.toString());
            }
        }).listen(8073);
        stop();
    }

    @Override
    public void stop() {
       // System.out.println("STOP");
    }
}


