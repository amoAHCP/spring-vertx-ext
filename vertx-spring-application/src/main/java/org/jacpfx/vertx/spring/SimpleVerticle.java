package org.jacpfx.vertx.spring;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;

import java.util.Map;

/**
 * @author Andy Moncsek
 */

public class SimpleVerticle extends AbstractVerticle {

	@Override
	public void start() {
		System.out.println("START HTTP VERTICLE THREAD: " + Thread.currentThread());
		vertx.createHttpServer(new HttpServerOptions().setPort(8071)).requestHandler(rc -> {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> header : rc.headers().entries()) {
				sb.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
			}
			rc.response().putHeader("content-type", "text/plain");
			rc.response().end(sb.toString());
		}).listen();
	}
}
