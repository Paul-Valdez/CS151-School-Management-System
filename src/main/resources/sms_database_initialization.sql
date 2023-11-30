    BEGIN;

    -- Drop all tables before recreation
    DROP TABLE IF EXISTS PERSONS CASCADE;
    DROP TABLE IF EXISTS user_login CASCADE;

    -- Drop PERSONS id number sequence initial value and recreate
    DROP SEQUENCE IF EXISTS persons_id_seq CASCADE;
    CREATE SEQUENCE persons_id_seq START WITH 100000001;

    CREATE TABLE PERSONS (
        id INTEGER PRIMARY KEY DEFAULT nextval('persons_id_seq'),
        prefix_name VARCHAR(50),
        first_name VARCHAR(100) NOT NULL,
        middle_name VARCHAR(100),
        last_name VARCHAR(100),
        suffix_name VARCHAR(50),
        birthdate DATE,
        address VARCHAR(255),
        phone_number VARCHAR(20),
        email VARCHAR(254)
    );

    CREATE TABLE user_login (
        id SERIAL PRIMARY KEY,
        username VARCHAR(200) NOT NULL,
        password VARCHAR(200) NOT NULL
    );

    INSERT INTO user_login (username, password) VALUES ('admin', 'admin');

    COMMIT;