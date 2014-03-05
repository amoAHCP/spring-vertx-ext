package org.jacpfx.vertx.spring;

import org.junit.Test;
import org.springframework.context.support.GenericApplicationContext;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Jochen Mader
 */
public class SpringContextHolderVerticleTest {

    @Test
    public void testStart() {
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.getDefaultListableBeanFactory().registerSingleton("testService", new TestService());
        genericApplicationContext.refresh();
      //  SpringContextHolderVerticle springVerticle = new SpringContextHolderVerticle(genericApplicationContext);
      //  springVerticle.start();
      //  assertNotNull(springVerticle.getApplicationContext().getBean("testService"));
    }

    @Test
    public void testStop() {
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.getDefaultListableBeanFactory().registerSingleton("testService", new TestService());
        genericApplicationContext.refresh();
     //   SpringContextHolderVerticle springVerticle = new SpringContextHolderVerticle(genericApplicationContext);
      //  springVerticle.start();
      //  springVerticle.stop();
        assertFalse(genericApplicationContext.isRunning());
    }
}
