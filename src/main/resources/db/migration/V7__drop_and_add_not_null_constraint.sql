ALTER TABLE driving_record
    MODIFY COLUMN start_date_time DATETIME(6) NOT NULL,
    MODIFY COLUMN end_date_time DATETIME(6) NOT NULL,
    MODIFY COLUMN distance_km DECIMAL(10, 3) NOT NULL;

ALTER TABLE driving_record
    DROP COLUMN drive_date,
    DROP COLUMN start_time,
    DROP COLUMN end_time;
