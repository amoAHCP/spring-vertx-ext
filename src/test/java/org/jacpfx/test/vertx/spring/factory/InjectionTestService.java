package org.jacpfx.test.vertx.spring.factory;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
* @author Jochen Mader
*/
@Scope("singleton")
public class InjectionTestService {
    @Autowired
    Vertx vertx;

    public InjectionTestService() {
    }

    public String hello() {
        return "world"+vertx.deploymentIDs().stream().reduce((a,b)->a+b);
    }

    public Vertx vertx() {
        return vertx;
    }
}
