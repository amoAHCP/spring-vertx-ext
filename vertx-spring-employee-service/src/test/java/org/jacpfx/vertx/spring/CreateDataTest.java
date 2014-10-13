package org.jacpfx.vertx.spring;

/**
 * Created by amo on 10.10.14.
 */

import org.fluttercode.datafactory.impl.DataFactory;
import org.jacpfx.vertx.spring.configuration.MongoDataSourceConfiguration;
import org.jacpfx.vertx.spring.model.Employee;
import org.jacpfx.vertx.spring.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={MongoDataSourceConfiguration.class,EmployeeRepository.class})
public class CreateDataTest {

    @Inject MongoDbFactory mongoDbFactory;

    @Inject
    EmployeeRepository repo;

    @Test
    public void testMongoDbFactoryConnection() {
        assertTrue(mongoDbFactory.getDb().getMongo().getConnector().isOpen());
    }
    @Test
    public void createBulkData() {
        DataFactory df = new DataFactory();
        List<Employee> all = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            all.add(new Employee(df.getRandomChars(5),df.getName(),df.getBirthDate(),df.getAddress(),df.getFirstName(),df.getLastName()));
        }
        repo.bulkCreateEmployees(all);

    }
    @Test
    public void getAllDataTest() {
        Collection<Employee> all = repo.getAllEmployees();
        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertTrue(all.size()>=1100);
    }
}