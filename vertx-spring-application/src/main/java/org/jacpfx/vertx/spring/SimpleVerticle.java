package org.jacpfx.vertx.spring;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import java.util.Map;

/**
 * @author Andy Moncsek
 */


public class SimpleVerticle extends Verticle {

   @Override
    public void start() {
        System.out.println("START HTTP VERTICLE" +"  THREAD: "+Thread.currentThread());
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> header : req.headers().entries()) {
                    sb.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
                }
                req.response().putHeader("content-type", "text/plain");
                req.response().end(sb.toString());
            }
        }).listen(8071);
    }
}
