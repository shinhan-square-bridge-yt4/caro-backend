CREATE TABLE member_attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    streak INT NOT NULL,
    points INT NOT NULL,
    is_bonus_day BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME(6) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    UNIQUE(member_id, attendance_date) -- 하루 한 번만 출석 가능
);
