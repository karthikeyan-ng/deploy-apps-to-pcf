# Todo Web Application using Spring Boot and H2 In memory database using Spring Security

Run `com.techstack.todo.Application` as a Java Web Application (War).

Runs on default port of Spring Boot - 8080 

## Can be run as a WAR

`mvn clean install` generate a war which can deployed to your favorite web server.

We will deploy to Cloud as a WAR

For Local deployment `mvn clean spring-boot:run`

## Web Application

- User can access the application by accessing `http://localhost:8080` or `http://localhost:8080/login`.
    - By default application configured with the following users.
        - pascal | thomas | sara 
    - The password for those user is "dummy"
- You can add, delete and update your todos
- Spring Security is used to secure the application
    - Refer: `com.techstack.todo.security.SecurityConfiguration` contains the in memory security credential configuration.

## H2 Console

- http://localhost:8080/h2-console
- Use `jdbc:h2:mem:testdb` as JDBC URL 

## manifest.mf

```yaml
applications:
- name: todo-web-app-h2
  disk_quota: 1G
  instances: 1
  memory: 1G
  path: target/todo-web-app-h2-0.0.1-SNAPSHOT.war
  timeout: 120
  routes:
  - route: todo-web-app-h2.cfapps.io
  buildpacks:
  - https://github.com/cloudfoundry/java-buildpack.git
  stack: cflinuxfs3
 ```