INSERT INTO reward_brand (name, category, created_at) VALUES
-- GAS_STATION
('SK에너지', 'GAS_STATION', NOW()),
('GS칼텍스', 'GAS_STATION', NOW()),

-- CAFE
('스타벅스', 'CAFE', NOW()),
('투썸플레이스', 'CAFE', NOW()),

-- FAST_FOOD
('맥도날드', 'FAST_FOOD', NOW()),
('버거킹', 'FAST_FOOD', NOW());

-- SK에너지 (reward_brand_id = 1)
INSERT INTO reward_coupon (reward_brand_id, item_name, required_points, image_url, redeem_count, created_at) VALUES
(1, '주유 5,000원 교환권', 6500, '/images/rewards/dummy.jpg', 10, NOW()),
(1, '주유 1만원 교환권', 13000, '/images/rewards/dummy.jpg', 9, NOW()),
(1, '차량 엔진 오일 교환권', 18000, '/images/rewards/dummy.jpg', 15, NOW()),
(1, '자동 세차 1회 이용권', 9000, '/images/rewards/dummy.jpg', 1, NOW()),
(1, '주유 교환권 패키지', 19500, '/images/rewards/dummy.jpg', 0, NOW());

-- GS칼텍스 (reward_brand_id = 2)
INSERT INTO reward_coupon (reward_brand_id, item_name, required_points, image_url, redeem_count, created_at) VALUES
(2, '주유 5,000원 교환권', 6500, '/images/rewards/dummy.jpg', 23, NOW()),
(2, '주유 1만원 교환권', 13000, '/images/rewards/dummy.jpg', 15, NOW()),
(2, '자동 세차 1회 이용권', 9500, '/images/rewards/dummy.jpg', 2, NOW()),
(2, '차량 엔진 오일 교환권', 19000, '/images/rewards/dummy.jpg', 11, NOW()),
(2, '프리미엄 주유 3만원 교환권', 42000, '/images/rewards/dummy.jpg', 2, NOW());

-- 스타벅스 (reward_brand_id = 3)
INSERT INTO reward_coupon (reward_brand_id, item_name, required_points, image_url, redeem_count, created_at) VALUES
(3, '아이스 아메리카노 Tall 사이즈 모바일 교환권', 5500, '/images/rewards/dummy.jpg', 31, NOW()),
(3, '카페라떼 Tall 사이즈 모바일 교환권', 6000, '/images/rewards/dummy.jpg', 25, NOW()),
(3, '케이크 1종 모바일 교환권', 11000, '/images/rewards/dummy.jpg', 14, NOW()),
(3, 'MD 상품 5,000원 교환권', 7000, '/images/rewards/dummy.jpg', 12, NOW()),
(3, '모바일 기프트 카드 1만원권', 13000, '/images/rewards/dummy.jpg', 29, NOW());

-- 투썸플레이스 (reward_brand_id = 4)
INSERT INTO reward_coupon (reward_brand_id, item_name, required_points, image_url, redeem_count, created_at) VALUES
(4, '아메리카노 R 사이즈 모바일 교환권', 5000, '/images/rewards/dummy.jpg', 32, NOW()),
(4, '카페라떼 R 사이즈 모바일 교환권', 5800, '/images/rewards/dummy.jpg', 51, NOW()),
(4, '조각 케이크 1종 모바일 교환권', 11500, '/images/rewards/dummy.jpg', 17, NOW()),
(4, '홀케이크 모바일 교환권', 22000, '/images/rewards/dummy.jpg', 9, NOW()),
(4, '모바일 기프트 카드 1만원권', 13000, '/images/rewards/dummy.jpg', 15, NOW());

-- 맥도날드 (reward_brand_id = 5)
INSERT INTO reward_coupon (reward_brand_id, item_name, required_points, image_url, redeem_count, created_at) VALUES
(5, '빅맥 세트 모바일 교환권', 8500, '/images/rewards/dummy.jpg', 62, NOW()),
(5, '치즈버거 세트 모바일 교환권', 7500, '/images/rewards/dummy.jpg', 43, NOW()),
(5, '맥너겟 10조각 모바일 교환권', 6500, '/images/rewards/dummy.jpg', 19, NOW()),
(5, '해피밀 모바일 교환권', 6000, '/images/rewards/dummy.jpg', 38, NOW()),
(5, '모바일 기프트 카드 1만원권', 13000, '/images/rewards/dummy.jpg', 21, NOW());

-- 버거킹 (reward_brand_id = 6)
INSERT INTO reward_coupon (reward_brand_id, item_name, required_points, image_url, redeem_count, created_at) VALUES
(6, '와퍼 세트 모바일 교환권', 9000, '/images/rewards/dummy.jpg', 12, NOW()),
(6, '치즈와퍼 세트 모바일 교환권', 9800, '/images/rewards/dummy.jpg', 61, NOW()),
(6, '너겟킹 모바일 교환권', 6500, '/images/rewards/dummy.jpg', 3, NOW()),
(6, '콜라 + 감자튀김 세트 모바일 교환권', 6000, '/images/rewards/dummy.jpg', 11, NOW()),
(6, '모바일 기프트 카드 1만원권', 13000, '/images/rewards/dummy.jpg', 25, NOW());
