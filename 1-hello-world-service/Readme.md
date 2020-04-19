# Hello World Rest API

Run com.techstack.pcf.Application as a Java Application.

- http://localhost:8080/hello-world

```txt
Hello World
```

- http://localhost:8080/hello-world-bean

```json
{"message":"Hello World - Changed"}
```

- http://localhost:8080/hello-world/path-variable/karthi

```json
{"message":"Hello World, karthi"}
```

## CF Push

## manifest.yml

```yaml
applications:
  - name: hello-service
    disk_quota: 1G
    instances: 1
    memory: 1G
    path: target/1-hello-world-service-0.0.1-SNAPSHOT.jar
    timeout: 120
    routes:
      - route: hello-service.cfapps.io
      - route: hello-service-new.cfapps.io
    buildpacks:
      - https://github.com/cloudfoundry/java-buildpack.git
    stack: cflinuxfs3
```

## manifest.yml - green
```yaml
applications:
  - name: hello-service
    disk_quota: 1G
    instances: 1
    memory: 1G
    path: target/1-hello-world-service-0.0.1-SNAPSHOT.jar
    timeout: 120
    routes:
        - route: hello-service-temp.cfapps.io
  buildpacks:
  - https://github.com/cloudfoundry/java-buildpack.git
  stack: cflinuxfs3
```