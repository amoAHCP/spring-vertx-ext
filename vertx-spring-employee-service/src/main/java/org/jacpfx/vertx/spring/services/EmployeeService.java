package org.jacpfx.vertx.spring.services;

import com.google.gson.Gson;
import org.jacpfx.vertx.spring.SpringVerticle;
import org.jacpfx.vertx.spring.configuration.SpringConfiguration;
import org.jacpfx.vertx.spring.repository.EmployeeRepository;
import org.springframework.stereotype.Component;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;

import javax.inject.Inject;


/**
 * Created by amo on 09.10.14.
 */
@Component(value = "EmployeeService")
@SpringVerticle(springConfig = SpringConfiguration.class)
public class EmployeeService extends Verticle {

    @Inject
    private EmployeeRepository repository;

    private final Gson gson = new Gson();

    @Override
    public void start() {
        vertx.eventBus().registerHandler("/employee", this::handleMessage);
    }


    private void handleMessage(Message m) {
        Logger logger = container.logger();

        m.reply(gson.toJson(repository.getAllEmployees()));
        logger.info("reply to: " + m.body());
    }
}
