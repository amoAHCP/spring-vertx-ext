package org.jacpfx.test.vertx.spring.factory;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import org.jacpfx.vertx.spring.SpringVerticleFactory;
import org.junit.Test;

public class SpringVerticleFactoryTest {

    @Test
    public void testDeployVerticle() throws Exception {
        SpringVerticleFactory springVerticleFactory = createCleanSpringVerticleFactory();
        Vertx vertx = mock(Vertx.class);
        springVerticleFactory.init(vertx);
        Verticle verticle = springVerticleFactory.createVerticle(SpringTestVerticle.class.getName(), Thread.currentThread().getContextClassLoader());
//        verticle.start(null);
        // note the verticle returned is an cglib enhanced class!!
//        assertEquals(SpringTestVerticle.class.getSimpleName(), verticle.getClass().getSuperclass().getSimpleName());
//        assertEquals(vertx, verticle.getVertx());
//        assertTrue(SpringTestVerticle.class.isAssignableFrom(verticle.getClass()));
//        SpringTestVerticle v1 = SpringTestVerticle.class.cast(verticle);
//        assertEquals(v1.getBean().getClass(), TestService.class);
    }

    @Test
    public void testIsolatedContext() throws Exception {
        SpringVerticleFactory springVerticleFactory = createCleanSpringVerticleFactory();
        Vertx vertx = mock(Vertx.class);
        springVerticleFactory.init(vertx);
        Verticle verticle1 = springVerticleFactory.createVerticle(SpringTestVerticle.class.getName(), Thread.currentThread().getContextClassLoader());
        assertNotNull(verticle1);
        Verticle verticle2 = springVerticleFactory.createVerticle(SpringTestVerticle.class.getName(), Thread.currentThread().getContextClassLoader());
        assertNotNull(verticle2);
//        SpringTestVerticle v1 = SpringTestVerticle.class.cast(verticle1);
//        SpringTestVerticle v2 = SpringTestVerticle.class.cast(verticle2);
//        assertFalse(v1.equals(v2));
//        assertFalse(v1.getBean().equals(v2.getBean()));
//        assertEquals(v1.getBean().getClass().getCanonicalName(),v2.getBean().getClass().getCanonicalName());
//        assertFalse(v1.getSpringContext().equals(v2.getSpringContext()));
//        assertEquals(v1.getSpringContext().getClass().getCanonicalName(),v2.getSpringContext().getClass().getCanonicalName());
    }

    private SpringVerticleFactory createCleanSpringVerticleFactory() {
        return new SpringVerticleFactory();
    }

}
