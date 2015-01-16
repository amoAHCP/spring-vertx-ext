package org.jacpfx.vertx.spring;

import io.vertx.core.Vertx;
import io.vertx.test.core.VertxTestBase;

import java.io.IOException;

import org.jacpfx.vertx.spring.factory.SpringTestVerticle;
import org.junit.Test;

/**
 * Test whether deployment of a verticle works as expected.
 * 
 * @author johannes2
 *
 */
public class SpringVertxTest extends VertxTestBase {

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeployment() throws IOException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("java-spring:" + SpringTestVerticle.class.getCanonicalName(), ch -> {
            assertTrue(ch.succeeded());
            assertNotNull(ch.result());
            vertx.undeployVerticle(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                testComplete();
            });
        });
        await();
    }
}
