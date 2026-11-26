# Smart Ward Management System

A Java / Spring Boot web application for urban ward management, inspired by the **Swachh Survekshan** initiative. Citizens report civic issues and give feedback, and administrators track complaints, manage ward data and view analytics.

## Features

### Citizens
- Dashboard with submitted complaints and feedback
- Complaint form with category and priority
- Ward directory with local facilities (hospitals, schools, banks and more)
- Feedback on ward services

### Administrators
- Analytics dashboard (complaint status, category distribution, feedback trends)
- Create and update wards, services and users
- Review and resolve citizen complaints

## Tech Stack
- Java 17, Spring Boot 3.2.4
- Spring Security with role-based access and JWT
- Spring Data JPA, MySQL
- Thymeleaf templates, plain CSS
- Lombok, Maven, Docker

## Getting Started

### Prerequisites
- JDK 17+
- MySQL server
- Maven (or the included wrapper if present)

### Run locally
```bash
git clone https://github.com/Raman-1166/smart-ward-management.git
cd smart-ward-management
mvn spring-boot:run
```
The app starts at `http://localhost:8080`. By default it uses a local MySQL database named `smart_ward_management_db`.

### Configuration
Set these environment variables to override the defaults (for Docker, Render or any host):

| Variable | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` | JDBC URL, starts with `jdbc:mysql://` |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `JWT_SECRET` | Secret used to sign JWT tokens |
| `PORT` | Optional, defaults to 8080 |

Never commit real credentials. Keep them in environment variables.

## Project Structure
```text
src/main/java/com/ward/system/
├── config/       # Security configuration
├── controller/   # MVC and REST API controllers
├── exception/    # Custom exceptions and global handler
├── model/        # JPA entities
├── repository/   # Data access layer
├── security/     # JWT filter and user details
└── service/      # Business logic
```

## Author
Raman Chourasiya, Java developer from Indore. [GitHub](https://github.com/Raman-1166)
