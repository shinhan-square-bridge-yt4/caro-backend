CREATE TABLE IF NOT EXISTS driving_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    member_car_id BIGINT NOT NULL,
    drive_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    distance_km DECIMAL(10, 1) NOT NULL,
    start_location VARCHAR(255) NOT NULL,
    end_location VARCHAR(255) NOT NULL,
    earned_points INT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (member_car_id) REFERENCES member_car(id)
);
