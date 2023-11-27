    BEGIN;

    CREATE TABLE PERSONS (
        id SERIAL PRIMARY KEY,
        prefix_name VARCHAR(50),
        first_name VARCHAR(100) NOT NULL,
        middle_name VARCHAR(100),
        last_name VARCHAR(100) NOT NULL,
        suffix_name VARCHAR(50),
        birthdate DATE,
        address VARCHAR(255),
        phone_number VARCHAR(20),
        email VARCHAR(100)
    );

    CREATE TABLE ROLES (
        person_id INT,
        role_type VARCHAR(50),  -- student, faculty, staff
        PRIMARY KEY (person_id, role_type),
        FOREIGN KEY (person_id) REFERENCES PERSONS(id)
    );

    CREATE TABLE STUDENTS (
        person_id INT PRIMARY KEY,
        student_type VARCHAR(50),   -- undergraduate, graduate
        major VARCHAR(100),
        minor VARCHAR(100),
        enrollment_status VARCHAR(50),  -- enrolled, leave of absence, graduated
        advisor_id INT,
        FOREIGN KEY (person_id) REFERENCES PERSONS(id),
        FOREIGN KEY (advisor_id) REFERENCES PERSONS(id)
    );

    CREATE TABLE EMPLOYEES (
        person_id INT PRIMARY KEY,
        department VARCHAR(100),
        title VARCHAR(100),
        employment_type VARCHAR(50),    -- full-time, part-time, contract, temporary, 
                                        -- volunteer, intern/trainee, seasonal, other
        employment_status VARCHAR(50),  -- active, retired, resigned, terminated, on leave, contract ended, suspended
                                        -- furloughed, laid off, probationary
        office_location VARCHAR(100),
        FOREIGN KEY (person_id) REFERENCES PERSONS(id)
    );

    CREATE TABLE FACULTY (
        employee_id INT PRIMARY KEY,
        faculty_type VARCHAR(50),   -- professor, associate professor, assistant professor, 
                                    -- lecturer, instructor, adjunct, emeritus, visiting scholar, visiting professor
        FOREIGN KEY (employee_id) REFERENCES EMPLOYEES(person_id)
    );

    CREATE TABLE STAFF (
        employee_id INT PRIMARY KEY,
        staff_type VARCHAR(50), -- Administration, Information Technology, Research,
                                -- Library, Maintenance and Facilities, Health and Counseling,
                                -- Student Services, Security, Campus Police, Contractors,
                                -- Consultants, Volunteers, Student Workers
        FOREIGN KEY (employee_id) REFERENCES EMPLOYEES(person_id)
    );

    CREATE TABLE user_login (
        id SERIAL PRIMARY KEY,
        username VARCHAR(200) NOT NULL,
        password VARCHAR(200) NOT NULL
    );

    INSERT INTO user_login (username, password) VALUES ('admin', 'admin');

    COMMIT;