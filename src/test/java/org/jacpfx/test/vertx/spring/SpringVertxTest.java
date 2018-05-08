package org.jacpfx.test.vertx.spring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Test;

import java.io.IOException;
import org.junit.runner.RunWith;

/**
 * Test whether deployment of a verticle works as expected.
 * 
 * @author johannes2
 *
 */
@RunWith(VertxUnitRunner.class)
public class SpringVertxTest  {

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeployment(TestContext tc) throws IOException {
        Async async = tc.async();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("java-spring:" + SpringTestVerticle.class.getCanonicalName(), ch -> {
            assertTrue(ch.succeeded());
            assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                async.complete();
            });
        });

        async.await();
    }
}
