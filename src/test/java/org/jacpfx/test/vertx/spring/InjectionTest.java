package org.jacpfx.test.vertx.spring;

import io.vertx.core.Vertx;
import io.vertx.test.core.VertxTestBase;
import org.jacpfx.test.vertx.spring.factory.SpringInjectionComponentScanTestVerticle;
import org.jacpfx.test.vertx.spring.factory.SpringInjectionComponentScanTestVerticleStatic;
import org.jacpfx.test.vertx.spring.factory.SpringInjectionTestVerticle;
import org.jacpfx.test.vertx.spring.factory.SpringInjectionTestVerticleStatic;
import org.jacpfx.test.vertx.spring.factory.SpringTestVerticle;
import org.junit.Test;

import java.io.IOException;

/**
 * Test whether deployment of a verticle works as expected.
 * 
 * @author johannes2
 *
 */
public class InjectionTest extends VertxTestBase {

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeployment() throws IOException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("java-spring:" + SpringInjectionTestVerticle.class.getCanonicalName(), ch -> {
            assertTrue(ch.succeeded());
            assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                testComplete();
            });
        });
        await();
    }

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeploymentComponentScan() throws IOException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("java-spring:" + SpringInjectionComponentScanTestVerticle.class.getCanonicalName(), ch -> {
            assertTrue(ch.succeeded());
            assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                testComplete();
            });
        });
        await();
    }


    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testStaticDeployment() throws IOException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SpringInjectionTestVerticleStatic.class.getCanonicalName(), ch -> {
            assertTrue(ch.succeeded());
            assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                assertTrue(chu.succeeded());
                testComplete();
            });
        });
        await();
    }

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testStaticDeploymentComponentScan() throws IOException {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SpringInjectionComponentScanTestVerticleStatic.class.getCanonicalName(), ch -> {
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
