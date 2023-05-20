# Spring Boot CURD Example

An example of CRUD operation REST APIs using Spring Boot and PostgreSQL.

# Features

- Model, Repository, Service, Controller.
- Unit test using Mockito.

# Quick start

## Environment

- Java 17
- Gradle
- PostgreSQL
- VSCode

## Steps

1. In "application.properties", Replace `spring.datasource.url` `spring.datasource.username` `spring.datasource.password` using your database credentials.
2. (Optional) Adjust concurrent connections using `spring.datasource.hikari.maximum-pool-size` if needed.
3. (Optional) In "build.gradle", adjust java and spring boot version if the project does not start.
