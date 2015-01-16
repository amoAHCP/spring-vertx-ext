package org.jacpfx.vertx.spring;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

/**
 * Created by amo on 04.03.14.
 */
@Component
@SpringVerticle(springConfig = TestConfiguration.class)
public class SpringTestVerticle3 extends AbstractVerticle {

	@Inject
	private SayHelloBean bean;

	@Override
	public void start() {
		System.out.println("START SpringVerticle3: " + bean.sayHello() + "  THREAD: " + Thread.currentThread() + "  this:" + this);
		vertx.createHttpServer(new HttpServerOptions().setPort(8099)).requestHandler(rc -> {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> header : rc.headers().entries()) {
				sb.append(header.getKey()).append(": ").append(header.getValue()).append("\n").append(bean.sayHello());
			}
			rc.response().putHeader("content-type", "text/plain");
			rc.response().end(sb.toString());
		}).listen();
	}

	@Override
	public void stop() {
		// System.out.println("STOP");
	}
}
