-- =============================================
-- 데모 유저 데이터 연도 보정 (2025 → 2026)
-- =============================================

SET @demo_member_id = (SELECT id FROM member WHERE email = 'demo@caro.today');

-- 지출 내역
UPDATE expense
SET expense_date = DATE_ADD(expense_date, INTERVAL 1 YEAR),
    created_at = DATE_ADD(created_at, INTERVAL 1 YEAR)
WHERE member_id = @demo_member_id
  AND YEAR(expense_date) = 2025;

-- 운행 기록
UPDATE driving_record
SET start_date_time = DATE_ADD(start_date_time, INTERVAL 1 YEAR),
    end_date_time = DATE_ADD(end_date_time, INTERVAL 1 YEAR),
    created_at = DATE_ADD(created_at, INTERVAL 1 YEAR)
WHERE member_id = @demo_member_id
  AND YEAR(start_date_time) = 2025;

-- 출석 기록
UPDATE member_attendance
SET attendance_date = DATE_ADD(attendance_date, INTERVAL 1 YEAR),
    created_at = DATE_ADD(created_at, INTERVAL 1 YEAR)
WHERE member_id = @demo_member_id
  AND YEAR(attendance_date) = 2025;
