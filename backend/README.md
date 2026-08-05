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

## Course
Represents a university course.

Relationship:
```
Department 1 ---- N Course
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
Student 1 ---- N Enrollment N ---- 1 Course
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
Course name cannot be empty
Credits must be positive
```

---

## Business Validation
Implemented in services.

Examples:
- Username cannot already exist
- Student cannot enroll twice
- Course must exist before enrollment

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
    "message": "Course not found"
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

# Running the Backend
## Requirements

Install:

- Java 17+
- Docker

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

Main API groups:

```
/api/auth

Authentication endpoints


/api/students

Student management


/api/professors

Professor management


/api/courses

Course management


/api/enrollments

Enrollment management
```

---

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
