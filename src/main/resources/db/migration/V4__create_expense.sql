CREATE TABLE IF NOT EXISTS expense (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    member_car_id BIGINT NOT NULL,
    expense_date DATE NOT NULL,
    amount DECIMAL(10, 0) NOT NULL,
    category VARCHAR(50) NOT NULL,
    location VARCHAR(255) NOT NULL,
    memo VARCHAR(255),
    created_at DATETIME(6) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (member_car_id) REFERENCES member_car(id)
);
