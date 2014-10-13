package org.jacpfx.vertx.spring;

/**
 * Created by amo on 10.10.14.
 */

import org.jacpfx.vertx.spring.configuration.MongoDataSourceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;

import static org.junit.Assert.assertTrue;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={MongoDataSourceConfiguration.class})
public class LocalMongoConfigurationTest {

    @Inject MongoDbFactory mongoDbFactory;

    @Test
    public void testMongoDbFactoryConnection() {
        assertTrue(mongoDbFactory.getDb().getMongo().getConnector().isOpen());
    }
}