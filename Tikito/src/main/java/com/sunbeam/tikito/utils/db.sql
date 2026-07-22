-- DROP DATABASE IF EXISTS tikito_db;
-- CREATE DATABASE tikito_db;
USE tikito_db;

CREATE TABLE users(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(80),
    last_name VARCHAR(80),
    birth_date Date,
    email VARCHAR(80) UNIQUE,
    image_name VARCHAR(80),
    password VARCHAR(200),
    phone CHAR(10),
    role VARCHAR(20) NOT NULL CHECK (role IN ('user', 'admin')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE bookings(
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    show_id INT,
    total_amt DECIMAL(10,2),
    payment_status VARCHAR(25),
    booking_status VARCHAR(50),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
   
);

CREATE TABLE booked_seats (
    booked_seat_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    show_id INT NOT NULL,
    seat_id INT NOT NULL,

    CONSTRAINT uk_show_seat UNIQUE (show_id, seat_id)

    -- FOREIGN KEY (booking_id) REFERENCES booking(booking_id),
    -- FOREIGN KEY (show_id) REFERENCES show(show_id),
    -- FOREIGN KEY (seat_id) REFERENCES seat(seat_id)
);

CREATE TABLE seats(
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    venue_id INT,
    seat_no VARCHAR(15),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE revues(
    revues_id INT PRIMARY KEY AUTO_INCREMENT,
    old_movie_id INT ,
    user_id INT,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE events(
    event_id INT PRIMARY KEY AUTO_INCREMENT,
    event_name VARCHAR(100) NOT NULL,
    event_type  VARCHAR(100) NOT NULL,
    event_description TEXT,
    event_duration_min INT,
    age_restriction INT,
    poster_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE shows(
    show_id INT PRIMARY KEY AUTO_INCREMENT,
    venue_id INT,
    event_id INT,
    price DECIMAL(4,2),
    is_eighteen_plus boolean NOT NULL,
    show_date DATE,
    show_start_time TIME,
    show_end_time TIME,
   show_language VARCHAR(15),
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE venues(
    venue_id INT PRIMARY KEY AUTO_INCREMENT,
    venue_name VARCHAR(80),
    venue_address VARCHAR(250),
    seat_capacity INT,
    are_facilities_available boolean NOT NULL,
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE old_movies(
old_movie_id INT PRIMARY KEY AUTO_INCREMENT,
old_movie_name VARCHAR(80),
old_movie_release_year YEAR,
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE event_posters(
event_poster_id INT PRIMARY KEY AUTO_INCREMENT,
event_id INT,
event_poster_name VARCHAR(250),
         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE booked_seats (
    booked_seat_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    show_id INT NOT NULL,
    seat_id INT NOT NULL,
    CONSTRAINT uk_show_seat UNIQUE (show_id, seat_id)
);


