package org.jacpfx.vertx.spring;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author Jochen Mader
 */

public class SpringVerticleFactoryTest {

    @Test
    public void testLoadApplicationContext() throws Exception {
        SpringVerticleFactory springVerticleFactory = createCleanSpringVerticleFactory();
        Vertx vertx = mock(Vertx.class);
        Container container = mock(Container.class);
        springVerticleFactory.init(vertx, container, Thread.currentThread().getContextClassLoader());
        Verticle verticle = springVerticleFactory.createVerticle(TestConfiguration.class.getName());
       // assertEquals(SpringContextHolderVerticle.class, verticle.getClass());
    }

    @Test
    public void testDeployVerticleFromApplicationContext() throws Exception {
        SpringVerticleFactory springVerticleFactory = createCleanSpringVerticleFactory();
        Vertx vertx = mock(Vertx.class);
        Container container = mock(Container.class);
        springVerticleFactory.init(vertx, container, Thread.currentThread().getContextClassLoader());
        springVerticleFactory.createVerticle(TestConfiguration.class.getName());
        Verticle verticle = springVerticleFactory.createVerticle("testVerticle");
       // assertEquals(TestVerticle.class, verticle.getClass());
        assertEquals(verticle.getContainer(), container);
        assertEquals(verticle.getVertx(), vertx);
    }

    private SpringVerticleFactory createCleanSpringVerticleFactory() {
        SpringVerticleFactory springVerticleFactory = new SpringVerticleFactory();
        AtomicReference<AnnotationConfigApplicationContext> annotationConfigApplicationContextAtomicReference = (AtomicReference<AnnotationConfigApplicationContext>) ReflectionTestUtils.getField(springVerticleFactory, "annotationConfigApplicationContextRef");
        annotationConfigApplicationContextAtomicReference.set(null);
        return springVerticleFactory;
    }
    @Configuration
    public static class TestConfiguration {
        @Bean
        public TestService testService() {
            return new TestService();
        }

    }



}
