package org.jacpfx.test.vertx.spring;

import io.vertx.core.Vertx;
import io.vertx.test.core.VertxTestBase;
import org.jacpfx.test.vertx.spring.factory.SpringTestVerticle;
import org.junit.Test;

import java.io.IOException;

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
            vertx.undeploy(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                testComplete();
            });
        });
        await();
    }
}
