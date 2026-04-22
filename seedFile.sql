-- =========================
-- DROP TABLES (for reset)
-- =========================
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS contact_types;
DROP TABLE IF EXISTS cases;
DROP TABLE IF EXISTS transports;
DROP TABLE IF EXISTS branches;
DROP TABLE IF EXISTS types;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS profiles;

-- =========================
-- TABLE: profiles
-- =========================
CREATE TABLE profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number BIGINT,
    address VARCHAR(255),
    cpr BIGINT,
    birth_date DATE,
    profile_imageurl VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

-- =========================
-- TABLE: users
-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(50),
    status VARCHAR(50),
    verified BOOLEAN,
    profile_id BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);

-- =========================
-- TABLE: types
-- =========================
CREATE TABLE types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    priority INT,
    created_at DATETIME,
    updated_at DATETIME
);

-- =========================
-- TABLE: branches
-- =========================
CREATE TABLE branches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    created_at DATETIME,
    updated_at DATETIME
);

-- =========================
-- TABLE: transports
-- =========================
CREATE TABLE transports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    description VARCHAR(255),
    register_number VARCHAR(255),
    reserved BOOLEAN,
    coverage_in_meter DOUBLE,
    speed DOUBLE,
    branch_id BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

-- =========================
-- TABLE: cases
-- =========================
CREATE TABLE cases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    latitude DOUBLE,
    longitude DOUBLE,
    coverage_in_meter DOUBLE,
    calculated_priority DOUBLE,
    calculated_distance DOUBLE,
    calculated_time DOUBLE,
    notes TEXT,
    started_at DATETIME,
    ended_at DATETIME,
    people_inside BOOLEAN,
    suggested_branch_id BIGINT,
    type_id BIGINT,
    status VARCHAR(50),
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (suggested_branch_id) REFERENCES branches(id),
    FOREIGN KEY (type_id) REFERENCES types(id)
);

-- =========================
-- TABLE: contact_types
-- =========================
CREATE TABLE contact_types (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    method VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);

-- =========================
-- TABLE: contact
-- =========================
CREATE TABLE contact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    priority INT,
    value VARCHAR(255),
    case_id BIGINT,
    contact_type BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (case_id) REFERENCES cases(id),
    FOREIGN KEY (contact_type) REFERENCES contact_types(id)
);

-- =========================
-- TABLE: groups (history)
-- =========================
CREATE TABLE groups (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    case_id BIGINT,
    user_id BIGINT,
    branch_id BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (case_id) REFERENCES cases(id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);

-- =========================
-- INSERT DATA
-- =========================

-- TYPES
INSERT INTO types (id, name, priority) VALUES
(1, 'Small Fire', 1),
(2, 'Medium Fire', 2),
(3, 'Large Fire', 3);

-- BRANCHES
INSERT INTO branches (id, name, latitude, longitude) VALUES
(1, 'Central Station', 26.2235, 50.5876),
(2, 'North Station', 26.2500, 50.6000);

-- TRANSPORTS
INSERT INTO transports (id, type, description, register_number, reserved, coverage_in_meter, speed, branch_id) VALUES
(1, 'Truck', 'Main fire truck', 'FT-001', false, 5000, 80, 1),
(2, 'Rescue Van', 'Rescue vehicle', 'RV-002', false, 3000, 100, 2);

-- PROFILES
INSERT INTO profiles (id, first_name, last_name, phone_number, address, cpr, birth_date)
VALUES (1, 'Ali', 'Hassan', 12345678, 'Manama', 123456789, '1990-01-01');

-- USERS
INSERT INTO users (id, email, password, role, status, verified, profile_id)
VALUES (1, 'admin@test.com', 'password', 'ADMIN', 'Active', true, 1);

-- CONTACT TYPES
INSERT INTO contact_types (id, method) VALUES
(1, 'PHONE'),
(2, 'EMAIL');

-- CASES
INSERT INTO cases (
    id, name, description, latitude, longitude,
    coverage_in_meter, notes, started_at,
    people_inside, type_id, suggested_branch_id, status
) VALUES
(1, 'House Fire', 'Residential fire', 26.2200, 50.5800,
 1000, 'Urgent', NOW(),
 true, 3, 1, 'CREATED'),

(2, 'Car Fire', 'Vehicle fire', 26.2400, 50.5900,
 500, 'Low risk', NOW(),
 false, 1, 2, 'CREATED');

-- CONTACTS
INSERT INTO contact (id, priority, value, case_id, contact_type) VALUES
(1, 1, '+97312345678', 1, 1),
(2, 2, 'report@test.com', 2, 2);

-- HISTORY
INSERT INTO groups (id, case_id, user_id, branch_id)
VALUES (1, 1, 1, 1);
