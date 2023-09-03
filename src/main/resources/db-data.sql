/*
============================================================
                        데이터
============================================================
*/

/* ==================== 자주 묻는 질문 카테고리 ==================== */
INSERT INTO cs_category
(ctgy)
VALUES('가입/탈퇴');
INSERT INTO cs_category
(ctgy)
VALUES('결제');

/* ==================== 자주 묻는 질문 ==================== */
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle1', 'testContent1', '2023-08-29 06:50:26', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle2', 'testContent2', '2023-08-29 06:50:27', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle3', 'testContent3', '2023-08-29 06:50:27', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle4', 'testContent4', '2023-08-29 06:50:28', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle5', 'testContent5', '2023-08-29 06:50:28', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle6', 'testContent6', '2023-08-29 06:50:29', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle7', 'testContent7', '2023-08-29 06:50:29', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle8', 'testContent8', '2023-08-29 06:50:30', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle9', 'testContent9', '2023-08-29 06:50:30', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle10', 'testContent10', '2023-08-29 06:50:31', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle11', 'testContent1', '2023-09-01 08:49:23', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle12', 'testContent2', '2023-09-01 08:49:24', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle13', 'testContent3', '2023-09-01 08:49:24', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle14', 'testContent4', '2023-09-01 08:49:25', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle15', 'testContent5', '2023-09-01 08:49:25', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle16', 'testContent6', '2023-09-01 08:49:26', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle17', 'testContent7', '2023-09-01 08:49:26', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle18', 'testContent8', '2023-09-01 08:49:26', 'admin', 2);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle19', 'testContent9', '2023-09-01 08:49:27', 'admin', 1);
INSERT INTO customer_service
(title, content, faq_datetime, user_id, category_id)
VALUES('testTitle20', 'testContent10', '2023-09-01 08:49:27', 'admin', 1);

/* ==================== 팔로우 ==================== */
INSERT INTO follows
(from_user_no, to_trainer_no)
VALUES(2, 1);
INSERT INTO follows
(from_user_no, to_trainer_no)
VALUES(1, 1);
INSERT INTO follows
(from_user_no, to_trainer_no)
VALUES(2, 2);
INSERT INTO follows
(from_user_no, to_trainer_no)
VALUES(2, 4);

/* ==================== 회원 ==================== */
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('aaaa', '1234', '추성훈', '010-1234-1234', 'test@test.com', '경기도 의정부시 전좌로', '2023-08-21 07:38:08');
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('bbbb', '2345', '김연아', '010-2345-2345', 'test@test.com', '서울시 강서구 화곡로', '2023-08-21 07:38:08');
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('cccc', '3456', '손흥민', '010-3456-3456', 'test@test.com', '서울시 송파구 오금로', '2023-08-21 07:38:08');
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('adimin', '1234', '관리자', NULL, 'admin@gmail.com', NULL, '2023-08-29 05:02:41');
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('wooyoung121', '1q2w3e4r', '김우영', '01091325129', 'kwy@kakao.com', '충남 천안시 동남구 광풍로 1800 105동 ****호  (청당동, 청당 코오롱 하늘채)', '2023-09-01 11:16:11');
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('acou99', 'test123', '박진석', '01097463344', 'acou99@gmail.com', '15325.경기 안산시 상록구 웃말2길 9. (일동).202호', '2023-09-01 15:48:47');
INSERT INTO users
(user_id, user_pw, user_name, user_tel, user_email, user_address, reg_date)
VALUES('seohee905', 'tjgml1234', '이서희', '01012341234', '', '07574.서울 강서구 양천로 442. (등촌동).SBS', '2023-09-01 20:22:57');

/* ==================== 강의 ==================== */
INSERT INTO lessons
(title, title_code, trainer_no, price, category, lesson_info, start_date, end_date, lesson_img)
VALUES('헬스헬스', 'A00001', 1, 10000, 1, '헬스입니다', '2023-08-23 00:00:00', '2024-08-23 00:00:00', 'https://images.unsplash.com/photo-1597076545399-91a3ff0e71b3?auto=format&fit=crop&w=1470&q=80');
INSERT INTO lessons
(title, title_code, trainer_no, price, category, lesson_info, start_date, end_date, lesson_img)
VALUES('득근득근', 'A00002', 2, 30000, 2, '득근합시다', '2023-08-25 01:12:19', '2023-08-26 01:12:19', 'https://images.unsplash.com/photo-1535914254981-b5012eebbd15?auto=format&fit=crop&w=1470&q=80');
INSERT INTO lessons
(title, title_code, trainer_no, price, category, lesson_info, start_date, end_date, lesson_img)
VALUES('헬스장은 머니까,', 'A00003', 4, 35000, 1, '커넥트짐 하자', '2023-08-29 00:00:00', '2023-09-01 00:00:00', 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/8ac35453-93a8-4d46-a040-1e9b4f338a69-%E1%84%83%E1%85%A1%E1%84%8B%E1%85%AE%E1%86%AB%E1%84%85%E1%85%A9%E1%84%83%E1%85%B3.png');
INSERT INTO lessons
(title, title_code, trainer_no, price, category, lesson_info, start_date, end_date, lesson_img)
VALUES('7일 다이어트', 'A00004', 3, 10000, 3, '필라테스 필라테스 필필라테스 필라테스 필라테스 필라테스필라테스필라테스 필라테스 필라테스필라테스필라테스필라테스라테스필라테스 필라테스 필라테스 필라테스필라테스필라테스 필라테스 필라테스필라테스필라테스필라테스필라테스 필라테스 필라테스 필라테스필라테스필라테스 필라테스 필라테스필라테스필라테스필라테스 필라테스필라테스필라테스 필라테스 필라테스필라테스필라테스필라테스', '2023-08-30 00:00:00', '2023-08-31 00:00:00', 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/529d2e46-ed89-4816-a16f-8c494bbf8bd8-%ED%94%BC%EC%B9%B4%EC%B8%84.jpg');

/* ==================== 주문 ==================== */
INSERT INTO orders
(`no`, day_of_payment, order_pay, `type`, member_no)
VALUES('20230901359180', '2023-09-01 12:39:48', 40000, 'card', 1);

/* ==================== 주문 상세 ==================== */
INSERT INTO order_detail
(lesson_no, order_no, enroll_key)
VALUES(1, '20230901359180', 927118791344);

/* ==================== 트레이너 회원 ==================== */
INSERT INTO trainers
(trainer_id, trainer_pw, trainer_name, trainer_tel, profile_img, info_content, reg_date, trainer_email, info_title)
VALUES('qwer', '1234', '심으뜸', '010-1234-1234', 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/profile/qwer/%E1%84%89%E1%85%B5%E1%86%B7%E1%84%8B%E1%85%B3%E1%84%84%E1%85%B3%E1%86%B7.png', '성실하게 가르치겠습니다', '2023-08-21 01:34:15', 'test@test.com', '성실하게 가르치겠습니다');
INSERT INTO trainers
(trainer_id, trainer_pw, trainer_name, trainer_tel, profile_img, info_content, reg_date, trainer_email, info_title)
VALUES('asdf', '1234', '김계란', '010-1234-1234', 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/profile/asdf/%E1%84%80%E1%85%B5%E1%86%B7%E1%84%80%E1%85%A8%E1%84%85%E1%85%A1%E1%86%AB.png', '항상 성실하게 가르치겠습니다', '2023-08-21 01:35:51', 'test@test.com', '항상 성실하게 가르치겠습니다');
INSERT INTO trainers
(trainer_id, trainer_pw, trainer_name, trainer_tel, profile_img, info_content, reg_date, trainer_email, info_title)
VALUES('aaaa', '1234', '추성훈', '010-1234-1234', 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/8347f7d2-bb3a-4f63-ad40-6deb68fffdc4-banner-health.jpg', '12345', NULL, 'test@test.com', '저만 믿고 따라오세요');
INSERT INTO trainers
(trainer_id, trainer_pw, trainer_name, trainer_tel, profile_img, info_content, reg_date, trainer_email, info_title)
VALUES('cccc', '3456', '손흥민', '010-3456-3456', 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/profile/cccc/20230902-000118-img_400_450.png', '※ 트레이너 경력 총 15년
※ 믿고 맡겨 주세요
※ 이력사항
 - OO짐 PT 트레이너
 - OO피트니스 스피닝 강사
※ 자격 증명
 - 생활스포츠 지도사 자격증 보유
 - 전문스포츠 지도사 자격증 보유', NULL, 'test@test.com', '경력多 자격증多 믿고 맡겨주세요');

/* ==================== 자격증 ==================== */
INSERT INTO licenses
(trainer_no, license_img)
VALUES(1, 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/profile/cccc/20230902-001222-license_ex.jpeg');
INSERT INTO licenses
(trainer_no, license_img)
VALUES(2, 'https://connectgym-bucket.s3.ap-northeast-2.amazonaws.com/profile/cccc/20230902-001223-license_ex2.jpeg');
