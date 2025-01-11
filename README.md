# Java Project with Spring Boot

This project was created to serve as a **boilerplate for microservices**. It is designed to support various demands for best practices in development, testing, continuous integration, and automated documentation.

By using this boilerplate, teams can streamline the development process, ensure code quality, and accelerate the delivery of robust and maintainable microservices.

This project uses **Spring Boot** to build a robust and scalable web application. Below, you'll find information about the technologies used and the practices adopted for development.

---

## 🚀 Tech Stack

- **Java**: The main programming language for the project.
- **Spring Boot**:
    - `spring-boot-starter-data-jpa`: For database integration using JPA.
    - `spring-boot-starter-validation`: For data validation.
    - `spring-boot-starter-web`: For developing RESTful APIs.
    - `spring-boot-starter-aop`: For aspect-oriented programming.
    - `spring-boot-devtools`: To enhance development with features like live reload.
    - `springdoc-openapi-starter-webmvc-ui`: For API documentation with Swagger UI support.
- **Database**:
    - `org.postgresql:postgresql`: PostgreSQL driver for database interactions.
    - **Liquibase**: For schema versioning and management.
- **MapStruct**: For object mapping.
- **Lombok**: To reduce boilerplate code.
- **Spotless**: To maintain code quality with consistent formatting.
- **JUnit**: For writing unit tests.
- **Spring REST Docs**: For generating documentation from tests.

---

## 📦 Configured Dependencies

### Main Dependencies
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `org.springframework.boot:spring-boot-starter-validation`
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-aop`
- `org.springframework.boot-devtools`
- `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0`
- `org.postgresql:postgresql:42.7.2`
- `org.mapstruct:mapstruct:1.6.3`
- `org.projectlombok:lombok`
- `com.diffplug.spotless:spotless-lib:2.45.0`

### Testing Dependencies
- `org.springframework.boot:spring-boot-starter-test`
- `org.springframework.restdocs:spring-restdocs-mockmvc`
- `org.junit.platform:junit-platform-launcher`

### Liquibase
- `org.liquibase:liquibase-core:4.23.0`
- `info.picocli:picocli:4.7.4`
- `org.postgresql:postgresql:42.7.2` (runtime)

---

## 📜 Commit Conventions and Versioning

This project follows the **[Conventional Commits](https://www.conventionalcommits.org/)** specification for commit messages. This approach allows:

- Automatic changelog generation.
- Identifying the type of change for each commit (`fix`, `feat`, `chore`, etc.).
- Marking breaking changes with `BREAKING CHANGE`.

Additionally, the project uses **[Semantic Versioning (SemVer)](https://semver.org/)** for version control:

- **MAJOR**: Breaking changes.
- **MINOR**: New features, backward-compatible.
- **PATCH**: Bug fixes.

The **[@semantic-release](https://semantic-release.gitbook.io/semantic-release/)** tool is integrated into the CI/CD pipeline to automatically manage versioning and release new versions based on commits.

---

## 🛠️ How to Run the Project

1. Ensure **Java 17** or higher is installed.
2. Configure the PostgreSQL database and update credentials in `application.properties` or `application.yml`.
3. Run the following commands:
   ```bash
   ./gradlew clean build
   ./gradlew bootRun
   ```

---

## 🧪 Tests
To run tests, use:

```bash
./gradlew test
```

## 📖 API Documentation
Access the API documentation at: `http://localhost:8080/swagger-ui.html` or `http://localhost:8080/swagger-ui/index.html`.

## 📝 License
This project is licensed under the MIT License.