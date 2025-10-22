[README.md](https://github.com/user-attachments/files/23051289/README.md)
# Dogs API

A small, practical RESTful service to manage police dog records. It demonstrates a typical Spring Boot + JPA application with DTO mapping via MapStruct, a soft-delete strategy, pagination, simple filtering, and integration tests using an in-memory H2 database.

This README explains the project's purpose, architecture, data model, HTTP API (examples), how to build/run/test locally, and troubleshooting tips.

---

## Quick overview

- Base API path: `/api/dogs`
- Consumes / produces: `application/json`
- Soft deletes: records are flagged with `deleted = true` instead of being physically removed
- Mapping: MapStruct maps between `entity` (JPA) and `dto` classes
- DB for local dev/tests: H2 in-memory (configured in `application.yml`)
- Default server port: `8085` (configured in `src/main/resources/application.yml`)

---

## What this project contains

- `src/main/java/org/example/entity/` — JPA `Dog` entity and enumerations
- `src/main/java/org/example/dto/` — DTOs (data transfer objects) for API payloads
- `src/main/java/org/example/repository/` — Spring Data JPA repository interfaces
- `src/main/java/org/example/service/` — business logic and soft-delete handling
- `src/main/java/org/example/controller/` — REST controllers (endpoints)
- `src/main/java/org/example/mapper/` — MapStruct mapper for DTO <-> Entity
- `src/test/java/` — unit and integration tests (integration tests use H2)
- `src/main/resources/application.yml` — config (H2 + server port)
- `pom.xml` — Maven build, dependencies (MapStruct, Spring Boot, H2, test starters)

---

## High-level architecture

- Controller layer handles HTTP requests and responses (JSON).
- Service layer contains business rules (e.g., soft delete, filtering, pagination).
- Repository layer (Spring Data JPA) handles persistence operations.
- MapStruct handles mapping boilerplate between JPA entity and DTO.

This separation keeps controllers thin and makes the core logic testable.

---

## Data model (summary)

Dog (key attributes):
- `badgeId` (Long) — primary key
- `name` (String)
- `breed` (String)
- `supplier` (enum/string)
- `gender` (enum: `MALE`, `FEMALE`)
- `birthDate` (LocalDate)
- `dateAcquired` (LocalDate)
- `currentStatus` (enum: `IN_TRAINING`, `IN_SERVICE`, `RETIRED`, `LEFT`)
- `leavingDate` (LocalDate)
- `leavingReason` (enum)
- `kennellingCharacteristic` (enum/string)
- `deleted` (boolean) — soft-delete flag

The project uses `DogDto` for API input/output and MapStruct to convert between `Dog` and `DogDto`.

---

## API Reference (concise)

All endpoints are prefixed with `/api/dogs`.

- POST `/api/dogs/` — create a dog
  - Request: JSON `DogDto` (example below)
  - Response: created `DogDto` with assigned `id` (`badgeId`)

- GET `/api/dogs/dogs` — list dogs (non-deleted)
  - Query params: `page` (default `0`), `size` (default `100`)
  - Response: paged list of `DogDto`

- GET `/api/dogs/{id}` — get a dog by badgeId (Long)

- PUT `/api/dogs/{id}` — update a dog by badgeId
  - Request: `DogDto` — controller preserves the `badgeId` from the existing record

- DELETE `/api/dogs/{id}` — soft-delete a dog (sets `deleted=true`)

- GET `/api/dogs/statuses` — returns possible `Status` enum values
- GET `/api/dogs/leaving-reasons` — returns possible `LeavingReason` enum values

Example `curl` to create a dog:

```bash
curl -X POST http://localhost:8085/api/dogs/ \
  -H "Content-Type: application/json" \
  -d '{"name":"Rex","breed":"Belgian Malinois","gender":"MALE","currentStatus":"IN_SERVICE"}'
```

Example to list dogs (paged):

```bash
curl "http://localhost:8085/api/dogs/dogs?page=0&size=10"
```

---

## Build & run (developer quick start)

Prerequisites:
- Java 17 (required for Spring Boot 3)
- Maven 3.6+

Steps:

1. Set Java 17 as your active JDK (example for macOS with Homebrew):

```bash
brew update
brew install openjdk@17
# update your shell config based on the `brew info openjdk@17` output, e.g.:
export JAVA_HOME="/usr/local/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"
source ~/.bash_profile
```

2. Build (MapStruct code generation happens during compile):

```bash
mvn -DskipTests clean package
```

3. Run the application:

```bash
mvn spring-boot:run
# or run the jar
java -jar target/untitled-1.0-SNAPSHOT.jar --server.port=8085
```

4. H2 console (for debugging) — default config in `application.yml`:
- URL: `http://localhost:8085/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (empty)

---

## Tests

- Unit tests: standard JUnit + Mockito tests in `src/test/java`.
- Integration tests: `DogApiIntegrationTest` starts the app on a random port using H2 and exercises POST and GET endpoints.

Run tests:

```bash
mvn test
```

If you want a quick build without running tests:

```bash
mvn -DskipTests package
```

---

## MapStruct details

- `DogMapper` is annotated `@Mapper(componentModel = "spring")` so MapStruct generates a Spring bean implementation at compile-time.
- The POM contains the MapStruct dependency and `mapstruct-processor` configured in the `maven-compiler-plugin` `annotationProcessorPaths` so generation occurs during the build.
- A conditional fallback mapper bean exists in the project to allow the app to run even if annotation processing isn't active — once MapStruct generates the mapper, Spring will prefer the generated bean and ignore the fallback.

---

## Troubleshooting & FAQ

- Q: Build fails with `release version 17 not supported`?
  - A: Your JDK is older than 17. Install and use Java 17 (see Build & run section).

- Q: `Cannot load driver class: org.h2.Driver` at startup?
  - A: Ensure the H2 dependency is present in `pom.xml` (it is by default in this project) and that the project has been built so the driver is on the classpath. Also ensure you didn't override `spring.datasource.*` to an external DB without the appropriate driver.

- Q: Spring can't find `DogRepository` bean?
  - A: Ensure `Main` (the `@SpringBootApplication` annotated class) is in package `org.example` and your repository package is `org.example.repository` (so component scanning finds it). The repository must also extend a Spring Data interface like `JpaRepository`.

- Q: MapStruct didn't generate a mapper class?
  - A: Confirm the POM contains `mapstruct-processor` in the `maven-compiler-plugin` `annotationProcessorPaths`, build with JDK 17, and re-run `mvn clean package`. If still missing, run with `-X` (`mvn -X package`) and inspect the annotation processor logs.

---

## Next improvements (suggested)

- Add request validation (`@Valid` + bean validation) for create/update endpoints.
- Implement more advanced filtering (name/breed/supplier) with query parsing and specs.
- Add OpenAPI/Swagger for interactive documentation.
- Harden tests: add negative path tests, edge cases, and DB migration scripts.

---
