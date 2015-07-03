package org.jacpfx.test.vertx.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import org.jacpfx.vertx.spring.SpringVerticleFactory;
import org.jacpfx.test.vertx.spring.factory.SpringTestVerticle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class FactoryTest {

    private SpringVerticleFactory factory;

    @Mock
    Vertx vertx;

    @Before
    public void setUp() throws Exception {
        factory = new SpringVerticleFactory();
        factory.init(vertx);
    }

    @Test
    public void testPrefix() {
        assertEquals("java-spring", factory.prefix());
    }

    @Test
    public void testCreateVerticle() throws Exception {
        String identifier = SpringVerticleFactory.PREFIX + ":" + SpringTestVerticle.class.getName();
        Verticle verticle = factory.createVerticle(identifier, Thread.currentThread().getContextClassLoader());
        assertNotNull(verticle);
    }


}
