CREATE DATABASE arkoEvents;
use arkoEvents;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    account_status VARCHAR(20) DEFAULT 'active',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reset_code VARCHAR(10),
    reset_code_timestamp TIMESTAMP,
    profile_image VARCHAR(300),
    role VARCHAR(20) NOT NULL DEFAULT 'user'
);


SELECT * FROM users;

INSERT INTO users(username,email,password,phone,role)
			VALUES("ashu","ashu@gmail.com","abcd","1234561234","admin");