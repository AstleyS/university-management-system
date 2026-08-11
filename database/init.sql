-- Faculty Table
CREATE TABLE IF NOT EXISTS faculty (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    code VARCHAR(10) NOT NULL,
    description VARCHAR(250)
);

-- Department Table
CREATE TABLE IF NOT EXISTS department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    code VARCHAR(10) NOT NULL,
    description VARCHAR(250),
    faculty_id BIGINT NOT NULL REFERENCES faculty(id)
);

-- Semester Table
CREATE TABLE IF NOT EXISTS semester (
    id SERIAL PRIMARY KEY,
    year INTEGER NOT NULL,
    term VARCHAR(255) NOT NULL,
    UNIQUE(year, term)
);

-- User Table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Professor Table
CREATE TABLE IF NOT EXISTS professor (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Student Table
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users(id),
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    date_of_birth DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

-- Course Table
CREATE TABLE IF NOT EXISTS course (
    id SERIAL PRIMARY KEY,
    department_id BIGINT NOT NULL REFERENCES department(id),
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(250),
    credits DOUBLE PRECISION NOT NULL
);

-- Enrollment Table
CREATE TABLE IF NOT EXISTS enrollment (
    id SERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES student(id),
    course_id BIGINT NOT NULL REFERENCES course(id),
    semester_id BIGINT NOT NULL REFERENCES semester(id),
    enrollment_date DATE NOT NULL,
    enrollment_status VARCHAR(20) NOT NULL,
    UNIQUE(student_id, course_id, semester_id)
);

-- CourseInstructor Table
CREATE TABLE IF NOT EXISTS course_instructor (
    id SERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES course(id),
    professor_id BIGINT NOT NULL REFERENCES professor(id),
    UNIQUE(course_id, professor_id)
);


-- =========================================
-- SEED DATA
-- =========================================

-- Faculties
INSERT INTO faculty (name, code, description)
VALUES
('Faculty of Computer Science', 'FCS', 'Computer Science and Engineering programs');


-- Departments
INSERT INTO department (name, code, description, faculty_id)
VALUES
('Software Engineering', 'SED', 'Software engineering and development', 1),
('Artificial Intelligence', 'AID', 'Artificial intelligence and machine learning', 1);


-- Semesters
INSERT INTO semester (year, term)
VALUES
(2026, 'SPRING'),
(2026, 'FALL');


/*
-- Users
INSERT INTO users (username, password)
VALUES
('professor1', 'password123'),
('professor2', 'password123'),
('professor3', 'password123'),
('professor4', 'password123'),
('student1', 'password123'),
('student2', 'password123'),
('student3', 'password123'),
('student4', 'password123');


-- Professors
INSERT INTO professor (
    user_id,
    first_name,
    last_name,
    gender,
    date_of_birth,
    email
)
VALUES
(1, 'Professor', 'One', 'FEMALE', '1980-05-15', 'professor1@university.edu'),
(2, 'Professor', 'Two', 'MALE', '1975-08-22', 'professor2@university.edu'),
(3, 'Professor', 'Three', 'FEMALE', '1985-03-10', 'professor3@university.edu'),
(4, 'Professor', 'Four', 'MALE', '1978-12-05', 'professor4@university.edu');


-- Students
INSERT INTO student (
    user_id,
    first_name,
    last_name,
    gender,
    date_of_birth,
    email
)
VALUES
(5, 'Student', 'One', 'FEMALE', '2004-01-20', 'student1@university.edu'),
(6, 'Student', 'Two', 'MALE', '2003-06-15', 'student2@university.edu'),
(7, 'Student', 'Three', 'MALE', '2002-11-30', 'student3@university.edu'),
(8, 'Student', 'Four', 'FEMALE', '2004-04-25', 'student4@university.edu');
*/

-- Courses
INSERT INTO course (
    department_id,
    code,
    name,
    description,
    credits
)
VALUES
(1, 'SE101', 'Software Fundamentals', 'Introduction to software engineering', 3.0),
(1, 'SE201', 'Advanced Algorithms', 'Study of advanced algorithms', 4.0),
(2, 'AI101', 'Machine Learning', 'Fundamentals of machine learning', 3.0),
(2, 'AI202', 'NLP Techniques', 'Natural language processing', 4.0);

/*
-- Enrollments
INSERT INTO enrollment (
    student_id,
    course_id,
    semester_id,
    enrollment_date,
    enrollment_status
)
VALUES
(1, 1, 1, '2026-01-10', 'ACTIVE'),
(1, 2, 1, '2026-01-10', 'PASSED'),
(2, 3, 1, '2026-01-12', 'ACTIVE'),
(2, 4, 2, '2026-01-12', 'ACTIVE'),
(3, 1, 1, '2026-01-10', 'COMPLETED'),
(4, 3, 1, '2026-01-15', 'FAILED');


-- Course Instructors
INSERT INTO course_instructor (
    course_id,
    professor_id
)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

 */