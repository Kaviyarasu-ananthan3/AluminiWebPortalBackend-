# Alumni Connect Portal API

This repository contains the Spring Boot API for an alumni and student networking portal. Its controllers and data models cover registration and login, profiles, alumni/student directories, jobs and applications, mentorship requests, chat, dashboard statistics, and administrator search/export workflows.

## Technology

- Java 17
- Spring Boot 4 with Spring Web MVC
- Spring Data JPA and MySQL
- Bean Validation and Lombok
- Apache POI and iText for administrator exports
- Maven Wrapper for builds

## Prerequisites

- Java 17 JDK
- A reachable MySQL database
- No global Maven installation is required; the repository includes `mvnw` and `mvnw.cmd`

## Configuration

Spring reads its settings from `src/main/resources/application.properties` and environment variables. The required database variables are listed in `.env.example`:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_JPA_HIBERNATE_DDL_AUTO`

`PORT` optionally overrides the default HTTP port of `8080`. Multipart upload limits and SQL logging can also be overridden with the variables referenced by `application.properties`.

The application does not load `.env` files itself. Export the variables in your shell or configure them in your IDE/runtime. Do not commit local database credentials.

## Run

On macOS or Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
mvnw.cmd spring-boot:run
```

When `PORT` is not set, the service listens on `http://localhost:8080`.

## Build and test

```bash
./mvnw test
./mvnw clean package
```

The packaged application is written to `target/`.

## API areas

- `/auth` — registration, login, profiles, password changes, and administrator search/export
- `/users`, `/students`, and `/alumni` — directory and profile data
- `/jobs` and `/jobApplication` — job posting and application workflows
- `/mentorship` — requests, statuses, meetings, and notifications
- `/chat` — student/mentor messages
- `/dashboard` — aggregate portal statistics

## Runtime note

The application class and the controller, entity, repository, and service
packages are all beneath `com.alumni.alumniconnectportal`, so Spring Boot's
default component scan covers them. Use the running application's route
mapping and test results as the authoritative check for endpoint availability.
