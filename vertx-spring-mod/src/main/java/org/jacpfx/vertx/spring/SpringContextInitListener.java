package org.jacpfx.vertx.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.vertx.java.platform.Container;

import java.util.Map;
import java.util.Set;

/**
 * Created by amo on 04.03.14.
 */
public class SpringContextInitListener implements ApplicationListener<ContextStartedEvent> {
    @Autowired
    private Container container;

    public void onApplicationEvent(ContextStartedEvent event) {
/*        final ApplicationContext springContext = event.getApplicationContext();
        final Map<String,Object> result = springContext.getBeansWithAnnotation(Autostart.class);
        final Set<String> keys = result.keySet();
        System.out.println("config: "+container.config());
        for(final String beanName : keys) {
                //container.deployVerticle(beanName);
        }*/
       // System.out.println("ContextStartedEvent Received: "+container+"   beans: "+result);
    }
}
