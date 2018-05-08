package org.jacpfx.test.vertx.spring;


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
public class InjectionTest  {

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeployment(TestContext tc) throws IOException {
        Async async = tc.async();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("java-spring:" + SpringInjectionTestVerticle.class.getCanonicalName(), ch -> {
            tc.assertTrue(ch.succeeded());
            tc.assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                tc.assertTrue(chu.succeeded());
                async.complete();
            });
        });
        async.await();
    }

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testDeploymentComponentScan(TestContext tc) throws IOException {
        Async async = tc.async();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("java-spring:" + SpringInjectionComponentScanTestVerticle.class.getCanonicalName(), ch -> {
            tc.assertTrue(ch.succeeded());
            tc.assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                tc.assertTrue(chu.succeeded());
                async.complete();
            });
        });
        async.await();
    }


    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testStaticDeployment(TestContext tc) throws IOException {
        Async async = tc.async();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SpringInjectionTestVerticleStatic.class.getCanonicalName(), ch -> {
            tc.assertTrue(ch.succeeded());
            tc.assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                tc.assertTrue(chu.succeeded());
                async.complete();
            });
        });
        async.await();
    }

    /**
     * Deploy and undeploy a verticle
     * @throws IOException
     */
    @Test
    public void testStaticDeploymentComponentScan(TestContext tc) throws IOException {
        Async async = tc.async();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(SpringInjectionComponentScanTestVerticleStatic.class.getCanonicalName(), ch -> {
            tc.assertTrue(ch.succeeded());
            tc.assertNotNull(ch.result());
            vertx.undeploy(ch.result(), chu -> {
                tc.assertTrue(chu.succeeded());
                async.complete();
            });
        });
        async.await();
    }
}
