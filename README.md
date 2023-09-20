# VelocityPoC

Velocity is a Java-based templating engine.

It’s an open source web framework designed to be used as a view component in the MVC architecture, and it provides an
alternative to some existing technologies such as JSP.

Velocity can be used to generate XML files, SQL, PostScript and most other text-based formats.

In this article, we will explore how it can be used to create dynamic web pages.

The core class of Velocity is the VelocityEngine.

It orchestrates the whole process of reading, parsing and generating content using data model and velocity template.

Simply put, here are the steps we need to follow for any typical velocity application:

Initialize the velocity engine
Read the template
Put the data model in context object
Merge the template with context data and render the view

```
       <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity</artifactId>
            <version>1.7</version>
        </dependency>
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity-tools</artifactId>
            <version>2.0</version>
        </dependency>
```

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options". Create your own
`application-local.yml` file to override settings for development.

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing -
[learn more](https://bootify.io/next-steps/spring-boot-with-lombok.html).

After starting the application it is accessible under `localhost:8080`.

## Build

The application can be built using the following command:

```
mvnw clean package
```

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/Velocity-PoC-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=dev.lucho/velocity-po-c
```
