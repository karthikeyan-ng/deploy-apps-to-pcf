# Todo Web Application using Spring Boot and MySQL as Database

Run com.techstack.todo.Application as a Java Web Application.

Runs on default port of Spring Boot - 8080

Application uses MySQL for development and H2 database to run the tests.

## Can be run as a Jar or a WAR

`mvn clean install` generate a war which can deployed to your favorite web server.

We will deploy to Cloud as a WAR

## Web Application
This application configured with Spring Security.

- http://localhost:8080/login with the following credentials
    - pascal /dummy
    - thomas / dummy
    - sara / dummy
- You can add, delete and update your todos
- Spring Security is used to secure the application
- `com.techstack.todo.security.SecurityConfiguration` contains the in memory security credential configuration.


## Changes from H2 Application

#### pom.xml

Add the following Dependencies
```xml
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
</dependency>

<!-- PCF Connectors -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-cloudfoundry-connector</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-spring-service-connector</artifactId>
</dependency>
```

The following `repositories` and `pluginRepositories` is required for `cloud` profile
```xml
<repositories>
    <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestones</url>
    </repository>
</repositories>

<pluginRepositories>
    <pluginRepository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestones</url>
    </pluginRepository>
</pluginRepositories>
```

#### src/main/resources/application.properties

```
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL55Dialect
spring.datasource.url=jdbc:mysql://db4free.net:3306/testdb_ng?useSSL=false
spring.datasource.username=system_ng
spring.datasource.password=password
```

#### src/test/resources/application.properties

```
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.username=sa
spring.datasource.password=sa
```

#### public class Todo

```
@Size(min=10, message="Enter at least 10 Characters...")
@Column(name="description")
private String desc;
```

## Spring Auto Reconfiguration

- [https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration](https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration)
- [https://docs.run.pivotal.io/buildpacks/java/configuring-service-connections/spring-service-bindings.html](https://docs.run.pivotal.io/buildpacks/java/configuring-service-connections/spring-service-bindings.html)


### Disable Spring Auto Reconfiguration

#### manifest.yml

```
  env:
    JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: false}'
```
#### application.properties

```
spring.datasource.url=${vcap.services.todo-database.credentials.jdbcUrl}
spring.datasource.username=${vcap.services.todo-database.credentials.username:todos-user}
spring.datasource.password=${vcap.services.todo-database.credentials.password:dummytodos}
```

### Customize Spring Boot Auto Reconfiguration - Spring Boot Cloud Connectors

- CloudFoundryDatabaseConfig

- https://spring.io/blog/2015/04/27/binding-to-data-services-with-spring-boot-in-cloud-foundry

## manifest.yml
```
applications:
  - name: todo-web-app-mysql
    disk_quota: 1G
    instances: 1
    memory: 1G
    path: target/todo-web-app-mysql-0.0.1-SNAPSHOT.war
    timeout: 120
    routes:
      - route: todo-web-app-mysql.cfapps.io
    buildpacks:
      - https://github.com/cloudfoundry/java-buildpack.git
    stack: cflinuxfs3
    env:
      JBP_CONFIG_SPRING_AUTO_RECONFIGURATION: '{enabled: false}'
    service:
      - todo-database
 ```