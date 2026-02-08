UPDATE driving_record
SET
    start_date_time = TIMESTAMP(drive_date, start_time),
    end_date_time   = TIMESTAMP(drive_date, end_time)
WHERE start_date_time IS NULL
  AND end_date_time IS NULL;
