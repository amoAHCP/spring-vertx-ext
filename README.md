spring-vertx-ext
================

A spring vertx extension which creates spring aware verticles. This extension starts for each spring aware verticle an own spring context. Spring verticles can deploy "normal" verticles and vice versa.
> You should use Spring-verticles in „worker mode“ to avoid blocking the event bus.

##Usage:##
Add the spring-vertx-ext dependency to your project, in case of maven like this:
```xml
        <dependency>
            <groupId>org.jacpfx.vertx.spring</groupId>
            <artifactId>vertx-spring-ext</artifactId>
            <version>2.0.0-SNAPSHOT</version>
        </dependency>
</pre>

> Note: currently only the snapshot is on maven central. Simply execute mvn clean install in the vertx-spring-ext project to add the dependency to your local repository.

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


## deployment ##

In case of using a spring-verticle as the initial verticle of your project, add the prefix "java-spring" to your vertex class.
<pre>
  spring-java:org.jacpfx.vertx.spring.SpringTestVerticle
</pre>
