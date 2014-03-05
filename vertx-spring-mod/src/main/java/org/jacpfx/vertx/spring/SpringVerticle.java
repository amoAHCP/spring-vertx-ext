package org.jacpfx.vertx.spring;

/**
 * Created by amo on 03.03.14.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.vertx.java.core.Vertx;
import org.vertx.java.platform.Container;
import org.vertx.java.platform.Verticle;

/**
 * A simple Verticle to be used for your own implementations. Vertx- and Container-instance are automatically wired.
 *
 * @author Jochen Mader
 */
public class SpringVerticle extends Verticle {

    @Override
    @Autowired
    public void setVertx(Vertx vertx) {
        super.setVertx(vertx);
    }

    @Override
    @Autowired
    public void setContainer(Container container) {
        super.setContainer(container);
    }

}