spring-vertx-mod
================

A spring vertx module which creates spring aware verticles. This module starts for each spring aware verticle an own spring context. Spring verticles can deploy "normal" verticles and vice versa.
> You should use Spring-verticles in „worker mode“ to avoid blocking the event bus.

##Usage:##
Add the spring-vertx-mod dependency to your project, in case of maven like this:
```xml
        <dependency>
            <groupId>org.jacpfx.vertx.spring</groupId>
            <artifactId>vertx-spring-mod</artifactId>
            <version>1.0</version>
        </dependency>
```
> Note: currently only the snapshot is on maven central. Simply execute mvn clean install in the vertx-spring-mod project to add the dependency to your local repository.

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
### module deployment ###
In case of using a spring-verticle as the initial verticle of your module, add the prefix "spring" to your vertex class name in the mod.json file.
<pre>
"main":"spring:org.jacpfx.vertx.spring.SpringTestVerticle"
</pre>
### start on command line ###
To run a spring verticle from command line or from Eclipse/Netbeans/Idea set the following VM Parameter:
<pre>
-Dvertx.langs.spring=org.jacpfx.vertx.spring~vertx-spring-mod~1.0-SNAPSHOT:org.jacpfx.vertx.spring.SpringVerticleFactory
</pre>
To run the verticle use:
<pre>
vertx run spring:org.jacpfx.vertx.spring.SpringTestVerticle
</pre>
###Hint for Idea users (should be similar with eclipse/netbeans)###
####Main class:####
<pre>org.vertx.java.platform.impl.cli.Starter</pre>
#### VM Options ####
<pre>-Dvertx.langs.spring=org.jacpfx.vertx.spring~vertx-spring-mod~1.0-SNAPSHOT:org.jacpfx.vertx.spring.SpringVerticleFactory</pre>
#### Programm arguments ####
<pre>run spring:org.jacpfx.vertx.spring.SpringTestVerticle</pre>