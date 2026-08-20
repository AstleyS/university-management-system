# University Management System (UMS)

This is a full-stack university management system (UMS) designed to simulate
the management of academic operations such as students, professors, courses, enrollments, and grades.

**Author:**

Astley Santos. Contact me via [Linkedin](https://www.linkedin.com/in/astley-santos/).


### Note: This project is still under development (last update: 17th-24th of August)

---
# Introduction
The project aims to demonstrate a complete software engineering workflow, including database design, backend architecture, and modern frontend development.

The main objective is to build a realistic application while exploring how AI can be integrated and improve
user interaction through natural language-based search and assistance.
---

# Running this project

The project is containerised using Docker Compose.

Requirements:

- Docker
- Java 17+ (only required if running the backend manually)
- Node.js and npm (only required for the frontend)

## Run the complete application

From the project root:

```bash
docker compose up --build
```

This will:

- Start the PostgreSQL database container
- Build the backend Docker image
- Start the backend application

The backend API will be available at:

```http
http://localhost:8080
```

---

## Run components individually

### Database

Start only the PostgreSQL container:

```bash
docker compose up postgres -d
```

---

### Backend

Navigate to the backend folder:

```bash
cd backend
```

Start only the backend / Spring Boot application container:

```bash
docker compose up backend -d
```

The API will be available at:

```http
http://localhost:8080
```

More backend details:

***See Backend [README.MD](./backend/README.md)***

---

## Frontend Setup

Navigate to the frontend folder:

```cd frontend```

Install dependencies:

```npm install```

Run Angular:

```ng serve```

The frontend will be available at:

```http://localhost:4200```


# Features
- Manage students
- Manage professors
- Manage courses
- Manage departments and faculties
- Manage course enrollments
- User registration and login
- Role-based authorisation - ADMIN, PROFESSOR, AND STUDENT

# AI integration 

UMS will include an AI-powered natural language search feature

The goal is to allow users to interact with the system using natural language queries
instead of navigating manually though multiple screens

Examples
> Show me all courses from the Computer Science department

> Which students are enrolled in Database Systems?

> What courses does Professor X teach?

The AI layer will be designed as an assistant on top of the existing application data and business logic.

# Architectural Overview
The application follows a standard full-stack architecture

**USER** → **FRONTEND**
----- **REST API** ----- 
**BACKEND** → **Database** (containerised using Docker)

The backend exposes RESTful APIs consumed by the frontend. 
The database is containerised using Docker to provide a consistent development environment.

# Technological Stack
## Database
- PostgreSQL

## Backend
- Java 
- Spring Boot 
- Spring Data JPA / Hibernate 
- Spring Security 
- JWT Authentication 
- Gradle 
- JUnit / Spring Testing with Mockito

## Frontend
- Angular 
- TypeScript 
- HTML/CSS

## AI
- Natural language querying
- AI-assisted interaction layer

# Project Structure

ums/
├── backend/
│
│   Spring Boot application
│   REST API
│   Authentication
│   Business logic
│
├── frontend/
│
│   Angular application
│   User interface
│
├── database/
│
│   Database initialisation scripts
│
└── README.md

For detailed backend architecture, setup instructions, API documentation, and implementation details:

***See Backend [README.MD](./backend/README.md)***


# Development Workflow

The project is developed incrementally:

## Phase 1 - Backend Foundation

- Database modelling
- Entity relationships
- Repository layer
- Service architecture
- REST API implementation 
- DTO mapping

## Phase 2 - Security

- User registration 
- Login 
- JWT authentication 
- Role-based authorisation

## Phase 3 - Backend Quality

- DTO validation
- Exception handling
- Integration testing setup

## Phase 4 - Frontend

Planned:

- Angular application structure
- Authentication flow
- Dashboard interfaces
- CRUD interfaces

## Phase 5 - AI Integration

Planned:

- Natural language queries 
- AI assistant 
- Intelligent data retrieval 


# Future Improvements

Possible future additions:

- Advanced dashboard analytics
- Notifications system
- File/document management
- More advanced AI agents for different user roles 
- Deployment pipeline with CI/CD 
- Cloud deployment