-- =============================================
-- 데모 유저 운행 기록 추가 (2026-02-11)
-- =============================================

SET @demo_member_id = (SELECT id FROM member WHERE email = 'demo@caro.today');
SET @demo_car_id = (SELECT id FROM member_car WHERE member_id = @demo_member_id AND is_primary = TRUE LIMIT 1);

-- 1.4km = 14pt
INSERT INTO driving_record (member_id, member_car_id, start_date_time, end_date_time, distance_km, start_location, end_location, earned_points, pending_points, points_claimed, route_coordinates, created_at)
VALUES (
  @demo_member_id, @demo_car_id,
  '2026-02-11 20:24:06', '2026-02-11 20:41:58',
  1.400,
  '서울 중구 퇴계로 129 명동교자 신관명동역점',
  '서울 중구 세종대로 11 세븐일레븐',
  0, 14, 0,
  '[{"lat":37.56082896056274,"lng":126.9843187034178,"timestamp":1770716100},{"lat":37.56067102668167,"lng":126.98247388200501,"timestamp":1770716104},{"lat":37.5609232012764,"lng":126.98178341493293,"timestamp":1770716110},{"lat":37.56177006386841,"lng":126.98130784047596,"timestamp":1770716115},{"lat":37.561337280947505,"lng":126.97947439589201,"timestamp":1770716119},{"lat":37.56107121457114,"lng":126.97796349101111,"timestamp":1770716125},{"lat":37.56054826624989,"lng":126.97606220302728,"timestamp":1770716135},{"lat":37.560313791376956,"lng":126.9750210154495,"timestamp":1770716145},{"lat":37.5595027877818,"lng":126.97452329643791,"timestamp":1770716155},{"lat":37.55887647702983,"lng":126.97398308214532,"timestamp":1770716165},{"lat":37.55819607648683,"lng":126.97332123026406,"timestamp":1770716175}]',
  '2026-02-11 20:41:58.000000'
);
