package org.jacpfx.vertx.spring.configuration;

/**
 * Created by amo on 10.10.14.
 */

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.inject.Inject;

@Configuration
@Profile("default")
public class MongoDataSourceConfiguration {

    @Inject Environment environment;

    @Bean(name="local")
    public MongoDbFactory mongoDbFactoryLocal() throws Exception {
        MongoClient mongo = new MongoClient("localhost", 27017);
        return new SimpleMongoDbFactory(mongo, "employee");
    }
}
