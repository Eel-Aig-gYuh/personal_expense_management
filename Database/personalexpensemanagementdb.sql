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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
INSERT INTO `budget` VALUES (2,2,1,40000,800000,'2025-04-02','2025-04-11','2025-04-02'),(3,2,2,100000,1000000,'2025-04-04','2025-04-23','2025-04-02'),(4,2,3,0,4000000,'2025-05-01','2025-05-27','2025-04-03'),(5,2,3,0,800000,'2025-05-01','2025-05-16','2025-04-03'),(6,3,4,0,100001,'2025-04-03','2025-04-03','2025-04-03'),(7,3,4,0,200000,'2025-04-03','2025-04-03','2025-04-03'),(8,2,1,100000,500000,'2025-04-06','2025-04-06','2025-04-06'),(11,4,6,0,2000000,'2025-04-10','2025-04-18','2025-04-10'),(13,5,8,1070000,800000,'2025-04-21','2025-04-30','2025-04-21'),(14,1,1,0,1500,'2025-04-23','2025-05-23','2025-04-23'),(15,2,7,50000,500000,'2025-04-23','2025-04-23','2025-04-23');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,2,'Chi','Ăn uống'),(2,2,'Chi','Hoá đơn & Tiện ích'),(3,2,'Chi','Mua sắm'),(4,3,'Chi','Ăn uống'),(5,2,'Thu','Lương'),(6,4,'Chi','Ăn uống'),(7,2,'Chi','Di choi'),(8,5,'Chi','Ăn uống');
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
  `created_at` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  KEY `wallet_id` (`wallet_id`),
  CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `transaction_ibfk_3` FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,2,1,2,100,'2023-01-01','Change user','2025-04-08'),(2,2,5,2,4000000,'2025-04-08','luong co ban','2025-04-08'),(4,5,8,5,500000,'2025-04-21','di an voi ban','2025-04-21'),(5,5,8,5,300000,'2025-04-21','','2025-04-21'),(6,5,8,5,100000,'2025-04-21','','2025-04-21'),(7,5,8,5,50000,'2025-04-21','','2025-04-21'),(8,5,8,5,120000,'2025-04-21','','2025-04-21'),(9,2,1,2,100,'2023-01-01','Test transaction','2025-04-23'),(10,2,1,2,100,'2023-01-01',NULL,'2025-04-23');
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin123','$2a$10$iJ8UkenQeft5c52cO.ugFuN7Ij.3TsWhv/X4WBq7rr10wJzZA54Im','Huy','Lê Gia',NULL,'giahuyle1030@gmail.com','Admin','2025-04-02'),(2,'testuser1','$2a$10$4a1hvzttzPuSOIUGpqShv.zwdG0Q.rrACMAlmDQ7iUmZqI1KWA1GC','Quốc','Trần Hoàng',NULL,'quoc.th@gmail.com','User','2025-04-02'),(3,'testuser2','$2a$10$i6Az5JPUL3uEYXxeMGkOP.smKvIKulokl7.SwnoSVJMXS6aAtHWvW','Hùng','Trần',NULL,'hung.tran@gmail.com','User','2025-04-03'),(4,'testuser5','$2a$10$A5CAowN6c9eEW5eXDdaJy.d8Ldvp/IAIxby4ivrMqnXkP96uVNjOS','Huy','Le Gia',NULL,'adf@gmail.com','User','2025-04-10'),(5,'testuser7','$2a$10$qsIkQn7SsE6SymtCKtvxM.YsJqESj0yhaUY4rA9Obefkii0XMuS6i','Nam','Nguyen',NULL,'nam.n@gmail.com','User','2025-04-21'),(6,'validUser_1745398975756','$2a$10$3oYXKgeRgepcn55a44EWyeSgmaIkkMXmbHXPjCL9IxlYndl.QwlxW','Test','User','avatar.png','test1745398975758@example.com','User','2025-04-23'),(7,'validUser_1745398976406','$2a$10$G65OasB1pR/uIHILjR7JAuzTZ2tiCn87cKyFSaniyae1DD6MS4wfG','Test','User','avatar.png','test1745398976406@example.com','User','2025-04-23'),(8,'validUser_1745398976542','$2a$10$v6vVF4rEVii2XEj68ZND9OmE9yMTGbSA.IGrrqe.weLcFhbkaPeS6','Test','User','avatar.png','test1745398976542@example.com','User','2025-04-23'),(9,'validUser_1745398976673','$2a$10$8TCdV5MTifp1Mf3jjc49pui.en7L9MleDPee/hel5hDaAKjw8XN2i','Test','User','avatar.png','test1745398976673@example.com','User','2025-04-23'),(10,'validUser_1745398976789','$2a$10$UaH.WpDBL.CqjYVknTBooe0f0aNiQj1AL10ob3AbvE99OEz/ex/lS','Test','User','avatar.png','test1745398976789@example.com','User','2025-04-23'),(11,'validUser_1745398976904','$2a$10$7d5T9ZNHvFwXmsWQ1BwhuOVqKMirGxl3oRgZVBtt5dIhRPqeKzmdm','Test','User','avatar.png','test1745398976904@example.com','User','2025-04-23'),(12,'validUser_1745398977019','$2a$10$UkFFzA4NFdlV19/T8mYlp.qhWjmg1FyALrctqws/nnR5zPrBqmzje','Test','User','avatar.png','test1745398977019@example.com','User','2025-04-23'),(13,'validUser_1745398977129','$2a$10$iSRStjstklgajhEs7X74qOYko8.mHDdvgg0dwI9snH61pSwJ1RJ72','Test','User','avatar.png','test1745398977129@example.com','User','2025-04-23'),(14,'validUser_1745398977225','$2a$10$FZkW/vCntnc.wc0tPErAhOea6jJsowBL8VGXVqDq9tbFH0BP4awgK','Test','User','avatar.png','test1745398977225@example.com','User','2025-04-23'),(15,'validUser_1745398977422','$2a$10$D7REXvVPpqILGvsu1axLGuIYEj0fHDxdxgioYzJN2vQvneNLJK3Dm','Test','User','avatar.png','test1745398977422@example.com','User','2025-04-23'),(16,'user123456123','$2a$10$irLuLs4qud2U/9nttfeCH.VQqmDn1xXOVz1sFr2gmimbJHVDHT1Ni','John','Doe','avatar.jpg','user123123@example.com','User','2025-04-23'),(17,'validUser_1745398977619','$2a$10$tM7cKJwNHuS06ZVC6ZjbC.d0Tp1kOMw3lWQrZQ8cCt5rG7trHpJoe','Test','User','avatar.png','test1745398977619@example.com','User','2025-04-23'),(18,'validUser_1745398977787','$2a$10$UEAk0BEF0ova7d2zKEzRHep55SusV.rft.MK3jZghA9d/XkRQWVGe','Test','User','avatar.png','test1745398977787@example.com','User','2025-04-23'),(19,'user12345','$2a$10$5ffodZEUs6ahhFBKAMaAIOuXNjHEqardPH71ydx.vX0jsF55kXUpS','John','Doe','avatar.jpg','user@example.com','User','2025-04-23'),(20,'validUser_1745398977985','$2a$10$KPGnK4nBWVv8705.IXS7t.TMQl5tq0zktk7dDz3b6R6Uyy5VU5YaG','Test','User','avatar.png','test1745398977985@example.com','User','2025-04-23'),(21,'validUser_1745398978171','$2a$10$am.zA8afVyCQCSHgBaDTS.FlqvJEUt3qw/kets2AaD8vIrTHFvSf6','Test','User','avatar.png','test1745398978171@example.com','User','2025-04-23'),(22,'validUser_1745398978341','$2a$10$eDKaKmNARRZb1ajxAatXeOY806rmPWKHX9PQeCWwkBnKWOmRGEU42','Test','User','avatar.png','test1745398978341@example.com','User','2025-04-23'),(23,'12345677','$2a$10$ByglwAVIkUOdjBt0.6yCpe9euNitC9ALNu8JgAoRBQiEkHWRS6AJO','John','Doe','avatar.jpg','user123222@example.com','User','2025-04-23'),(24,'validUser_1745398978537','$2a$10$Vpab9vELxWcq1PL/mzenUeA0TbIhb2dsstHgh59c1hbJ7ran1uY.e','Test','User','avatar.png','test1745398978537@example.com','User','2025-04-23'),(26,'validUser_1745398978726','$2a$10$P6.tNIrsL5tOTHxfbhRKo.ToZPZs9nR8tQmPbDICAnPC5gfgDtdcK','Test','User','avatar.png','test1745398978726@example.com','User','2025-04-23'),(28,'walletTestUser_1745400579185','$2a$10$PuDDRvMjej/Y2O5QfuoTNOAS5SnyUdxdCUEFMbeDyjQfAwhNIjSB2','Wallet','Test','wallet_avatar.png','wallet_test1745400579189@example.com','User','2025-04-23'),(29,'walletTestUser_1745400579946','$2a$10$0NL0V52wV9GTC9D4kQNFg.b.NO40G6CdsN26krbu7gfJfAOH42.hi','Wallet','Test','wallet_avatar.png','wallet_test1745400579946@example.com','User','2025-04-23'),(30,'walletTestUser_1745400580222','$2a$10$xorVd2SzTpxPvm3.mTs72ORy2K8a7VNd7l.I3NuXe5s4TyWb7cnJy','Wallet','Test','wallet_avatar.png','wallet_test1745400580222@example.com','User','2025-04-23'),(31,'walletTestUser_1745400580442','$2a$10$J6Zu2n8K8ZzUdFEG95ewG.ps7lqaISTj1QfOyieRE98TaGZBxpQcK','Wallet','Test','wallet_avatar.png','wallet_test1745400580442@example.com','User','2025-04-23'),(32,'walletTestUser_1745400580662','$2a$10$h1fPwDZX1H4zjt7gCCi.xeKpELRlumZ/QbZfUgFD0ckZkKtEv22l.','Wallet','Test','wallet_avatar.png','wallet_test1745400580662@example.com','User','2025-04-23'),(33,'walletTestUser_1745400580887','$2a$10$isSVEIC.boVk/IrhK2Y/cOaDQyFvGZnnaUT7i4bXqi5qdL3LJFOCm','Wallet','Test','wallet_avatar.png','wallet_test1745400580887@example.com','User','2025-04-23');
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
INSERT INTO `wallet` VALUES (1,0,'2025-04-02'),(2,9709800,'2025-04-02'),(3,0,'2025-04-03'),(4,0,'2025-04-10'),(5,-1070000,'2025-04-21'),(6,0,'2025-04-23'),(7,0,'2025-04-23'),(8,0,'2025-04-23'),(9,0,'2025-04-23'),(10,0,'2025-04-23'),(11,0,'2025-04-23'),(12,0,'2025-04-23'),(13,0,'2025-04-23'),(14,0,'2025-04-23'),(15,0,'2025-04-23'),(16,0,'2025-04-23'),(17,0,'2025-04-23'),(18,0,'2025-04-23'),(19,0,'2025-04-23'),(20,0,'2025-04-23'),(21,0,'2025-04-23'),(22,0,'2025-04-23'),(23,0,'2025-04-23'),(24,0,'2025-04-23'),(26,0,'2025-04-23'),(28,0,'2025-04-23'),(29,0,'2025-04-23'),(30,0,'2025-04-23'),(31,0,'2025-04-23'),(32,0,'2025-04-23'),(33,0,'2025-04-23');
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
	DECLARE v_category_type ENUM('Thu', 'Chi');

	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = false;
        SET p_message = 'Lỗi: Không thể thêm danh mục. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;
    
    IF EXISTS (SELECT 1 FROM category WHERE category.name = p_name) THEN
		SET p_success = false;
		SET p_message = 'Lỗi: Danh mục đã tồn tại!';
		ROLLBACK;
	END IF;
    
    IF p_type NOT IN ('Thu', 'Chi') THEN 
		SET p_success = false;
        SET p_message = 'Lỗi: Loại danh mục chỉ được phép là "Thu" hoặc "Chi"';
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
                LIMIT 1;

                -- Nếu tồn tại ngân sách
                IF @budget_id IS NOT NULL THEN
                    -- Tính tổng số tiền giao dịch trong khoảng thời gian ngân sách (bao gồm giao dịch mới)
                    SET @total_amount = @budget_amount + p_amount;

                    -- Kiểm tra xem tổng số tiền có vượt quá 80% ngân sách hay không
                    IF @budget_target > 0 AND (@total_amount / @budget_target) > 0.8 THEN
                        SET p_message = CONCAT('Cảnh báo: Giao dịch vượt quá 80% ngân sách ', @p_name);
					ELSE
						SET p_message = "";

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
            SET p_message = CONCAT(p_message, ' Thêm giao dịch thành công!');
            COMMIT;
        END IF;
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
	DECLARE v_amount DOUBLE;
    DECLARE v_wallet_id INT;
    DECLARE v_category_id INT;
    DECLARE v_category_type ENUM('Thu', 'Chi');
    DECLARE v_transaction_date DATE;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể xóa giao dịch. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Kiểm tra các tham số đầu vào không null
    IF p_transaction_id IS NULL OR p_user_id IS NULL THEN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Thông tin không đầy đủ!';
        ROLLBACK;
    ELSE
        -- Kiểm tra xem giao dịch có tồn tại và thuộc về người dùng không
        SELECT amount, wallet_id, category_id, transaction_date
        INTO v_amount, v_wallet_id, v_category_id, v_transaction_date
        FROM Transaction
        WHERE id = p_transaction_id AND user_id = p_user_id;

        IF v_amount IS NULL THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Giao dịch không tồn tại hoặc không thuộc về người dùng này!';
            ROLLBACK;
        ELSE
            -- Lấy loại danh mục (Thu hoặc Chi)
            SELECT type
            INTO v_category_type
            FROM Category
            WHERE id = v_category_id;

            IF v_category_type IS NULL THEN
                SET p_success = FALSE;
                SET p_message = 'Lỗi: Danh mục không tồn tại!';
                ROLLBACK;
            ELSE
                -- Cập nhật số dư ví
                IF v_category_type = 'Thu' THEN
                    -- Nếu là thu, xóa giao dịch thì số dư giảm
                    UPDATE Wallet 
                    SET so_du = so_du - v_amount 
                    WHERE id = v_wallet_id;
                ELSE
                    -- Nếu là chi, xóa giao dịch thì số dư tăng
                    UPDATE Wallet 
                    SET so_du = so_du + v_amount 
                    WHERE id = v_wallet_id;
                END IF;

                -- Cập nhật ngân sách (chỉ tính giao dịch Chi)
                IF v_category_type = 'Chi' THEN
                    UPDATE Budget b
                    SET b.amount = (
                        SELECT COALESCE(SUM(t.amount), 0)
                        FROM Transaction t
                        JOIN Category c ON t.category_id = c.id
                        WHERE t.user_id = p_user_id
                        AND t.category_id = v_category_id
                        AND t.transaction_date BETWEEN b.start_date AND b.end_date
                        AND c.type = 'Chi'
                    )
                    WHERE b.user_id = p_user_id
                    AND b.category_id = v_category_id
                    AND v_transaction_date BETWEEN b.start_date AND b.end_date;
                END IF;

                -- Xóa giao dịch
                DELETE FROM Transaction
                WHERE id = p_transaction_id AND user_id = p_user_id;

                SET p_success = TRUE;
                SET p_message = 'Xóa giao dịch thành công!';
                COMMIT;
            END IF;
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
    
    SET @valid_budget = (
		SELECT id
        FROM budget
        WHERE id = p_budget_id
    );
    
    IF @valid_budget IS NULL THEN
		SET p_success = false;
        SET p_message = 'Lỗi: Không tồn tại ngân sách !';
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
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
        SET p_success = FALSE;
        SET p_message = 'Lỗi: Không thể cập nhật giao dịch. Vui lòng kiểm tra lại thông tin (có thể do lỗi cơ sở dữ liệu).';
        ROLLBACK;
    END;

    START TRANSACTION;

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
        -- Kiểm tra ngày giao dịch không vượt quá ngày hiện tại
        ELSEIF p_transaction_date > CURDATE() THEN
            SET p_success = FALSE;
            SET p_message = 'Lỗi: Ngày giao dịch không được vượt quá ngày hiện tại!';
            ROLLBACK;
        END IF;

        -- Nếu tất cả kiểm tra đều qua, thêm giao dịch
        IF p_success IS NULL THEN
            -- Cập nhật giao dịch
            UPDATE transaction 
            SET	category_id = p_category_id,
				amount = p_amount,
				transaction_date = p_transaction_date,
                description = p_description
			WHERE id = p_transaction_id;
            
            -- Cập nhật số dư ví
            SET @p_type = (SELECT type FROM category WHERE id = p_category_id);
            SET @old_amount = (SELECT amount FROM transaction WHERE id = p_transaction_id);
            
            IF @p_type = 'Thu' THEN
                UPDATE wallet 
                SET so_du = so_du - @old_amount + p_amount 
                WHERE id = p_wallet_id;
            ELSE
                UPDATE wallet 
                SET so_du = so_du + @old_amount - p_amount 
                WHERE id = p_wallet_id;
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
            SET p_message = 'Cập nhật giao dịch thành công!';
            COMMIT;
        END IF;
    END IF;
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

-- Dump completed on 2025-04-24 14:58:35
