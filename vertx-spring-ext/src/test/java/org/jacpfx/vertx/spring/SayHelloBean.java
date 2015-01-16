package org.jacpfx.vertx.spring;

import org.springframework.stereotype.Component;

@Component
public class SayHelloBean {

    public String sayHello() {
        return "Hello Vertx world";
    }
}
