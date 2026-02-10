ALTER TABLE member_car ADD COLUMN is_primary BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE member_car mc
JOIN (
    SELECT MIN(id) AS id
    FROM member_car
    GROUP BY member_id
) oldest ON mc.id = oldest.id
SET mc.is_primary = TRUE;
