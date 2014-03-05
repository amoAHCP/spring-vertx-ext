package org.jacpfx.vertx.spring;

import org.springframework.stereotype.Component;

/**
 * Created by amo on 04.03.14.
 */
@Component
public class SayHelloBean {

    public String sayHello() {
        return "Hello Vertx world";
    }
}
