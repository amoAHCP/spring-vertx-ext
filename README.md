spring-vertx-ext
================

## changes in 2.2 (SNAPSHOT)
- Update Vert.x to version 3.5.0.Beta1
- Update Spring to version 5.0.0.RC3
- Update AspectJ to version 1.8.10
- New: Static initializer to create a Spring-aware Verticle without using the Vert.x Factory mechanism. This means you can skip the maven-shade configuration or adding the "java-spring" prefix while deploying a verticle. This change will not affect the existing mechanism.
  - Usage:  
  ```java
  @Component
  @SpringVerticle(springConfig = TestConfiguration.class)
  public class SpringInjectionComponentScanTestVerticle extends AbstractVerticle {
  
      @Autowired
      private SayHelloBean bean;
  
      @Override
      public void start() {
          SpringVerticleFactory.initSpring(this);
          assertNotNull(bean);
         
      }
  }
   ```

## changes in 2.1
You can now inject the Vertx instance to your beans

## What it is
A spring-vertx extension which creates spring aware verticles. This extension creates for each spring aware Verticle an own spring context.
Spring Verticles can deploy "normal" Verticles and vice versa.
> You should use Spring-Verticles in „worker mode“ or use vertx.executeBlocking when calling blocking methods. Avoid blocking the event loop!

## Usage
Add the spring-vertx-ext dependency to your project, in case of maven like this:
```xml
        <dependency>
            <groupId>org.jacpfx.vertx.spring</groupId>
            <artifactId>vertx-spring-ext</artifactId>
            <version>2.1</version>
        </dependency>
 ```

Version 2.1 uses Vert.X 3.3.3 and Spring 4.3.5.RELEASE



To make a Verticle spring aware you first need a (Java) Spring configuration class like this:
```java
@Configuration
@ComponentScan
public class TestConfiguration {
…
}
 ```
> xml configuration is not supported! (you can import your xml in your java configuration)
 
Next you create a verticle class and annotate it with @SpringVerticle.

```java
@Component
@SpringVerticle(springConfig=TestConfiguration.class)
public class SpringVerticle extends AbstractVerticle {
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

 ```
To access the Vertx instance in your spring beans, you can inject it by using autowiring :
```java

@Component
public class MyService {
  @Autowired 
  private Vertx vertx;
  ...
}
 ```

## deployment ##

In case of using a spring-verticle as the initial verticle of your project, add the prefix "java-spring" to your vertex class.
```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>2.3</version>
        <executions>
            <execution>
                <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <transformers>
                            <transformer
                                    implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                <manifestEntries>
                                    <Main-Class>io.vertx.core.Starter</Main-Class>
                                    <Main-Verticle>java-spring:org.jacpfx.service.SpringVerticle</Main-Verticle>
                                </manifestEntries>
                            </transformer>
                            <transformer
                                    implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                                <resource>META-INF/services/io.vertx.core.spi.VerticleFactory</resource>
                            </transformer>
                        </transformers>
                        <outputFile>${project.build.directory}/${artifactId}-${version}-fat.jar</outputFile>
                    </configuration>
                </execution>
            </executions>
        </plugin>

  ```
  
  For embedded deployment use:
  ```java
  vertx.deployVerticle("java-spring:org.jacpfx.service.SpringVerticle"); 
  ```
  
