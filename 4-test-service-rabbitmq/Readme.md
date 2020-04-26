# Test Service using RabbitMQ (local and cloud)

Run com.techstack.todo.RabbitMQApplication as a Java Web Application.

This Application uses the RabbitMQ to send and receive messages from RabbitMQ broker.

## Dependencies required
In order to work with RabbitMQ in Spring Boot Application add the following dependencies.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

If you are going to deploy your application on PCF which you going to use PCF Marketplace RabbitMQ service use below
dependency. This would do Auto Reconfigure application and establish a connection to PCF bounded service.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cloud-connectors</artifactId>
</dependency>
```

## Using RabbitMQ

#### Local RabbitMQ configuration
In order to use RabbitMQ during development you can download Rabbit
```properties
## Option1
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
## Option2
spring.rabbitmq.addresses=amqp://guest:guest@localhost:5672
```

#### Produce Message
You have an option to **produce** message using below Templates.  
- `RabbitTemplate`
- `AmqpTemplate`
    
#### Consume Message
In order to consume message annotate any Spring bean method using `@RabbitListener`    

## Local Build

`mvn clean install` generate a jar which can deployed to your favorite server.

## Spring Auto Reconfiguration

- [https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration](https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration)
- [https://docs.run.pivotal.io/buildpacks/java/configuring-service-connections/spring-service-bindings.html](https://docs.run.pivotal.io/buildpacks/java/configuring-service-connections/spring-service-bindings.html)

## PCF Deployment 

### manifest.yml

```yaml
applications:
  - name: test-service-using-rabbitmq
    disk_quota: 1G
    instances: 1
    memory: 1G
    path: target/4-test-service-rabbitmq-0.0.1-SNAPSHOT.jar
    timeout: 120
    routes:
      - route: test-service-using-rabbitmq.cfapps.io
    buildpacks:
      - https://github.com/cloudfoundry/java-buildpack.git
    stack: cflinuxfs3
    env:
##    To enable cloud profile on PCF while deploying app, use below property
#      SPRING_PROFILES_ACTIVE: cloud
##    OR
##    This property enables the "cloud" profile through JavaBuildPack(JBP) Configuration using Spring Auto Reconfiguration
      JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: true}'

##    This property enables you to use Java11 jre for your app
#      JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { version: 11.+ } }'
    service:
      - rabbitmq-service
 ```

## References:   
Refer this repository for more detailed notes: https://github.com/karthikeyan-ng/learn-and-apply-rabbitmq