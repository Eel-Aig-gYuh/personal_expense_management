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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
INSERT INTO `budget` VALUES (1,2,2,800000,1000000,'2025-04-27','2025-04-27','2025-04-27'),(2,2,3,80000001,100000000,'2025-04-27','2025-04-27','2025-04-27'),(3,1,1,810000,1000000,'2025-04-27','2025-04-27','2025-04-27'),(4,1,1,0,100000000,'2025-04-29','2025-05-01','2025-04-27'),(6,1,5,0,15000000,'2025-04-27','2025-04-27','2025-04-27');
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
  `type` enum('Thu','Chi') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,1,'Chi','Đi chơi'),(2,2,'Chi','Đi chơi'),(3,2,'Chi','Đi ăn'),(5,1,'Thu','Lương');
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
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  KEY `wallet_id` (`wallet_id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (2,2,2,2,800000,'2025-04-27','','2025-04-27'),(3,2,3,2,80000001,'2025-04-27','','2025-04-27'),(6,1,1,1,10000,'2025-04-27','','2025-04-27'),(29,1,5,1,10000000,'2025-04-27','','2025-04-27'),(30,1,1,1,800000,'2025-04-27','','2025-04-27'),(32,1,1,1,100000,'2025-04-10','','2025-04-27');
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
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('User','Admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'User',
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
INSERT INTO `users` VALUES (1,'testuser1','$2a$10$.DaLkZOBn9qgG38iiLTfcOC8LeOjBiUH48ThoMBfaBLvKag3lnnI6','Huy','Le Gia',NULL,'admin@gmail.com','User','2025-04-27'),(2,'testuser2','$2a$10$5f4MXj0CC4c10ngEvh3aWONu4HDTo3kUvIEFqwd5tamYFnPf0c8fS','Hiển','Trần Thế',NULL,'hien123@gmail.com','User','2025-04-27'),(3,'testuser3','$2a$10$TgWSvS2feU4ttkkXCVpc4uFD2AZhT1yEHxUs9FiKD8NMMpO0jRpR2','Lam','Le',NULL,'lam@gmail.com','User','2025-04-27');
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
INSERT INTO `wallet` VALUES (1,9090000,'2025-04-27'),(2,-80800001,'2025-04-27'),(3,0,'2025-04-27');
/*!40000 ALTER TABLE `wallet` ENABLE KEYS */;
UNLOCK TABLES;

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

	-- Kiểm tra ngân sách tối thiểu
	IF p_target < 100000 THEN 
		SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách đang nhỏ hơn giới hạn ngân sách cho phép.';
        ROLLBACK;
    END IF;
    
    -- Kiểm tra ngân sách tối đa
	IF p_target > 100000000 THEN 
		SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách đang vượt hơn giới hạn ngân sách cho phép.';
        ROLLBACK;
    END IF;

    -- Kiểm tra ngày hợp lệ
    IF p_start_date > p_end_date OR p_end_date < CURDATE() THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Khoảng thời gian không hợp lệ!';
        ROLLBACK;
	ELSE 
		-- Tính tổng chi tiêu trong khoảng thời gian (nếu có giao dịch)
		SET @total_expense = (
			SELECT COALESCE(SUM(amount), 0)
			FROM transaction t
			JOIN category c ON t.category_id = c.id
			WHERE t.user_id = p_user_id
			AND c.type = 'Chi'
			AND t.transaction_date BETWEEN p_start_date AND p_end_date
		);

		-- Kiểm tra target không nhỏ hơn tổng chi tiêu
		IF p_target < @total_expense THEN
			SET p_success = false;
			SET p_message = 'Lỗi: Ngân sách không được nhỏ hơn tổng chi tiêu hiện tại (' || @total_expense || ')!';
			ROLLBACK;
		END IF;
        
        -- Kiểm tra nếu có một ngân sách trong cùng khoảng thời gian.
        SET @same_budget = (
			SELECT id
            FROM budget
            WHERE category_id = p_category_id 
				AND start_date = p_start_date
                AND end_date = p_end_date
        );
        
        IF @same_budget IS NOT NULL THEN
			SET p_success = false;
			SET p_message = concat ('208:', @same_budget );
			ROLLBACK;
        END IF;

		IF p_success IS NULL THEN 
			-- Thêm ngân sách
			INSERT INTO Budget (user_id, category_id, amount, target, start_date, end_date, created_at)
			VALUES (p_user_id, p_category_id, 0.00, p_target, p_start_date, p_end_date, p_created_at);

			SET p_success = true;
			SET p_message = 'Tạo ngân sách thành công!';
			COMMIT;
        END IF;
    END IF;

    
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
        SET p_success = false;
        SET p_message = 'Lỗi: Không thể thêm danh mục. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;
    
    -- Kiểm tra đầu vào
    IF p_user_id IS NULL OR p_type IS NULL OR p_name IS NULL THEN
		SET p_success = false;
        SET p_message = 'Lỗi: Dữ liệu đầu vào rỗng.';
        ROLLBACK;
    END IF;
    
    IF EXISTS (SELECT 1 FROM category WHERE category.name = p_name AND category.user_id = p_user_id) THEN
		SET p_success = false;
		SET p_message = 'Lỗi: Danh mục đã tồn tại!';
		ROLLBACK;
	END IF;
    
    IF p_type NOT IN ('Thu', 'Chi') THEN 
		SET p_success = false;
		SET p_message = 'Lỗi: Danh mục chỉ có thể là "Thu" hoặc "Chi".';
		ROLLBACK;
    END IF;
    
    START TRANSACTION;
    
    IF p_success IS NULL THEN
		INSERT INTO category (user_id, type, name)
		VALUES (p_user_id, p_type, p_name);
		
		SET p_success = TRUE;
		SET p_message = 'Tạo mới danh mục thành công';
    END IF;
        
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
    in p_created_at date,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN main_block: BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể thêm giao dịch. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Kiểm tra các tham số đầu vào không null
    IF p_user_id IS NULL OR p_category_id IS NULL OR p_wallet_id IS NULL OR p_amount IS NULL 
       OR p_transaction_date IS NULL OR p_created_at IS NULL THEN
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
		-- Kiểm tra số tiền thuộc khoảng từ 10 000 -> 100 000 000
		ELSEIF p_amount < 10000 OR p_amount > 100000000 THEN
			SET p_success = FALSE;
            SET p_message = 'Lỗi: Số tiền vượt quá ngưỡng cho phép!';
            ROLLBACK;
        -- Kiểm tra ngày giao dịch không vượt quá ngày hiện tại
        ELSEIF p_transaction_date > CURDATE() THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Ngày giao dịch không được vượt quá ngày hiện tại!';
            ROLLBACK;
        -- Kiểm tra số dư ví (nếu là giao dịch Chi)
        -- ELSEIF p_type = 'Chi' THEN
        --    SET @current_balance = (SELECT so_du FROM Wallet WHERE id = p_wallet_id);
        --    IF @current_balance IS NULL OR @current_balance < p_amount THEN
        --        SET p_success = FALSE;
        --        SET p_message = 'Lỗi: Số dư ví không đủ để thực hiện giao dịch!';
        --        ROLLBACK;
        --    END IF;
        END IF;
        
        -- Kiểm tra giao dịch có vượt quá 80% ngân sách hay không ?
         IF p_success IS NULL THEN
             SET @p_type = (
                 SELECT type
                 FROM category
                 WHERE id = p_category_id
             );
             
             SET @p_name = (
                 SELECT name
                 FROM category
                 WHERE id = p_category_id
             );
 
             -- Chỉ kiểm tra ngân sách nếu giao dịch là Chi
             IF @p_type = 'Chi' THEN
                 -- Tìm ngân sách liên quan đến danh mục và ngày giao dịch
                 SET @budget_id = NULL;
                 SET @budget_target = 0.0;
                 SET @budget_amount = 0.0;
 
                 SELECT id, target, amount INTO @budget_id, @budget_target, @budget_amount
                 FROM budget
                 WHERE user_id = p_user_id
                 AND category_id = p_category_id
                 AND p_transaction_date BETWEEN start_date AND end_date
                 ORDER BY target ASC
                 LIMIT 1;
 
                 -- Nếu tồn tại ngân sách
                 IF @budget_id IS NOT NULL THEN
                     -- Tính tổng số tiền giao dịch trong khoảng thời gian ngân sách (bao gồm giao dịch mới)
					SET @total_amount = @budget_amount + p_amount;
					
                    IF @total_amount > @budget_target THEN 
						SET p_success = false;
                        SET p_message = 'Cảnh báo: Giao dịch vượt ngân sách! \nVui lòng chọn giao dịch khác!';
						ROLLBACK;
                        LEAVE main_block;
                    END IF;
					
					-- Kiểm tra xem tổng số tiền có vượt quá 80% ngân sách hay không
					IF @budget_target > 0 AND (@total_amount / @budget_target) > 0.8 THEN
						SET p_success = NULL;
						SET p_message = CONCAT('Cảnh báo: Giao dịch vượt quá 80% ngân sách ', @p_name);
					ELSE
						SET p_message = " ";
					END IF;
                 END IF;
             END IF;
         END IF;

        -- Nếu tất cả kiểm tra đều qua, thêm giao dịch
        IF p_success IS NULL THEN
            -- Thêm giao dịch
            INSERT INTO transaction (user_id, category_id, wallet_id, amount, transaction_date, description, created_at)
            VALUES (p_user_id, p_category_id, p_wallet_id, p_amount, p_transaction_date, p_description, p_created_at);

            -- Cập nhật số dư ví
            SET @p_type = (
				SELECT type
                FROM category
                WHERE id = p_category_id
            );
            
            IF @p_type = 'Thu' THEN
                UPDATE wallet SET so_du = so_du + p_amount WHERE id = p_wallet_id;
            ELSE
                UPDATE wallet SET so_du = so_du - p_amount WHERE id = p_wallet_id;
            END IF;

            -- Cập nhật ngân sách (chỉ tính giao dịch Chi)
            IF @p_type = 'Chi' THEN
                UPDATE budget b
                SET b.amount = (
                    SELECT COALESCE(SUM(t.amount), 0)
                    FROM Transaction t
                    JOIN Category c ON t.category_id = c.id
                    WHERE t.user_id = p_user_id
                    AND t.category_id = p_category_id
                    AND t.transaction_date BETWEEN b.start_date AND b.end_date
                    AND c.type = 'Chi'
                )
                WHERE b.user_id = p_user_id
                AND b.category_id = p_category_id
                AND p_transaction_date BETWEEN b.start_date AND b.end_date;
            END IF;

            SET p_success = TRUE;
            IF p_message IS NOT NULL THEN
				SET p_message = CONCAT(p_message, '\nThêm giao dịch thành công!');
			ELSE
				SET p_message = 'Thêm giao dịch thành công!';
			END IF;
            COMMIT;
        END IF;
    END IF;
END main_block;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DeleteCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteCategory`(
	in p_category_id int,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi hệ thống: Không thể xóa danh mục.';
    END;

    -- Kiểm tra category_id có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM Category WHERE id = p_category_id) THEN
        SET p_success = FALSE;
        SET p_message = 'Danh mục không tồn tại.';
        
    -- Kiểm tra xem danh mục có đang được sử dụng trong Budget không
    ELSEIF EXISTS (SELECT 1 FROM Budget WHERE category_id = p_category_id) THEN
        SET p_success = FALSE;
        SET p_message = 'Không thể xóa danh mục vì vẫn còn ngân sách liên quan.';
        
    -- Kiểm tra xem danh mục có đang được sử dụng trong Transaction không
    ELSEIF EXISTS (SELECT 1 FROM Transaction WHERE category_id = p_category_id) THEN
        SET p_success = FALSE;
        SET p_message = 'Không thể xóa danh mục vì vẫn còn giao dịch liên quan.';
    ELSE
    
        -- Xóa danh mục
        DELETE FROM Category WHERE id = p_category_id;

        SET p_success = TRUE;
        SET p_message = 'Xóa danh mục thành công.';
        
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DeleteCatgory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteCatgory`(
	in p_category_id int,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi hệ thống: Không thể xóa danh mục.';
    END;

    -- Kiểm tra category_id có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM Category WHERE id = p_category_id) THEN
        SET p_success = FALSE;
        SET p_message = 'Danh mục không tồn tại.';
        
    -- Kiểm tra xem danh mục có đang được sử dụng trong Budget không
    ELSEIF EXISTS (SELECT 1 FROM Budget WHERE category_id = p_category_id) THEN
        SET p_success = FALSE;
        SET p_message = 'Không thể xóa danh mục vì vẫn còn ngân sách liên quan.';
        
    -- Kiểm tra xem danh mục có đang được sử dụng trong Transaction không
    ELSEIF EXISTS (SELECT 1 FROM Transaction WHERE category_id = p_category_id) THEN
        SET p_success = FALSE;
        SET p_message = 'Không thể xóa danh mục vì vẫn còn giao dịch liên quan.';
    ELSE
    
        -- Xóa danh mục
        DELETE FROM Category WHERE id = p_category_id;

        SET p_success = TRUE;
        SET p_message = 'Xóa danh mục thành công.';
        
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DeleteTransaction` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `DeleteTransaction`(
	in p_transaction_id int,
    in p_user_id int,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN
	DECLARE old_amount DOUBLE;
    DECLARE old_category_id INT;
    DECLARE old_category_type ENUM('Thu', 'Chi');
    DECLARE old_wallet_id INT;
    DECLARE old_user_id INT;
    DECLARE old_transaction_date DATE;
    DECLARE new_so_du DOUBLE;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể xóa giao dịch. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Kiểm tra các tham số đầu vào không null
    IF p_transaction_id IS NULL OR p_user_id IS NULL THEN
        SET p_success = false;
        SET p_message = 'Lỗi: Thông tin không đầy đủ!';
        ROLLBACK;
    END IF;
	-- Kiểm tra xem giao dịch có tồn tại và thuộc về người dùng không
	IF NOT EXISTS (SELECT 1 FROM transaction WHERE id = p_transaction_id AND user_id = p_user_id) THEN
		SET p_success = false;
        SET p_message = 'Lỗi: Giao dịch không tồn tại!';
        ROLLBACK;
    END IF;
	
    -- Lấy thông tin giao dịch cũ
	SELECT user_id, category_id, wallet_id, amount, transaction_date
	INTO old_user_id, old_category_id, old_wallet_id, old_amount, old_transaction_date
	FROM transaction
	WHERE id = p_transaction_id;
    
    -- Lấy type của danh mục mà giao dịch hiện có
    SELECT type
    INTO old_category_type
    FROM category
    WHERE id = old_category_id;
    
    -- Cập nhật ngân sách 
    -- Nếu giao dịch thuộc loại "Chi", cập nhật Budget.amount
    IF old_category_type = 'Chi' THEN
        UPDATE Budget b
        SET b.amount = b.amount - old_amount
        WHERE b.user_id = old_user_id
        AND b.category_id = old_category_id
        AND old_transaction_date BETWEEN b.start_date AND b.end_date;
    END IF;
    
    -- Cập nhật số dư ví
    IF old_category_type = 'Chi' THEN
		UPDATE wallet
		SET so_du = so_du + old_amount
		WHERE id = old_wallet_id;
    ELSE
		UPDATE wallet
		SET so_du = so_du - old_amount
		WHERE id = old_wallet_id;
    END IF;
    
    IF p_success IS NULL THEN
		-- Xóa giao dịch
		DELETE FROM transaction
		WHERE id = p_transaction_id;
		
		-- Commit transaction nếu thành công
		SET p_success = TRUE;
		SET p_message = 'Xóa giao dịch thành công!';
		COMMIT;
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

	IF message IS NULL THEN 
		INSERT INTO users (username, password, first_name, last_name, avatar, email, role, created_at)
		VALUES (username, password, first_name, last_name, avatar, email, role, created_at);

		-- Lấy ID của user vừa thêm
		SET @user_id = LAST_INSERT_ID();

		-- Tạo ví cho user
		INSERT INTO Wallet (id, so_du, created_at)
		VALUES (@user_id, 0.0, created_at);

		SET success = TRUE;
		SET message = 'Đăng ký thành công!';
    END IF;
    
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
    in p_category_id int,
    in p_target double,
    in p_start_date date,
    in p_end_date date,
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
    
    -- Kiểm tra budget hợp lệ
    IF NOT EXISTS (SELECT 1 FROM budget WHERE budget.id = p_budget_id) THEN
		SET p_success = FALSE;
		SET p_message = 'Lỗi: Không tồn tại ngân sách này !';
		ROLLBACK;
	END IF;
    
    -- Kiểm tra ngân sách tối thiểu
	IF p_target < 100000 THEN 
		SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách đang nhỏ hơn giới hạn ngân sách cho phép.';
        ROLLBACK;
    END IF;
    
    -- Kiểm tra ngân sách tối đa
	IF p_target > 100000000 THEN 
		SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách đang vượt hơn giới hạn ngân sách cho phép.';
        ROLLBACK;
    END IF;
    
    -- Kiểm tra ngày hợp lệ
	IF p_start_date > p_end_date OR p_end_date < CURDATE() THEN
		SET p_success = false;
		SET p_message = 'Lỗi: Khoảng thời gian không hợp lệ!';
		ROLLBACK;
	END IF;
        
    -- Lấy tổng chi tiêu hiện tại của ngân sách
    SET @user_id = (
		SELECT user_id
        FROM budget
        WHERE p_budget_id = id
    );
    
    SET @total_expense = (
			SELECT COALESCE(SUM(amount), 0)
			FROM transaction t
			JOIN category c ON t.category_id = c.id
			WHERE t.user_id = @user_id
			AND c.type = 'Chi'
			AND t.transaction_date BETWEEN p_start_date AND p_end_date
		);
	
	-- Kiểm tra target không nhỏ hơn tổng chi tiêu
	IF p_target < @total_expense THEN
		SET p_success = false;
		SET p_message = 'Lỗi: Ngân sách không được nhỏ hơn tổng chi tiêu hiện tại (' || @total_expense || ')!';
		ROLLBACK;
	END IF;
    
    -- Cập nhật ngân sách
    IF p_success IS NULL THEN
		UPDATE budget
		SET	category_id = p_category_id,
			target = p_target,
			start_date = p_start_date,
			end_date = p_end_date,
			amount = @total_expense
		WHERE id = p_budget_id;

		SET p_success = true;
		SET p_message = 'Cập nhật ngân sách thành công!';
    END IF;
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateCategory`(
	in p_name varchar(100),
    in p_type ENUM('Thu', 'Chi'),
    in p_category_id int,
    in p_user_id int,
    out p_success boolean,
    out p_message varchar(225)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = false;
        SET p_message = 'Lỗi: Không thể cập nhật danh mục.';
        ROLLBACK;
    END;
    
    IF NOT EXISTS (SELECT 1 FROM category WHERE id = p_category_id AND user_id = p_user_id) THEN
		SET p_success = false;
        SET p_message = 'Lỗi: Không tìm thấy danh mục.';
        ROLLBACK;
	END IF;
    
	IF NOT EXISTS (SELECT 1 FROM Users WHERE id = p_user_id) THEN
        SET p_success = false;
        SET p_message = 'Người dùng không tồn tại.';
        ROLLBACK;
	END IF;
    
    -- Kiểm tra name không null và không vượt quá 100 ký tự
    IF p_name IS NULL OR p_name = '' THEN
        SET p_success = false;
        SET p_message = 'Tên danh mục không được để trống.';
        ROLLBACK;
	END IF;
    
    IF LENGTH(p_name) > 100 THEN
        SET p_success = false;
        SET p_message = 'Tên danh mục không được vượt quá 100 ký tự.';
        ROLLBACK;
	END IF;
    
    -- Kiểm tra type có hợp lệ không (do type là ENUM nên không cần kiểm tra, nhưng thêm để rõ ràng)
    IF p_type NOT IN ('Thu', 'Chi') THEN
        SET p_success = false;
        SET p_message = 'Loại danh mục phải là "Thu" hoặc "Chi".';
        ROLLBACK;
    END IF;
    
    START TRANSACTION;
    
    IF p_success IS NULL THEN
		-- Cập nhật danh mục
        UPDATE Category
		SET user_id = p_user_id,
			type = p_type,
			name = p_name
		WHERE id = p_category_id;

        SET p_success = true;
        SET p_message = 'Cập nhật danh mục thành công.';
    END IF;
    
    COMMIT;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UpdateTransaction` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateTransaction`(
	in p_transaction_id int,
    in p_user_id int,
    in p_category_id int,
    in p_wallet_id int,
    in p_amount double,
    in p_transaction_date date,
    in p_description text,
    out p_success boolean,
    out p_message varchar(255)
)
BEGIN main_block: BEGIN
	DECLARE old_amount DOUBLE;
    DECLARE old_category_id INT;
    DECLARE old_transaction_date DATE;
    DECLARE old_user_id INT;
    DECLARE old_wallet_id INT;
    DECLARE old_category_type ENUM('Thu', 'Chi');
    DECLARE new_so_du DOUBLE;
  
	DECLARE new_category_type ENUM('Thu', 'Chi');
  
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể cập nhật giao dịch. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;

    START TRANSACTION;
    
    -- Kiểm tra giao dịch có tồn tại
    IF NOT EXISTS (SELECT 1 FROM transaction WHERE transaction.id = p_transaction_id) THEN
		SET p_success = FALSE;
		SET p_message = 'Lỗi: Không tồn tại giao dịch này !';
		ROLLBACK;
	END IF;

    -- Kiểm tra các tham số đầu vào không null
    IF p_transaction_id IS NULL OR p_category_id IS NULL OR p_amount IS NULL 
       OR p_transaction_date IS NULL THEN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Thông tin không đầy đủ!';
        ROLLBACK;
    ELSE
        -- Kiểm tra số tiền không âm
        IF p_amount < 0 THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Số tiền không được âm!';
            ROLLBACK;
		-- Kiểm tra số tiền thuộc khoảng từ 10 000 -> 100 000 000
		ELSEIF p_amount < 10000 OR p_amount > 100000000 THEN
			SET p_success = FALSE;
            SET p_message = 'Lỗi: Số tiền vượt quá ngưỡng cho phép!';
            ROLLBACK;
        -- Kiểm tra ngày giao dịch không vượt quá ngày hiện tại
        ELSEIF p_transaction_date > CURDATE() THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Ngày giao dịch không được vượt quá ngày hiện tại!';
            ROLLBACK;
        END IF;
        
        -- Kiểm tra giao dịch có vượt quá 80% ngân sách hay không ?
      
        -- Nếu tất cả kiểm tra đều qua, cập nhật giao dịch
        IF p_success IS NULL THEN
			-- Lấy thông tin giao dịch cũ
            SELECT user_id, category_id, wallet_id, amount, transaction_date
			INTO old_user_id, old_category_id, old_wallet_id, old_amount, old_transaction_date
			FROM transaction
			WHERE id = p_transaction_id;
            
            -- Lấy type của giao dịch cũ
            SELECT type
            INTO old_category_type
            FROM category
            WHERE id = old_category_id;
            
            -- Lấy type của giao dịch mới
            SET @p_name = '';
            
            SELECT type, name
            INTO new_category_type, @p_name
            FROM category
            WHERE id = p_category_id;
            
            -- Nếu giao dịch cũ thuộc loại Chi thì
            IF new_category_type = 'Chi' THEN 
				-- Kiểm tra nếu có ngân sách vượt quá 80% ngân sách thì đánh fail.
                
                -- Tìm ngân sách liên quan đến danh mục và ngày giao dịch
				SET @budget_id = NULL;
				SET @budget_target = 0.0;
				SET @budget_amount = 0.0;
 
				SELECT id, target, amount INTO @budget_id, @budget_target, @budget_amount
				FROM budget
				WHERE user_id = p_user_id
					AND category_id = p_category_id
					AND p_transaction_date BETWEEN start_date AND end_date
                ORDER BY target ASC
				LIMIT 1;
 
				-- Nếu tồn tại ngân sách
				IF @budget_id IS NOT NULL THEN
					-- Tính tổng số tiền giao dịch trong khoảng thời gian ngân sách (bao gồm giao dịch mới)
					SET @total_amount = @budget_amount + p_amount;
 
					IF p_amount > @budget_target THEN 
						SET p_success = false;
                        SET p_message = CONCAT('Cảnh báo: Giao dịch vượt ngân sách! \nVui lòng chọn giao dịch khác!', @total_amount, ' ', @budget_target );
						ROLLBACK;
                        LEAVE main_block;
                    END IF;
 
					-- Kiểm tra xem tổng số tiền có vượt quá 80% ngân sách hay không
					IF @budget_target > 0 AND (@total_amount / @budget_target) > 0.8 THEN
						SET p_success = NULL;
						SET p_message = CONCAT('Cảnh báo: Giao dịch vượt quá 80% ngân sách ', @p_name);
					ELSE
						SET p_message = "";
					END IF;
                 END IF;
            END IF;
        END IF;
        
        
		-- bắt đầu cập nhật
		IF p_success IS NULL THEN
			-- Hoàn lại amount cũ
			IF old_category_type = 'Chi' THEN
				UPDATE Budget b
				SET b.amount = b.amount - old_amount
				WHERE b.user_id = p_user_id
				AND b.category_id = old_category_id
				AND old_transaction_date BETWEEN b.start_date AND b.end_date;
			END IF;
                
			-- Cập nhật amount mới 
			IF new_category_type = 'Chi' THEN
				UPDATE Budget b
				SET b.amount = b.amount + p_amount
				WHERE b.user_id = p_user_id
					AND b.category_id = p_category_id
					AND p_transaction_date BETWEEN b.start_date AND b.end_date;
			END IF;
                
			-- Cập nhật lại số dư ví
			IF old_wallet_id = p_wallet_id THEN
				-- Trường hợp không thay đổi số dư ví
				-- TH1: hoàn lại giao dịch cũ.
				IF old_category_type = 'Chi' THEN 
					UPDATE wallet
					SET so_du = so_du + old_amount
					WHERE id = p_wallet_id;
				ELSE
					UPDATE wallet
					SET so_du = so_du - old_amount
					WHERE id = p_wallet_id;
				END IF;
				-- TH2: cập nhật dựa trên giao dịch mới.
				IF new_category_type = 'Chi' THEN
					UPDATE wallet
					SET so_du = so_du - p_amount
					WHERE id = p_wallet_id;	
				ELSE
					UPDATE wallet
					SET so_du = so_du + p_amount
					WHERE id = p_wallet_id;	
				END IF;
			ELSE 
				-- Trường hợp thay đổi ví.
				-- TH1: hoàn lại giao dịch cũ.
				IF old_category_type = 'Chi' THEN 
					UPDATE wallet
					SET so_du = so_du + old_amount
					WHERE id = old_wallet_id;
				ELSE
					UPDATE wallet
					SET so_du = so_du - old_amount
					WHERE id = old_wallet_id;
				END IF;
				
				-- TH2: cập nhật dựa trên giao dịch mới.
				IF new_category_type = 'Chi' THEN
					UPDATE wallet
					SET so_du = so_du - p_amount
					WHERE id = p_wallet_id;	
				ELSE
					UPDATE wallet
					SET so_du = so_du + p_amount
					WHERE id = p_wallet_id;
				END IF;
			END IF;
            
            -- Cập nhật lại giao dịch
			UPDATE Transaction
			SET user_id = p_user_id,
				category_id = p_category_id,
				wallet_id = p_wallet_id,
				amount = p_amount,
				transaction_date = p_transaction_date,
				description = p_description
			WHERE id = p_transaction_id;
            
			SET p_success = true;
			IF p_message IS NOT NULL THEN
				SET p_message = CONCAT(p_message, '\nThêm giao dịch thành công!');
			ELSE
				SET p_message = 'Cập nhật giao dịch thành công!';
			END IF;
			COMMIT;
            
        END IF;

    END IF;
END main_block;
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

-- Dump completed on 2025-04-28  0:03:10
