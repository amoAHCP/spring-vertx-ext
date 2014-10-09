package org.jacpfx.vertx.spring;

import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.VertxFactory;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.platform.PlatformLocator;
import org.vertx.java.platform.PlatformManager;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by amo on 26.09.14.
 */
public class RestEntryVerticleTest {

    private ExecutorService executor = Executors.newCachedThreadPool();
    static final PlatformManager pm = PlatformLocator.factory.createPlatformManager();
   // @BeforeClass
    public static void onStart() throws MalformedURLException, InterruptedException {
        // if server was started manually, uncomment this:
        connect(10);
    }



    private static PlatformManager connect(int instances) throws MalformedURLException, InterruptedException {

        final CountDownLatch waitForDeploy = new CountDownLatch(1);
        pm.deployVerticle("spring:org.jacpfx.vertx.spring.rest.RestEntryVerticle",
                null,
                new URL[]{new File(".").toURI().toURL()},
                instances,
                null,
                (event) -> {
                    if (event.succeeded()) waitForDeploy.countDown();
                });
        waitForDeploy.await(1000, TimeUnit.MILLISECONDS);
        return pm;

    }

    private HttpClient getClient() {

        Vertx vertx = VertxFactory.newVertx();
        HttpClient client = vertx.
                createHttpClient().
                setHost("localhost").
                setPort(8080);

        return client;
    }

    @Test
    public void getSimpleConnection() throws InterruptedException, IOException {
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        HttpClient client = getClient();
        HttpClientRequest request = client.get("/employee", new Handler<HttpClientResponse>() {
            public void handle(HttpClientResponse resp) {
                 resp.bodyHandler(body->System.out.println("Got a response: " +body.toString()));
                latch.countDown();
            }
        });

        request.end();
        latch.await();



    }
}
