-- =========================
-- GAS_STATION / SK_ENERGY (reward_brand_id = 1)
-- =========================
UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/SK_ENERGY/FUEL_5000.svg'
WHERE reward_brand_id = 1 AND item_name = '주유 5,000원 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/SK_ENERGY/FUEL_10000.svg'
WHERE reward_brand_id = 1 AND item_name = '주유 1만원 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/SK_ENERGY/ENGINE_OIL.png'
WHERE reward_brand_id = 1 AND item_name = '차량 엔진 오일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/SK_ENERGY/CAR_WASH.jpg'
WHERE reward_brand_id = 1 AND item_name = '자동 세차 1회 이용권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/SK_ENERGY/FUEL_PACKAGE.jpg'
WHERE reward_brand_id = 1 AND item_name = '주유 교환권 패키지';

-- =========================
-- GAS_STATION / GS_CALTEX (reward_brand_id = 2)
-- =========================
UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/GS_CALTEX/FUEL_5000.jpg'
WHERE reward_brand_id = 2 AND item_name = '주유 5,000원 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/GS_CALTEX/FUEL_10000.jpg'
WHERE reward_brand_id = 2 AND item_name = '주유 1만원 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/GS_CALTEX/CAR_WASH.jpg'
WHERE reward_brand_id = 2 AND item_name = '자동 세차 1회 이용권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/GS_CALTEX/ENGINE_OIL.jpg'
WHERE reward_brand_id = 2 AND item_name = '차량 엔진 오일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/GAS_STATION/GS_CALTEX/PREMIUM_FUEL_30000.png'
WHERE reward_brand_id = 2 AND item_name = '프리미엄 주유 3만원 교환권';

-- =========================
-- CAFE / STARBUCKS (reward_brand_id = 3)
-- =========================
UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/STARBUCKS/AMERICANO_TALL.jpg'
WHERE reward_brand_id = 3 AND item_name = '아이스 아메리카노 Tall 사이즈 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/STARBUCKS/CAFFE_LATTE_TALL.jpg'
WHERE reward_brand_id = 3 AND item_name = '카페라떼 Tall 사이즈 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/STARBUCKS/CAKE.jpg'
WHERE reward_brand_id = 3 AND item_name = '케이크 1종 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/STARBUCKS/MD_5000.jpg'
WHERE reward_brand_id = 3 AND item_name = 'MD 상품 5,000원 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/STARBUCKS/GIFT_CARD_10000.jpg'
WHERE reward_brand_id = 3 AND item_name = '모바일 기프트 카드 1만원권';

-- =========================
-- CAFE / A_TWOSOME_PLACE (reward_brand_id = 4)
-- =========================
UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/A_TWOSOME_PLACE/AMERICANO_R.jpg'
WHERE reward_brand_id = 4 AND item_name = '아메리카노 R 사이즈 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/A_TWOSOME_PLACE/CAFFE_LATTE_R.jpg'
WHERE reward_brand_id = 4 AND item_name = '카페라떼 R 사이즈 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/A_TWOSOME_PLACE/CAKE_SLICE.jpg'
WHERE reward_brand_id = 4 AND item_name = '조각 케이크 1종 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/A_TWOSOME_PLACE/CAKE_WHOLE.jpg'
WHERE reward_brand_id = 4 AND item_name = '홀케이크 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/CAFE/A_TWOSOME_PLACE/GIFT_CARD_10000.jpg'
WHERE reward_brand_id = 4 AND item_name = '모바일 기프트 카드 1만원권';

-- =========================
-- FAST_FOOD / MCDONALDS (reward_brand_id = 5)
-- =========================
UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/MCDONALDS/BIGMAC_SET.png'
WHERE reward_brand_id = 5 AND item_name = '빅맥 세트 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/MCDONALDS/CHEESEBURGER_SET.png'
WHERE reward_brand_id = 5 AND item_name = '치즈버거 세트 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/MCDONALDS/NUGGET_10.jpg'
WHERE reward_brand_id = 5 AND item_name = '맥너겟 10조각 모바일 교환권';

UPDATE reward_coupon
SET
    item_name = '해피밀 모바일 1만원권',
    image_url = '/images/rewards/FAST_FOOD/MCDONALDS/HAPPY_MEAL.jpg'
WHERE reward_brand_id = 5 AND item_name = '해피밀 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/MCDONALDS/GIFT_CARD_10000.jpg'
WHERE reward_brand_id = 5 AND item_name = '모바일 기프트 카드 1만원권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/MCDONALDS/CHEESE_STICK.jpg',
    required_points = 2000
WHERE reward_brand_id = 5 AND item_name = '치즈스틱 교환권';

-- =========================
-- FAST_FOOD / BURGER_KING (reward_brand_id = 6)
-- =========================
UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/BURGER_KING/WHOPPER_SET.jpg'
WHERE reward_brand_id = 6 AND item_name = '와퍼 세트 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/BURGER_KING/CHEESE_WHOPPER_SET.jpg'
WHERE reward_brand_id = 6 AND item_name = '치즈와퍼 세트 모바일 교환권';

UPDATE reward_coupon
SET
    item_name = '너겟킹 6조각 모바일 교환권',
    image_url = '/images/rewards/FAST_FOOD/BURGER_KING/NUGGET_KING.jpg'
WHERE reward_brand_id = 6
  AND item_name = '너겟킹 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/BURGER_KING/COKE_FRIES_SET.jpg'
WHERE reward_brand_id = 6 AND item_name = '콜라 + 감자튀김 세트 모바일 교환권';

UPDATE reward_coupon
SET image_url = '/images/rewards/FAST_FOOD/BURGER_KING/GIFT_CARD_10000.jpg'
WHERE reward_brand_id = 6 AND item_name = '모바일 기프트 카드 1만원권';
