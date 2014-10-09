package org.jacpfx.vertx.spring.services;

import org.jacpfx.vertx.spring.SpringVerticle;
import org.jacpfx.vertx.spring.configuration.SpringConfiguration;
import org.springframework.stereotype.Component;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Verticle;


/**
 * Created by amo on 09.10.14.
 */
@Component(value = "EmployeeService")
@SpringVerticle(springConfig = SpringConfiguration.class)
public class EmployeeService extends Verticle {

    @Override
    public void start() {
       vertx.eventBus().registerHandler("/employee",this::handleMessage);
    }


    private void handleMessage(Message m) {
        Logger logger = container.logger();
        logger.info("message employee"+m.body());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m.reply("hello world");
    }
}
