package org.jacpfx.vertx.spring.rest;

import org.jacpfx.vertx.spring.SpringVerticle;
import org.jacpfx.vertx.spring.configuration.SpringConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.platform.Verticle;

/**
 * Created by amo on 26.09.14.
 */
@Component(value = "RestEntryVerticle")
@SpringVerticle(springConfig = SpringConfiguration.class)
public class RestEntryVerticle extends Verticle {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void start() {
        System.out.println("START RestEntryVerticle  THREAD: " + Thread.currentThread() + "  this:" + this+"   " +applicationContext);
        //container.deployWorkerVerticle("spring:org.jacpfx.vertx.spring.services.EmployeeService",10);
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(handler -> {
                    handler.response().putHeader("content-type", "text/json");

                    final String path = handler.path();
                    final String id = handler.params().get("id");
                    vertx.eventBus().sendWithTimeout(path, id, 6000, (Handler<AsyncResult<Message<String>>>) event -> {
                        if (event.succeeded()) {
                            handler.response().end(event.result().body());
                        } else {

                            handler.response().end("error");
                        }
                    });
                    System.out.println("http method: " + handler.method());
                }

        ).listen(8080, "localhost", asyncResult -> System.out.println("Listen succeeded? " + asyncResult.succeeded()));


    }
}
