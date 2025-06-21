-- Create the database
CREATE DATABASE ArkoEvent;
USE ArkoEvent;

-- Create users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    account_status VARCHAR(20) DEFAULT 'active',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    profile_image VARCHAR(300),
    role VARCHAR(20) NOT NULL DEFAULT 'user'
);

-- Create events table
CREATE TABLE events (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT,
    event_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    price DOUBLE NOT NULL DEFAULT 0.0,
    tickets_available INT NOT NULL DEFAULT 0,
    tickets_sold INT NOT NULL DEFAULT 0,
    category VARCHAR(100),
    type ENUM('Private', 'Public') NOT NULL DEFAULT 'Public',
    ticket_type ENUM('Paid', 'Free') NOT NULL DEFAULT 'Paid',
    rsvp_deadline DATE,
    event_status ENUM('Draft', 'Published') NOT NULL DEFAULT 'Draft'
);

-- Create bookings table
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    ticket_count INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Confirmed',
    payment_status VARCHAR(20) DEFAULT 'Paid',
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create feedback table
CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT,
    user_id INT,
    rating INT,
    submitted_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES events(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Stored procedure for fetching feedback on past events
DELIMITER //
CREATE PROCEDURE GetAllFeedbacks()
BEGIN
    SELECT 
        f.id,
        f.rating,
        f.submitted_at,
        u.username AS user_name,
        e.title AS event_name
    FROM 
        feedback f
    JOIN users u ON f.user_id = u.id
    JOIN events e ON f.event_id = e.id
    WHERE e.event_date < CURDATE();
END //
DELIMITER ;

SELECT * FROM users;
SELECT * FROM events;
SELECT * FROM feedback;
SELECT * FROM users WHERE account_status = 'active';

INSERT INTO users(username,email,password,phone,role)
			VALUES("ashu","ashu@gmail.com","abcd","1234561234","admin");