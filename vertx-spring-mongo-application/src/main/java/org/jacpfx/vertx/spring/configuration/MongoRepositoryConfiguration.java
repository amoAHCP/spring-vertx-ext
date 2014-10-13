package org.jacpfx.vertx.spring.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.inject.Inject;
import javax.inject.Named;
/**
 * Created by amo on 10.10.14.
 */
@Configuration
@Import( { MongoDataSourceConfiguration.class })
@EnableMongoRepositories
public class MongoRepositoryConfiguration {

    @Inject
    @Named("local")
    private MongoDbFactory mongoDbFactoryLocal;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactoryLocal);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDbFactory mongoDbFactory, MongoTemplate mongoTemplate) throws Exception {
        return new GridFsTemplate(mongoDbFactory, mongoTemplate.getConverter());
    }
}