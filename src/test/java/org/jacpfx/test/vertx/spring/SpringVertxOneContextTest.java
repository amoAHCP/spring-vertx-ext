package org.jacpfx.test.vertx.spring;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.test.core.VertxTestBase;
import org.jacpfx.test.vertx.spring.factory.SpringTestVerticle;
import org.jacpfx.vertx.spring.SpringVerticleFactory;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * Test whether deployment of a verticle works as expected.
 * 
 * @author johannes2
 *
 */
@SpringBootApplication
public class SpringVertxOneContextTest extends VertxTestBase {

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeployment() throws IOException {
        Vertx vertx = Vertx.vertx();

		ApplicationContext context = SpringApplication.run(SpringVertxOneContextTest.class);


		SpringVerticleFactory.setApplicationContext(context);

        vertx.deployVerticle("java-spring:" + SpringTestVerticle.class.getCanonicalName(), new DeploymentOptions().setInstances(2), ch -> {
            assertTrue(ch.succeeded());
            assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                testComplete();
            });
        });
        await();
    }
}
