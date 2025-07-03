CREATE DATABASE arkoEvents;
USE arkoEvents;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    account_status VARCHAR(20) NOT NULL DEFAULT 'active' CHECK (account_status IN ('active', 'banned', 'deactivated')),
    profile_image VARCHAR(255),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(10) NOT NULL DEFAULT 'user' CHECK (role IN ('user', 'admin'))
);


CREATE TABLE events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    description TEXT,
    category VARCHAR(100),
    type VARCHAR(20) CHECK (type IN ('Private', 'Public')),
    ticket_type VARCHAR(20) CHECK (ticket_type IN ('Paid', 'Free')),
    event_status VARCHAR(20) DEFAULT 'Draft' CHECK (event_status IN ('Draft', 'Published')),
    event_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    rsvp_deadline DATE,
    price DECIMAL(10, 2) DEFAULT 0.00,
    tickets_available INT DEFAULT 0,
    tickets_sold INT DEFAULT 0,
    banner VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    event_id INT NOT NULL,
    user_id INT NOT NULL,
    ticket_count INT NOT NULL CHECK (ticket_count > 0),
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rating INT CHECK (rating BETWEEN 1 AND 5),
	feedback TEXT,
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE(event_id, user_id) 
);





-- procedure to get events by filtering
DELIMITER //

CREATE PROCEDURE getEventsByTicketTypeAndSearch(
    IN event_type VARCHAR(10),
    IN search_query VARCHAR(255)
)
BEGIN
    IF event_type = 'All' THEN
        SELECT * FROM events
        WHERE title LIKE CONCAT('%', search_query, '%');
    ELSE
        SELECT * FROM events
        WHERE ticket_type = event_type
          AND title LIKE CONCAT('%', search_query, '%');
    END IF;
END //

DELIMITER ;


-- pocedure to get feedbacks
DELIMITER //

CREATE PROCEDURE getEventFeedbacks(IN input_event_id INT)
BEGIN
    SELECT 
        b.user_id,
        b.event_id,
        u.username,
        e.title AS event_name,
        b.rating,
        b.feedback
    FROM bookings b
    JOIN users u ON b.user_id = u.id
    JOIN events e ON b.event_id = e.id
    WHERE b.event_id = input_event_id
      AND b.feedback IS NOT NULL;
END //

DELIMITER ;

SELECT * FROM users;
SELECT * FROM events;
SELECT * FROM bookings;