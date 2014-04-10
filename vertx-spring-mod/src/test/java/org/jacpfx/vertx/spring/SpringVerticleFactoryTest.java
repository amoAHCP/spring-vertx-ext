package org.jacpfx.vertx.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Jochen Mader
 */

public class SpringVerticleFactoryTest {


    @Test
    public void testDeployVerticle() throws Exception {
        SpringVerticleFactory springVerticleFactory = createCleanSpringVerticleFactory();
        Vertx vertx = mock(Vertx.class);
        Container container = mock(Container.class);
        springVerticleFactory.init(vertx, container, Thread.currentThread().getContextClassLoader());
        Verticle verticle = springVerticleFactory.createVerticle(SpringTestVerticle.class.getName());
        // note the verticle returned is an cglib enhanced class!!
        assertEquals(SpringTestVerticle.class.getSimpleName(), verticle.getClass().getSuperclass().getSimpleName());
        assertEquals(verticle.getContainer(), container);
        assertEquals(verticle.getVertx(), vertx);
        assertTrue(SpringTestVerticle.class.isAssignableFrom(verticle.getClass()));
        SpringTestVerticle v1 = SpringTestVerticle.class.cast(verticle);
        assertEquals(v1.getBean().getClass(), TestService.class);
    }
    @Test
    public void testIsolatedContext() throws Exception {
        SpringVerticleFactory springVerticleFactory = createCleanSpringVerticleFactory();
        Vertx vertx = mock(Vertx.class);
        Container container = mock(Container.class);
        springVerticleFactory.init(vertx, container, Thread.currentThread().getContextClassLoader());
        Verticle verticle1 = springVerticleFactory.createVerticle(SpringTestVerticle.class.getName());
        Verticle verticle2 = springVerticleFactory.createVerticle(SpringTestVerticle.class.getName());
        SpringTestVerticle v1 = SpringTestVerticle.class.cast(verticle1);
        SpringTestVerticle v2 = SpringTestVerticle.class.cast(verticle2);
        assertFalse(v1.equals(v2));
        assertFalse(v1.getBean().equals(v2.getBean()));
        assertEquals(v1.getBean().getClass().getCanonicalName(),v2.getBean().getClass().getCanonicalName());
        assertFalse(v1.getSpringContext().equals(v2.getSpringContext()));
        assertEquals(v1.getSpringContext().getClass().getCanonicalName(),v2.getSpringContext().getClass().getCanonicalName());

    }

    private SpringVerticleFactory createCleanSpringVerticleFactory() {
        SpringVerticleFactory springVerticleFactory = new SpringVerticleFactory();
        return springVerticleFactory;
    }

    @Configuration
    public static class TestConfiguration {
        @Bean
        public TestService testService() {
            return new TestService();
        }

        @Bean
        public SpringTestVerticle springTestVerticle() {
            return new SpringTestVerticle();
        }

    }

    @Component
    @SpringVerticle(springConfig = TestConfiguration.class)
    public static class SpringTestVerticle extends Verticle {

        @Autowired
        private TestService bean;
        @Autowired
        public ApplicationContext context;

        @Override
        public void start() {
            System.out.println(bean);
        }

        public TestService getBean() {
            return bean;
        }

        public ApplicationContext getSpringContext() {
            return context;
        }
    }


}
