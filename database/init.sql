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
    name VARCHAR(50) NOT NULL,
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

-- Faculties
INSERT INTO faculty (name, code, description)
VALUES
    ('Faculty of Computer Science', 'FCS',
     'Computer Science and Engineering programs');

-- Departments
INSERT INTO department (name, code, description, faculty_id)
VALUES
    (
     'Software Engineering Department',
     'SED',
     'Software engineering and application development',
     1),

    (
     'Artificial Intelligence Department',
     'AID',
     'Artificial intelligence and machine learning',
     1);

-- Semesters
INSERT INTO semester (year, term)
VALUES
    (2026, 'SPRING'),

    (2026, 'FALL');