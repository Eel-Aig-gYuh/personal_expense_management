-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: personalexpensemanagementdb
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `budget`
--

DROP TABLE IF EXISTS `budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `budget` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `category_id` int NOT NULL,
  `amount` double NOT NULL,
  `target` double NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `budget_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `budget_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
INSERT INTO `budget` VALUES (1,2,1,0,800000,'2025-04-02','2025-04-05','2025-04-02'),(2,2,1,0,2000000,'2025-04-02','2025-04-11','2025-04-02'),(3,2,2,0,1000000,'2025-04-04','2025-04-23','2025-04-02'),(4,2,3,0,4000000,'2025-05-01','2025-05-27','2025-04-03'),(5,2,3,0,800000,'2025-05-01','2025-05-16','2025-04-03'),(6,3,4,0,100001,'2025-04-03','2025-04-03','2025-04-03'),(7,3,4,0,200000,'2025-04-03','2025-04-03','2025-04-03');
/*!40000 ALTER TABLE `budget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `type` enum('Thu','Chi') COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,2,'Chi','Ăn uống'),(2,2,'Chi','Hoá đơn & Tiện ích'),(3,2,'Chi','Mua sắm'),(4,3,'Chi','Ăn uống');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `category_id` int NOT NULL,
  `wallet_id` int NOT NULL,
  `amount` double NOT NULL,
  `transaction_date` date NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `type` enum('Thu','Chi') COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  KEY `wallet_id` (`wallet_id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('User','Admin') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'User',
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin123','$2a$10$iJ8UkenQeft5c52cO.ugFuN7Ij.3TsWhv/X4WBq7rr10wJzZA54Im','Huy','Lê Gia',NULL,'giahuyle1030@gmail.com','Admin','2025-04-02'),(2,'testuser1','$2a$10$4a1hvzttzPuSOIUGpqShv.zwdG0Q.rrACMAlmDQ7iUmZqI1KWA1GC','Quốc','Trần Hoàng',NULL,'quoc.th@gmail.com','User','2025-04-02'),(3,'testuser2','$2a$10$i6Az5JPUL3uEYXxeMGkOP.smKvIKulokl7.SwnoSVJMXS6aAtHWvW','Hùng','Trần',NULL,'hung.tran@gmail.com','User','2025-04-03');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet`
--

DROP TABLE IF EXISTS `wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet` (
  `id` int NOT NULL,
  `so_du` double NOT NULL DEFAULT '0',
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `wallet_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet`
--

LOCK TABLES `wallet` WRITE;
/*!40000 ALTER TABLE `wallet` DISABLE KEYS */;
INSERT INTO `wallet` VALUES (1,0,'2025-04-02'),(2,0,'2025-04-02'),(3,0,'2025-04-03');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'personalexpensemanagementdb'
--

--
-- Dumping routines for database 'personalexpensemanagementdb'
--
/*!50003 DROP PROCEDURE IF EXISTS `CreateBudget` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateBudget`(
	in p_user_id int,
    in p_category_id int,
    in p_target double,
    in p_start_date date,
    in p_end_date date,
    in p_created_at date,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
		SET p_success = false;
        SET p_message = 'Lỗi: Không thể tạo ngân sách. Vui lòng kiểm tra lại thông tin.';
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Kiểm tra ngày hợp lệ
    IF p_start_date > p_end_date OR p_end_date > CURDATE() THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Khoảng thời gian không hợp lệ!';
        ROLLBACK;
    END IF;

    -- Tính tổng chi tiêu trong khoảng thời gian (nếu có giao dịch)
    SET @total_expense = (
        SELECT COALESCE(SUM(amount), 0)
        FROM Transaction
        WHERE user_id = p_user_id
        AND type = 'Chi'
        AND transaction_date BETWEEN p_start_date AND p_end_date
    );

    -- Kiểm tra target không nhỏ hơn tổng chi tiêu
    IF p_target < @total_expense THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách không được nhỏ hơn tổng chi tiêu hiện tại (' || @total_expense || ')!';
        ROLLBACK;
    END IF;

    -- Thêm ngân sách
    INSERT INTO Budget (user_id, category_id, amount, target, start_date, end_date, created_at)
    VALUES (p_user_id, p_category_id, 0.00, p_target, p_start_date, p_end_date, p_created_at);

	SET p_success = true;
    SET p_message = 'Tạo ngân sách thành công!';
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CreateCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateCategory`(
	in p_user_id int,
    in p_type enum('Thu', 'Chi'),
    in p_name varchar(100),
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể thêm danh mục. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;
    
    IF EXISTS (SELECT 1 FROM category WHERE category.name = p_name) THEN
		SET p_success = FALSE;
		SET p_message = 'Lỗi: Danh mục đã tồn tại!';
		ROLLBACK;
	END IF;
    
    START TRANSACTION;
    
    INSERT INTO category (user_id, type, name)
    VALUES (p_user_id, p_type, p_name);
    
    SET p_success = TRUE;
	SET p_message = 'Tạo mới danh mục thành công';
        
	COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CreateTransaction` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateTransaction`(
    in p_user_id int,
    in p_category_id int,
    in p_wallet_id int,
    in p_amount double,
    in p_transaction_date date,
    in p_description text,
    in p_type enum('Thu', 'Chi'),
    in p_created_at date,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể thêm giao dịch. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Kiểm tra các tham số đầu vào không null
    IF p_user_id IS NULL OR p_category_id IS NULL OR p_wallet_id IS NULL OR p_amount IS NULL 
       OR p_transaction_date IS NULL OR p_type IS NULL OR p_created_at IS NULL THEN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Thông tin không đầy đủ!';
        ROLLBACK;
    ELSE
        -- Kiểm tra người dùng có tồn tại không
        IF NOT EXISTS (SELECT 1 FROM Users WHERE id = p_user_id) THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Người dùng không tồn tại!';
            ROLLBACK;
        -- Kiểm tra danh mục có tồn tại và thuộc người dùng không
        ELSEIF NOT EXISTS (SELECT 1 FROM Category WHERE id = p_category_id AND user_id = p_user_id) THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Danh mục không tồn tại hoặc không thuộc người dùng!';
            ROLLBACK;
        -- Kiểm tra loại giao dịch có khớp với loại danh mục không
        ELSEIF (SELECT type FROM Category WHERE id = p_category_id) != p_type THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Loại giao dịch không khớp với loại danh mục!';
            ROLLBACK;
        -- Kiểm tra ví có tồn tại và thuộc người dùng không
        ELSEIF NOT EXISTS (SELECT 1 FROM Wallet WHERE id = p_wallet_id AND id = p_user_id) THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Ví không tồn tại hoặc không thuộc người dùng!';
            ROLLBACK;
        -- Kiểm tra số tiền không âm
        ELSEIF p_amount < 0 THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Số tiền không được âm!';
            ROLLBACK;
        -- Kiểm tra ngày giao dịch không vượt quá ngày hiện tại
        ELSEIF p_transaction_date > CURDATE() THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Ngày giao dịch không được vượt quá ngày hiện tại!';
            ROLLBACK;
        -- Kiểm tra số dư ví (nếu là giao dịch Chi)
        ELSEIF p_type = 'Chi' THEN
            SET @current_balance = (SELECT so_du FROM Wallet WHERE id = p_wallet_id);
            IF @current_balance IS NULL OR @current_balance < p_amount THEN
                SET p_success = FALSE;
                SET p_message = 'Lỗi: Số dư ví không đủ để thực hiện giao dịch!';
                ROLLBACK;
            END IF;
        END IF;

        -- Nếu tất cả kiểm tra đều qua, thêm giao dịch
        IF p_success IS NULL THEN
            -- Thêm giao dịch
            INSERT INTO Transaction (user_id, category_id, wallet_id, amount, transaction_date, description, type, created_at)
            VALUES (p_user_id, p_category_id, p_wallet_id, p_amount, p_transaction_date, p_description, p_type, p_created_at);

            -- Cập nhật số dư ví
            IF p_type = 'Thu' THEN
                UPDATE Wallet SET so_du = so_du + p_amount WHERE id = p_wallet_id;
            ELSE
                UPDATE Wallet SET so_du = so_du - p_amount WHERE id = p_wallet_id;
            END IF;

            -- Cập nhật ngân sách (chỉ tính giao dịch Chi)
            UPDATE Budget b
            SET b.amount = (
                SELECT COALESCE(SUM(t.amount), 0)
                FROM Transaction t
                WHERE t.user_id = p_user_id
                AND t.category_id = p_category_id
                AND t.transaction_date BETWEEN b.start_date AND b.end_date
                AND t.type = 'Chi'
            )
            WHERE b.user_id = p_user_id
            AND b.category_id = p_category_id
            AND p_transaction_date BETWEEN b.start_date AND b.end_date;

            SET p_success = TRUE;
            SET p_message = 'Thêm giao dịch thành công!';
            COMMIT;
        END IF;
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GetWalletByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetWalletByUserId`(
	in p_user_id int,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_message = 'Lỗi: Không thể lấy thông tin ví.';
    END;

    SELECT id, so_du, created_at
    FROM Wallet
    WHERE id = p_user_id;

    SET p_message = 'Lấy thông tin ví thành công!';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `LoginUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `LoginUser`(
	in p_username varchar(255)
)
BEGIN
	SELECT * FROM users WHERE username = p_username;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RegisterUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `RegisterUser`(
	in username varchar(255),
    in password varchar(255),
    in first_name varchar(255), 
    in last_name varchar(255),
    in avatar varchar(255),
    in email varchar(255),
    in role varchar(5),
    in created_at date,
    out success boolean,
    out message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
		-- Kiểm tra username đã tồn tại chưa
		IF EXISTS (SELECT 1 FROM users WHERE users.username = username) THEN
			SET success = FALSE;
			SET message = 'Bởi: Tên tài khoản đã tồn tại!';
			ROLLBACK;

		-- Kiểm tra xem email đã tồn tại chưa
		ELSEIF EXISTS (SELECT 1 FROM users WHERE users.email = email) THEN
			SET success = FALSE;
			SET message = 'Bởi: Email đã được sử dụng!';
			ROLLBACK;
		
        ELSE
			SET success = FALSE;
			SET message = 'Lỗi: Không thể đăng ký user. Vui lòng kiểm tra lại thông tin.';
			ROLLBACK;
		END IF;
        
    END;
    
    START TRANSACTION;

    INSERT INTO users (username, password, first_name, last_name, avatar, email, role, created_at)
    VALUES (username, password, first_name, last_name, avatar, email, role, created_at);

	-- Lấy ID của user vừa thêm
	SET @user_id = LAST_INSERT_ID();

	-- Tạo ví cho user
	INSERT INTO Wallet (id, so_du, created_at)
	VALUES (@user_id, 0.0, created_at);

    SET success = TRUE;
    SET message = 'Đăng ký thành công!';
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateBudget` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateBudget`(
    in p_budget_id int,
    in p_user_id int,
    in p_category_id int,
    in p_target double,
    in p_start_date date,
    in p_end_date date,
    in p_created_at date,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
		SET p_success = false;
        SET p_message = 'Lỗi: Không thể cập nhật ngân sách. Vui lòng kiểm tra lại thông tin.';
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Kiểm tra ngân sách có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM Budget WHERE id = p_budget_id AND user_id = p_user_id) THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách không tồn tại hoặc không thuộc người dùng!';
        ROLLBACK;
    END IF;

    -- Kiểm tra ngày hợp lệ
    IF p_start_date > p_end_date OR p_end_date > CURDATE() THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Khoảng thời gian không hợp lệ!';
        ROLLBACK;
    END IF;

    -- Lấy tổng chi tiêu hiện tại của ngân sách
    SET @total_expense = (
        SELECT COALESCE(SUM(amount), 0)
        FROM Transaction
        WHERE budget_id = p_budget_id
        AND type = 'Chi'
        AND transaction_date BETWEEN p_start_date AND p_end_date
    );

    -- Kiểm tra target không nhỏ hơn tổng chi tiêu
    IF p_target < @total_expense THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách không được nhỏ hơn tổng chi tiêu hiện tại (' || @total_expense || ')!';
        ROLLBACK;
    END IF;

    -- Cập nhật ngân sách
    UPDATE Budget
    SET	category_id = p_category_id,
        target = p_target,
        start_date = p_start_date,
        end_date = p_end_date,
        amount = @total_expense
    WHERE id = p_budget_id;

	SET p_success = true;
    SET p_message = 'Cập nhật ngân sách thành công!';
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-06 17:08:39
