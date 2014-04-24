spring-vertx-mod
================

A spring vertx module which creates spring aware verticles. This module starts for each spring aware verticle an own spring context. Spring verticles can deploy "normal" verticles and vice versa.
> You should use Spring-verticles in „worker mode“ to avoid blocking the event bus.

##Usage:##
To make a verticle spring aware you first need a (Java) Spring configuration class like this:
<pre>
@Configuration
@ComponentScan
public class TestConfiguration {
…
}
</pre>
Next you create a verticle class and annotate it with @SpringVerticle.

<pre>
@Component
@SpringVerticle(springConfig=TestConfiguration.class)
public class SpringVerticle extends Verticle {
    @Inject
    private SayHelloBean bean;

    @Override
    public void start() {
       ...
    }

    @Override
    public void stop() {
       ...
    }
}


</pre>