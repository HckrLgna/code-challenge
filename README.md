# Backend Challenge - Getting Started

This guide will help you set up and run the application, including database dockerization.

## Prerequisites
- Java 17 or higher
- Maven
- Docker & Docker Compose

## 1. Clone the Repository
```
git clone <your-repo-url>
cd backend_challenge
```

## 2. Start the PostgreSQL Database with Docker
A ready-to-use PostgreSQL configuration is provided in `docker-compose.yml`.

Run:
```
docker-compose up -d
```
This will start a PostgreSQL container with the required extensions and initial configuration.

## 3. Configure Application Properties
Edit `src/main/resources/application.properties` if needed:
- Set your database connection URL, username, and password to match the Docker setup.

Example:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_user
spring.datasource.password=your_password
```

## 4. Build the Application
```
mvn clean install
```

## 5. Run the Application
```
mvn spring-boot:run
```

## 6. Access Swagger UI
Once the app is running, open:
```
http://localhost:8080/swagger-ui.html
```
You can test all endpoints from here.

## 7. Common Commands
- **Stop database:**
  ```
  docker-compose down
  ```
- **Rebuild database:**
  ```
  docker-compose down -v && docker-compose up -d
  ```

## 8. Troubleshooting
- If you get database connection errors, check Docker logs:
  ```
  docker-compose logs
  ```
- Make sure ports 5432 (Postgres) and 8080 (Spring Boot) are free.
- Ensure your database credentials match those in `application.properties`.

## 9. Useful Links
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Swagger](https://swagger.io/tools/swagger-ui/)
- [Docker Compose](https://docs.docker.com/compose/)

---
For any issues, check the logs or reach out to your team lead.
