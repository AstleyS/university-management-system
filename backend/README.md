# University Management System (UMS) Backend

This folder contains the backend application of **UMS**, a university management system built with Spring Boot.

The backend provides a RESTful API responsible for managing academic entities, business logic, authentication, authorization, and communication with the PostgreSQL database.

The project follows a layered architecture to maintain separation of concerns, scalability, and maintainability.

---

# Technology Stack
## Core
- Java 17+
- Spring Boot
- Spring Data JPA / Hibernate
- Gradle

## Database
- PostgreSQL 16
- Docker

## Security
- Spring Security
- JWT Authentication
- BCrypt password encryption

## Testing
- JUnit
- Spring Boot Test
- Mockito

---
# Architecture Overview

The backend follows a layered architecture:

```
Controller Layer
        |
        v
Service Layer
        |
        v
Repository Layer
        |
        v
Database
```

Each layer has a specific responsibility:

## Controller Layer

Responsible for:
- Receiving HTTP requests
- Validating input
- Returning HTTP responses
- Mapping API endpoints

Controllers do not contain business logic.

---

## Service Layer

Responsible for:
- Business logic
- Data validation rules
- Entity operations
- Exception handling decisions

The service layer acts as the bridge between controllers and repositories.

---

## Repository Layer

Responsible for:
- Database communication
- CRUD operations
- Query execution using Spring Data JPA

---

# UML Diagram

> TODO: Add UML class diagram exported from draw.io

The diagram will represent:
- Entity relationships
- Database structure
- Main application components

---

# Project Structure

```
src/main/java/com/ums/ums_backend

├── controller
│   └── REST API endpoints
│
├── service
│   └── Business logic
│
├── repository
│   └── Database access layer
│
├── entity
│   └── JPA database entities
│
├── dto
│   └── Data transfer objects
│
├── mapper
│   └── Entity ↔ DTO conversion
│
├── security
│   └── Authentication and authorization
│
├── exception
│   └── Global exception handling
```

---

# Database Model

The application uses PostgreSQL as the relational database.

Main entities:

## Faculty
Represents a university faculty.

Example:
```
Faculty
    |
    |
    +-- Departments
```

---

## Department
Represents an academic department.

Relationship:
```
Faculty 1 ---- N Department
```

---

## CourseRoute
Represents a university course.

Relationship:
```
Department 1 ---- N CourseRoute
```

A join table is used to represent the many-to-many relationship.

---

## Student
Represents students enrolled in the university.

---

## Professor
Represents professors teaching courses.

---

## Enrollment
Represents the relationship between students and courses.

Contains:
- Enrollment date
- Status
- Grade

Relationship:
```
Student 1 ---- N Enrollment N ---- 1 CourseRoute
```

---

## Semester
Represents academic periods.

Examples:
- Fall 2026
- Spring 2027

---

# Data Transfer Objects (DTO)
The application uses DTOs to separate the API representation from database entities.

Flow:

```
HTTP Request

      |
      v

Request DTO

      |
      v

Mapper

      |
      v

Entity

      |
      v

Database
```

Responses follow the reverse process:

```
Database

      |
      v

Entity

      |
      v

Mapper

      |
      v

Response DTO

      |
      v

Client
```

Benefits:
- Prevent exposing database structure
- Control API responses
- Improve maintainability
- Reduce coupling

---

# Authentication and Authorization
The backend implements JWT-based authentication.

Authentication flow:

```
User Registration

        |
        v

Password encrypted with BCrypt

        |
        v

User stored in database


----------------------------


User Login

        |
        v

Credentials validated

        |
        v

JWT generated

        |
        v

Client sends JWT with requests

        |
        v

Security filter validates token
```

---

## Security Components
Located in:

```
security/
```

Includes:
- Security configuration
- JWT authentication filter
- JWT service
- UserDetails implementation
- Authentication services

---

## Roles
The system supports role-based authorisation.

Current roles:
- ADMIN
- PROFESSOR
- STUDENT

Roles are used to restrict access to specific operations.

Example:
```
ADMIN

- Create courses
- Manage users


PROFESSOR

- Manage assigned courses
- Submit grades


STUDENT

- View courses
- Manage enrollments
```

---

# Validation
Validation is implemented at multiple levels.

## DTO Validation
Responsible for validating incoming requests.

Examples:
- Required fields
- String length
- Numeric limits

Example:
```
CourseRoute name cannot be empty
Credits must be positive
```

---

## Business Validation
Implemented in services.

Examples:
- Username cannot already exist
- Student cannot enroll twice
- CourseRoute must exist before enrollment

---

# Exception Handling
The backend uses centralised exception handling.

Structure:

```
exception/

├── GlobalExceptionHandler
├── ErrorResponse
├── ResourceNotFoundException
├── AlreadyExistsException
└── BadRequestException
```

Exception flow:

```
Service

   |
   v

Throw Custom Exception

   |
   v

GlobalExceptionHandler

   |
   v

HTTP Error Response
```

Example:

```json
{
    "timestamp": "2026-08-05T10:00:00",
    "status": 404,
    "error": "Not Found",
    "message": "CourseRoute not found"
}
```

---

# Database Setup
The database is containerised using Docker.

The PostgreSQL container is configured in:

```
docker-compose.yml
```

Configuration:

```
Database:
ums

Username:
postgres

Port:
5433
```

Start database:

```bash
docker compose up -d
```

---

# Database Seeding
Default data is automatically inserted when the database is created.

Seeded data includes:
- Faculties
- Departments
- Semesters

Location:

```
database/init/
```

The initialisation scripts are executed automatically by PostgreSQL during the first container startup.

---

# Docker Support

The backend is fully containerised using a multi-stage Docker build.

The build process consists of two stages:

1. **Build stage**
    - Compiles the Spring Boot application using Gradle.
    - Produces the executable JAR inside the container.

2. **Runtime stage**
    - Uses a lightweight Java Runtime Environment (JRE) image.
    - Copies only the generated JAR from the build stage.
    - Exposes port `8080` and starts the application.

This approach keeps the final image smaller, improves build reproducibility, and removes the need to have Java or Gradle installed on the host machine.

### Build the backend image

From the project root:

```bash
docker compose build backend
```

### Run the backend

```bash
docker compose up backend
```

Or build and start all services together:

```bash
docker compose up --build
```

This command will:
- Build the backend image
- Start the PostgreSQL container
- Start the backend container

---

# Running the Backend
## Requirements

Install:
- Java 17+
- Docker

## Spring Boot Dependencies
The project uses the following Spring Boot starters and libraries:

### Core
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation

### Security
- Spring Boot Starter Security
- JSON Web Token (JJWT)
- BCrypt Password Encoder

### Database
- PostgreSQL Driver
- Hibernate ORM

### Development
- Lombok
- Spring Boot DevTools

### Testing
- Spring Boot Starter Test
- Spring Security Test
- Mockito
- JUnit 5

---

## Start Database

From project root:
```docker compose up -d```

---

## Run Spring Boot
Inside the backend folder:

```./gradlew bootRun```

The API will run at:

```http://localhost:8080```

---

# API Documentation

The backend exposes a RESTful API organized by resource and business operations.

Base URL:

```
http://localhost:8080
```

---

## Authentication

```
/api/auth
```

Handles user authentication and account creation.

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/auth/register` | Register a new user | Public |
| POST | `/api/auth/login` | Authenticate user and receive JWT token | Public |

Authentication flow:

```
POST /api/auth/login

        |
        v

Receive JWT token

        |
        v

Include token in requests:

Authorization: Bearer <token>
```

---

# Users

```
/api/users
```

User account management.

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/users` | Retrieve all users | ADMIN |
| GET | `/api/users/{id}` | Retrieve user by ID | ADMIN |
| POST | `/api/users` | Create user | ADMIN |
| PUT | `/api/users/{id}` | Update user | ADMIN |
| DELETE | `/api/users/{id}` | Delete user | ADMIN |

---

# Students

```
/api/students
```

Student management.

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/students` | Retrieve all students | ADMIN |
| GET | `/api/students/{id}` | Retrieve student details | ADMIN / PROFESSOR |
| POST | `/api/students` | Create student | ADMIN |
| PUT | `/api/students/{id}` | Update student | ADMIN |
| DELETE | `/api/students/{id}` | Delete student | ADMIN |

---

# Professors

```
/api/professors
```

Professor management.

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/professors` | Retrieve all professors | ADMIN |
| GET | `/api/professors/{id}` | Retrieve professor details | ADMIN |
| POST | `/api/professors` | Create professor | ADMIN |
| PUT | `/api/professors/{id}` | Update professor | ADMIN |
| DELETE | `/api/professors/{id}` | Delete professor | ADMIN |

---

# Courses

```
/api/courses
```

CourseRoute management.

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/courses` | Retrieve all courses | ADMIN / PROFESSOR / STUDENT |
| GET | `/api/courses/{id}` | Retrieve course details | ADMIN / PROFESSOR / STUDENT |
| POST | `/api/courses` | Create course | ADMIN |
| PUT | `/api/courses/{id}` | Update course | ADMIN |
| DELETE | `/api/courses/{id}` | Delete course | ADMIN |

---

# Enrollments

```
/api/enrollments
```

Student course enrollment management.

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/enrollments` | Retrieve all enrollments | ADMIN / PROFESSOR |
| GET | `/api/enrollments/{id}` | Retrieve enrollment details | ADMIN / PROFESSOR |
| GET | `/api/enrollments/student/{studentId}` | Retrieve student enrollments | STUDENT |
| POST | `/api/enrollments` | Create enrollment | ADMIN |
| PUT | `/api/enrollments/{id}` | Update enrollment | ADMIN |
| PATCH | `/api/enrollments/{id}/grade` | Update student grade | ADMIN / PROFESSOR |
| DELETE | `/api/enrollments/{id}` | Delete enrollment | ADMIN |

---

# Common Response Codes

| Code | Meaning |
|------|---------|
| 200 OK | Request completed successfully |
| 201 Created | Resource successfully created |
| 204 No Content | Resource successfully deleted |
| 400 Bad Request | Invalid request data |
| 401 Unauthorized | Missing or invalid authentication |
| 403 Forbidden | Authenticated user does not have permission |
| 404 Not Found | Resource does not exist |

---

# Authentication Header

Protected endpoints require a JWT token.

Example:

```http
GET /api/courses

Authorization: Bearer eyJhbGciOiJIUzI1Ni...
```

The token contains the authenticated user's identity and role, which are validated by the JWT authentication filter before accessing protected resources.

# Testing

The backend uses Spring testing support.

Current testing strategy:

- Integration tests for API flows
- Authentication flow testing
- Database interaction testing

Example scenarios:

```
Register user

        ↓

Login

        ↓

Receive JWT

        ↓

Access protected endpoint
```

---

# Future Improvements

Planned backend improvements:

- More complete integration tests
- Advanced search endpoints
- AI-powered natural language queries
- Dockerized backend service
- CI/CD pipeline
- Production deployment configuration
