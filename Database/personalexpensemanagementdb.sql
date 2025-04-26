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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
INSERT INTO `budget` VALUES (2,2,1,540000,800000,'2025-04-02','2025-04-11','2025-04-02'),(3,2,2,100000,1000000,'2025-04-04','2025-04-23','2025-04-02'),(4,2,3,0,4000000,'2025-05-01','2025-05-27','2025-04-03'),(5,2,3,0,800000,'2025-05-01','2025-05-16','2025-04-03'),(6,3,4,0,100001,'2025-04-03','2025-04-03','2025-04-03'),(7,3,4,0,200000,'2025-04-03','2025-04-03','2025-04-03'),(8,2,1,100000,500000,'2025-04-06','2025-04-06','2025-04-06'),(11,4,6,0,2000000,'2025-04-10','2025-04-18','2025-04-10'),(13,1,2,0,1500,'2025-05-01','2025-05-31','2025-04-22'),(14,1,1,0,1000,'2025-04-23','2025-05-23','2025-04-22'),(16,1,1,0,1000,'2025-04-24','2025-05-24','2025-04-24'),(17,1,2,0,1000,'2025-06-01','2025-06-01','2025-04-24'),(18,1,1,0,1000,'2025-04-26','2025-05-26','2025-04-26');
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,2,'Chi','Ăn uống'),(2,2,'Chi','Hoá đơn & Tiện ích'),(3,2,'Chi','Mua sắm'),(4,3,'Chi','Ăn uống'),(5,2,'Thu','Lương'),(6,4,'Chi','Ăn uống'),(7,1,'Chi','Grocery'),(8,1,'Thu','Grocery'),(9,1,'Chi','Grocery'),(10,1,'Chi','Grocery'),(11,1,'Thu','Grocery'),(12,1,'Chi','Grocery'),(13,1,'Chi','Test Category 1745406037511'),(14,1,'Chi','Test Category 1745406037850'),(15,1,'Chi','Test Category 1745407922407'),(16,1,'Chi','Test Category 1745407922732'),(17,1,'Chi','Test Category '),(18,1,'Chi','Test Category '),(19,1,'Chi','Grocery'),(20,1,'Thu','Grocery'),(21,1,'Chi','Grocery'),(22,1,'Chi','Test Category '),(23,1,'Chi','Test Category '),(24,1,'Chi','Grocery'),(25,1,'Thu','Grocery'),(26,1,'Chi','Grocery'),(27,1,'Chi','Test Category '),(28,1,'Chi','Test Category '),(29,1,'Chi','Grocery'),(30,1,'Thu','Grocery'),(31,1,'Chi','Grocery'),(32,1,'Chi','Test Category '),(33,1,'Chi','Test Category ');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,2,1,2,100,'2023-01-01','Change user','2025-04-08'),(2,2,5,2,4000000,'2025-04-08','luong co ban','2025-04-08'),(3,2,1,2,40000,'2025-04-10','hom nay di uong tra sua','2025-04-10'),(4,2,1,2,100,'2023-01-01','Test transaction','2025-04-14'),(5,2,1,2,100,'2023-01-01',NULL,'2025-04-14'),(6,2,1,2,100,'2023-01-01','Test transaction','2025-04-23'),(7,2,1,2,100,'2023-01-01',NULL,'2025-04-23'),(8,2,1,2,100,'2023-01-01','Test transaction','2025-04-23'),(9,2,1,2,100,'2023-01-01',NULL,'2025-04-23'),(10,2,1,2,100,'2023-01-01','Test transaction','2025-04-24'),(11,2,1,2,100,'2023-01-01',NULL,'2025-04-24'),(12,2,1,2,100,'2023-01-01','Test transaction','2025-04-24'),(13,2,1,2,100,'2023-01-01',NULL,'2025-04-24'),(14,2,1,2,100,'2023-01-01','Test transaction','2025-04-26'),(15,2,1,2,100,'2023-01-01',NULL,'2025-04-26'),(16,2,1,2,100,'2023-01-01','Test transaction','2025-04-26'),(17,2,1,2,100,'2023-01-01',NULL,'2025-04-26'),(18,2,1,2,100,'2023-01-01','Test transaction','2025-04-26'),(19,2,1,2,100,'2023-01-01',NULL,'2025-04-26');
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
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin123','$2a$10$iJ8UkenQeft5c52cO.ugFuN7Ij.3TsWhv/X4WBq7rr10wJzZA54Im','Huy','Lê Gia',NULL,'giahuyle1030@gmail.com','Admin','2025-04-02'),(2,'testuser1','$2a$10$4a1hvzttzPuSOIUGpqShv.zwdG0Q.rrACMAlmDQ7iUmZqI1KWA1GC','Quốc','Trần Hoàng',NULL,'quoc.th@gmail.com','User','2025-04-02'),(3,'testuser2','$2a$10$i6Az5JPUL3uEYXxeMGkOP.smKvIKulokl7.SwnoSVJMXS6aAtHWvW','Hùng','Trần',NULL,'hung.tran@gmail.com','User','2025-04-03'),(4,'testuser5','$2a$10$A5CAowN6c9eEW5eXDdaJy.d8Ldvp/IAIxby4ivrMqnXkP96uVNjOS','Huy','Le Gia',NULL,'adf@gmail.com','User','2025-04-10'),(5,'validUser_1744375087927','$2a$10$trlVOkSbFsUIfKi0TPkbEOfrj95j/FAfsrsqaihuUiLWEJpY1T7aC','Test','User','avatar.png','test1744375087928@example.com','User','2025-04-11'),(6,'user123456123','$2a$10$qTopIMbvzPZ8D0D/ovTWNuD03T8BAO5jQAzXIJwYk9mgneFh5GUcK','John','Doe','avatar.jpg','user123123@example.com','User','2025-04-11'),(7,'validUser_1744375088563','$2a$10$Rb7nKWPDZvezty3CuDYSPOKKsjglyid9mmgj1Odv78dNyE.PSQAKS','Test','User','avatar.png','test1744375088563@example.com','User','2025-04-11'),(8,'user1','$2a$10$QcJf7TVs06Im9qb6upj5BOEK2Eyhbc8E.AuEPMx6.glicvE.R9wTG','John','Doe','avatar.jpg','user@example.com','User','2025-04-11'),(9,'validUser_1744375088714','$2a$10$cj4e.RFMLi2AixjpXe2z3.ypyfBqNtTs8sLd2nBvc7I.ZpxrR8zBS','Test','User','avatar.png','test1744375088714@example.com','User','2025-04-11'),(10,'validUser_1744375088864','$2a$10$z49yN7YRhjlofsPJl3tgg.zj9NlN5jsTuWEAXTB9UFEfM6A8asxku','Test','User','avatar.png','test1744375088864@example.com','User','2025-04-11'),(12,'validUser_1744375089019','$2a$10$tzecbuKo0z8.ZJ1W.731V.IZKDs.M9zRpIBRZKgJfLY8zlrgL1B4O','Test','User','avatar.png','test1744375089019@example.com','User','2025-04-11'),(13,'validUser_1744375089154','$2a$10$BOxTAZy4EGN10JTmJQZdieODZtoxW9NLaek/DlIbqnOgGU6REVbUy','Test','User','avatar.png','test1744375089154@example.com','User','2025-04-11'),(14,'user123456','$2a$10$Yus7g8E1pgIMZUy7S4FXWOnMrdiqNkpjhW8jhaWc9zpuM6LHGP22W','John','Doe','avatar.jpg','invalid-email','User','2025-04-11'),(15,'validUser_1744375089301','$2a$10$a480.V6lfLoJFyeTrmbIHui3KEZkRxJWMW.4p26MRzWPLN.S7/pvy','Test','User','avatar.png','test1744375089301@example.com','User','2025-04-11'),(16,'validUser_1744375089438','$2a$10$QNOUDde3pzsXDbz/HC1.Zu2Fgp.GICmhwMtt9nmHuNMg2X1203PXG','Test','User','avatar.png','test1744375089438@example.com','User','2025-04-11'),(17,'12345677','$2a$10$CMsp9Y6/rdtNH49e2SpyjORUFK/4ewfeqgxjM2qsrXZErw8tcjn9O','John','Doe','avatar.jpg','user123222@example.com','User','2025-04-11'),(18,'validUser_1744375089583','$2a$10$EdSerBRGGQm108OuNliLr.6lesAenikaWNHJugt8qJOPAe38nVSFO','Test','User','avatar.png','test1744375089583@example.com','User','2025-04-11'),(20,'validUser_1744375089721','$2a$10$4CwqWywSd7aY6QReOOvlSeHJBR0wtWIh10NXamCkEoIJmbwP4ECna','Test','User','avatar.png','test1744375089721@example.com','User','2025-04-11'),(22,'validUser_1744375645760','$2a$10$hNYY5Jwg9NUaLMmwMkVol.ekGXjZVBBIm3nh48lcRQFPTu4lfnypu','Test','User','avatar.png','test1744375645763@example.com','User','2025-04-11'),(24,'validUser_1744375646306','$2a$10$2JhJ1Bkp4Kz4woBbmuGBIuJuWC/wy0cqEQuY0ZvvKKLCNYVJIY7zu','Test','User','avatar.png','test1744375646306@example.com','User','2025-04-11'),(25,'validUser_1744375646452','$2a$10$ZnVMuvXzIEQnadm13CXHPeQgPPi8O7sKomWQRZgEPzFVIYiaerS/W','Test','User','avatar.png','test1744375646452@example.com','User','2025-04-11'),(27,'validUser_1744375646599','$2a$10$YK2lDAm26zrrqrPd694UF.Oxf5F/OT2PgUr0LxVnbYnS2RZXQmi4W','Test','User','avatar.png','test1744375646599@example.com','User','2025-04-11'),(28,'validUser_1744375646749','$2a$10$eBJlRrWM1.jlY4K/12M/Q.wrzFRoJlUqd7Yyp.PRvDtuEryYzPwLO','Test','User','avatar.png','test1744375646749@example.com','User','2025-04-11'),(29,'validUser_1744375646884','$2a$10$WnNY4TnKdI.DiIxATp50FuSGhnemDtWxhLeDi64gPQfr1h6bON.xq','Test','User','avatar.png','test1744375646885@example.com','User','2025-04-11'),(31,'validUser_1744375647024','$2a$10$LcA99FQXEMFCDbQ4E7Uz4./CzqYrPgEHycp08jZnAOjpGTUYUvPJC','Test','User','avatar.png','test1744375647024@example.com','User','2025-04-11'),(33,'validUser_1744375647167','$2a$10$cq1lSf1QmoVe6PSTN93ty.c05KeHzohkbCNh7vYjGKuYdAJbt6ONe','Test','User','avatar.png','test1744375647167@example.com','User','2025-04-11'),(35,'walletTestUser_1744642509720','$2a$10$UawbuJfcF0lTCjku6j2LAem4mnZeqonpM3zcN37wfUdb7xdhVr9iG','Wallet','Test','wallet_avatar.png','wallet_test1744642509721@example.com','User','2025-04-14'),(36,'walletTestUser_1744642510253','$2a$10$nfIJ.OJlXC57VhF/v.bjL.Bg04TGSCUuo1QHp.HnAl/EeqDwLfm16','Wallet','Test','wallet_avatar.png','wallet_test1744642510253@example.com','User','2025-04-14'),(37,'walletTestUser_1744642510397','$2a$10$JOs6r/pEO8E5VpAvunp6KuTXpLPrEPfSZV97esrAO6aZl8aQ6WNpm','Wallet','Test','wallet_avatar.png','wallet_test1744642510397@example.com','User','2025-04-14'),(38,'walletTestUser_1744642510540','$2a$10$Jr8RpvCSj1L/.omd0cfLKeMy56OMA8FbsYnLuEdUy0OOC4XP0Axm6','Wallet','Test','wallet_avatar.png','wallet_test1744642510540@example.com','User','2025-04-14'),(39,'walletTestUser_1744642897115','$2a$10$TnYWCZxzxif8Udd4i.cSQey0lt98kEWn77D55TClAKR0hK5tz9vfK','Wallet','Test','wallet_avatar.png','wallet_test1744642897117@example.com','User','2025-04-14'),(40,'walletTestUser_1744642897644','$2a$10$RtoE4obvht9.dseKwY0ZxuVEyNojVLsnX/ryQRLgdsPZkc2Ipx3Ia','Wallet','Test','wallet_avatar.png','wallet_test1744642897644@example.com','User','2025-04-14'),(41,'walletTestUser_1744642897787','$2a$10$B7w3ldTEzAiFSR6Qi1GuUese0z9ISz5sUVkpMMr4vyuZQKlcGvm0G','Wallet','Test','wallet_avatar.png','wallet_test1744642897787@example.com','User','2025-04-14'),(42,'walletTestUser_1744642897932','$2a$10$3QwfX8pM8LJ.1sZyLUGCL.wAK9kwy1i.46UWQ3BNZulq1HHAY8o32','Wallet','Test','wallet_avatar.png','wallet_test1744642897932@example.com','User','2025-04-14'),(43,'walletTestUser_1744643023736','$2a$10$q42t5xfwLygt1EG6zYHTJeSwGKsvXI7MbQlWkmCntxF5DWJ7USSAe','Wallet','Test','wallet_avatar.png','wallet_test1744643023737@example.com','User','2025-04-14'),(44,'walletTestUser_1744643024312','$2a$10$8gpL1FL5dXkuIrYpEQDjweCE/fyjYxsKoUMn748HIVhoLW7ID6wA6','Wallet','Test','wallet_avatar.png','wallet_test1744643024312@example.com','User','2025-04-14'),(45,'walletTestUser_1744643024490','$2a$10$z6s2FbmBE9tlMMSKHZqg2OMSlzcUQZ4/VU3dqEyhtxUWbvcF8zXyS','Wallet','Test','wallet_avatar.png','wallet_test1744643024490@example.com','User','2025-04-14'),(46,'walletTestUser_1744643024654','$2a$10$Xlor/QN4tU6E53y7kc0lQukkpch6BkNqc.ftZs.izZypd89D2dAWa','Wallet','Test','wallet_avatar.png','wallet_test1744643024654@example.com','User','2025-04-14'),(47,'walletTestUser_1744643124435','$2a$10$tUCtI8TbtTGi/c.mP5XOVentpuLTUh0bze6cB/u/rWEK1u5xGgcte','Wallet','Test','wallet_avatar.png','wallet_test1744643124438@example.com','User','2025-04-14'),(48,'walletTestUser_1744643125042','$2a$10$BLx7xd4urcPgGkELZmcCXO1KWe51t/cuzNTEmgS6xG/Hk5Tg8G1CW','Wallet','Test','wallet_avatar.png','wallet_test1744643125042@example.com','User','2025-04-14'),(49,'walletTestUser_1744643125239','$2a$10$BN/4eNCu.iMfLdT2W06apOAQ6RANpOY2bSlEGo08LcH6QvIZ4vk9O','Wallet','Test','wallet_avatar.png','wallet_test1744643125239@example.com','User','2025-04-14'),(50,'walletTestUser_1744643125424','$2a$10$UxvaTuibUEcMZinbWRnLnOw9u2krrRGVSGIWwjap3lrr89xQCKey6','Wallet','Test','wallet_avatar.png','wallet_test1744643125424@example.com','User','2025-04-14'),(51,'walletTestUser_1744868114579','$2a$10$2lbDCBN4S6kA8bWnWPzF4O2ELgYxt0rl1v.jtZuwCk.MRbyj/4JVa','Wallet','Test','wallet_avatar.png','wallet_test1744868114580@example.com','User','2025-04-17'),(52,'walletTestUser_1744868115121','$2a$10$boXjRPvRD1ig060jkcoia.mUqA67/TnCqIUi23CrYhyqK/cJFy2FK','Wallet','Test','wallet_avatar.png','wallet_test1744868115121@example.com','User','2025-04-17'),(53,'walletTestUser_1744868115276','$2a$10$rAQQMATqghcA.Et8rzJZjOHco11kpmfz/SDYmJIobXU3LTv4aa.Tq','Wallet','Test','wallet_avatar.png','wallet_test1744868115276@example.com','User','2025-04-17'),(54,'walletTestUser_1744868115421','$2a$10$29hBViSaWYzSKBoho7x9FepX1SuX7Q4vz.HXweJZTY4Y3vmeNfN4a','Wallet','Test','wallet_avatar.png','wallet_test1744868115421@example.com','User','2025-04-17'),(55,'walletTestUser_1744868172579','$2a$10$hMt5Gapsi.Sj3Il9MxgnSOtzJ41lHRM55G8aIDxh.bwgswFeMRVlK','Wallet','Test','wallet_avatar.png','wallet_test1744868172581@example.com','User','2025-04-17'),(56,'walletTestUser_1744868173185','$2a$10$vPYTUBGRDaorESfKNZPSJu8dUNpsQRrtR/OBNsrFOMJuyJL6Gyte2','Wallet','Test','wallet_avatar.png','wallet_test1744868173185@example.com','User','2025-04-17'),(57,'walletTestUser_1744868173384','$2a$10$oMOspzkJPVTM2mQX4i9qRePjW.ftmJi8qh/.MwDFnAZ0HLQgmw0CO','Wallet','Test','wallet_avatar.png','wallet_test1744868173384@example.com','User','2025-04-17'),(58,'walletTestUser_1744868173570','$2a$10$iTFnbgyXbAUW.KxD/GQ84OwiI/qmau7QRaQc9lsUTrzVloMccmneG','Wallet','Test','wallet_avatar.png','wallet_test1744868173570@example.com','User','2025-04-17'),(59,'walletTestUser_1744904160428','$2a$10$QorjV7z5IdIM1lla.DZX0uXom5g3ErunUC0083/P5XQVPmjYwBURu','Wallet','Test','wallet_avatar.png','wallet_test1744904160430@example.com','User','2025-04-17'),(60,'walletTestUser_1744904161023','$2a$10$BSjkohp6WejJ5oXzQSFgSu5ktFTUkDBPBg9wkE.zNM01fOp4NBGOu','Wallet','Test','wallet_avatar.png','wallet_test1744904161023@example.com','User','2025-04-17'),(61,'walletTestUser_1744904161203','$2a$10$EHNzG6ME.SUWDmqPIcJSuuSabA.LbkHUdioZ0VJtPgYk1wXURx/oa','Wallet','Test','wallet_avatar.png','wallet_test1744904161203@example.com','User','2025-04-17'),(62,'walletTestUser_1744904161372','$2a$10$imNCYGAME21a.oucfI1fxei/1473BjxDgbank6VkhSb67wthhd5hW','Wallet','Test','wallet_avatar.png','wallet_test1744904161372@example.com','User','2025-04-17'),(63,'walletTestUser_1744904536994','$2a$10$sUgFXOEmYelpIStkE3noG.7ARW20mzo1Z6kzoMKD6KfwZKhyGVODG','Wallet','Test','wallet_avatar.png','wallet_test1744904536997@example.com','User','2025-04-17'),(64,'walletTestUser_1744904537572','$2a$10$FdcQhu1HXb4jfBw132T02uFGKx3CyuOJW4lZgCebYqZddYKM22A/K','Wallet','Test','wallet_avatar.png','wallet_test1744904537572@example.com','User','2025-04-17'),(65,'walletTestUser_1744904537747','$2a$10$HOAUZCX.IfhFYM1Jd2gO4Oz5D4CbJznZSXc1vnpVYR57VC0IMEdjG','Wallet','Test','wallet_avatar.png','wallet_test1744904537747@example.com','User','2025-04-17'),(66,'walletTestUser_1744904537927','$2a$10$z4bbEVkY8pZ54v6mYUPjfufkXg5QXgRY5NXMZIzblelwaYEHpkULW','Wallet','Test','wallet_avatar.png','wallet_test1744904537927@example.com','User','2025-04-17'),(67,'walletTestUser_1744904610633','$2a$10$Wz06mCCNiLAWj.lXESGgEOL9BzAjAbi0fDHjH.Dx/q05udrOMZcv2','Wallet','Test','wallet_avatar.png','wallet_test1744904610634@example.com','User','2025-04-17'),(68,'walletTestUser_1744904611196','$2a$10$Jj0/JjUEI9SXDNbNjuoLRu2FkJ7.MXt24dTHeS.QhBrOcFSG4KS3O','Wallet','Test','wallet_avatar.png','wallet_test1744904611196@example.com','User','2025-04-17'),(69,'walletTestUser_1744904611374','$2a$10$WrTUwuHokTMMDx8Z.ytke.6DWtTOgaJp/Wk6GWu9hX68.xb25rcVq','Wallet','Test','wallet_avatar.png','wallet_test1744904611374@example.com','User','2025-04-17'),(70,'walletTestUser_1744904611540','$2a$10$kgMj9GJDAjL60c46pKgmoeOcuIbDRgpKdsP4FeWvH2yzp5YLB73he','Wallet','Test','wallet_avatar.png','wallet_test1744904611540@example.com','User','2025-04-17'),(71,'walletTestUser_1744905466451','$2a$10$L13DwvnSzdoF9yEV4siZ9.coxUGAyw5xcoxaxVa/Q83OQQKc2Y/pa','Wallet','Test','wallet_avatar.png','wallet_test1744905466452@example.com','User','2025-04-17'),(72,'walletTestUser_1744905467022','$2a$10$aDyx6MBI5Rqt6jvhFDEfgOIFhiMZnHlWfjc7LHHbXnCfZdvt3riSC','Wallet','Test','wallet_avatar.png','wallet_test1744905467022@example.com','User','2025-04-17'),(73,'walletTestUser_1744905467215','$2a$10$WyyKjP6oWkG9cN8z1DK.KO6owhUjDm/Lb2bFgCbXDsqh6sHpXz1qa','Wallet','Test','wallet_avatar.png','wallet_test1744905467215@example.com','User','2025-04-17'),(74,'walletTestUser_1744905467387','$2a$10$xVQvCf0DV0jHN826WjqIou4mTtJowKJMxqgBZvOj86o4/Dwv5puXC','Wallet','Test','wallet_avatar.png','wallet_test1744905467387@example.com','User','2025-04-17'),(75,'walletTestUser_1744905530795','$2a$10$2SLrMp4xbMLD5LDbHVncv.ako3q81vL5zomUzuvnrIvmSw1eIEt4i','Wallet','Test','wallet_avatar.png','wallet_test1744905530796@example.com','User','2025-04-17'),(76,'walletTestUser_1744905531375','$2a$10$wne0r6E7ATTXGYJC8eBb3.yWgnOlNa/V5m35VxBgAIzyocg6VwOKy','Wallet','Test','wallet_avatar.png','wallet_test1744905531375@example.com','User','2025-04-17'),(77,'walletTestUser_1744905531559','$2a$10$NArF8/v3uZlpV3rtMXwq8O7sF/tQOxyWJhITj6941sf4LYc9yVpJu','Wallet','Test','wallet_avatar.png','wallet_test1744905531559@example.com','User','2025-04-17'),(78,'walletTestUser_1744905531727','$2a$10$5gJAm.XW/JNMnwrXaHH0fe5J0RFqESeRxrXmrx6P0JAB3w.MBFG3y','Wallet','Test','wallet_avatar.png','wallet_test1744905531727@example.com','User','2025-04-17'),(79,'validUser_1745405153873','$2a$10$Pi1fcRYYA1w5q0Xavej9V.zH8iOS80RX2o.8dOWu13Q694nwnKJS2','Test','User','avatar.png','test1745405153874@example.com','User','2025-04-23'),(81,'validUser_1745405154492','$2a$10$SqG7OZ7jE/OCx6lC9rp5qe9A9MuwLCWh2WflKKJ98nLE2yg1SYqnG','Test','User','avatar.png','test1745405154492@example.com','User','2025-04-23'),(82,'validUser_1745405154643','$2a$10$WDpXPbDAmKeueJMafBdJG.2hA2GZfoY5QdFSw8bgZqMWq52HzMDba','Test','User','avatar.png','test1745405154643@example.com','User','2025-04-23'),(84,'validUser_1745405154798','$2a$10$9N3mbmUUF8Qzp.Q1U892R.6LaKXf3Hj9gs3HpX47/susZRSxsNAta','Test','User','avatar.png','test1745405154798@example.com','User','2025-04-23'),(85,'validUser_1745405154947','$2a$10$L5dvmtrfUGu7V3dzVDyPy.aDdziXV9zSyIqLV2VQKAfNDEUmHdy12','Test','User','avatar.png','test1745405154947@example.com','User','2025-04-23'),(86,'validUser_1745405155083','$2a$10$jpUSu/CI7KGq7hcDEOXH5OWbUfLoUGD3FcTxWKwSPisXs7p4j31Ga','Test','User','avatar.png','test1745405155083@example.com','User','2025-04-23'),(88,'validUser_1745405155221','$2a$10$LKtuI07DrhVKoeQlk7aHeO6Q.VQLqF0a.j3dF6yNc.he0ktDaotIe','Test','User','avatar.png','test1745405155221@example.com','User','2025-04-23'),(90,'validUser_1745405155365','$2a$10$p9bhrGWgJSnztCMOhCaM.unMW7Mq1qe/5zYUcbgTeY.sMNSKRs19.','Test','User','avatar.png','test1745405155365@example.com','User','2025-04-23'),(92,'validUser_1745405208238','$2a$10$R7IEnc.2Ad43eIB2B1qmhu9rSG9Soz2/d.yXFK62r2j2Zg17sYyP.','Test','User','avatar.png','test1745405208240@example.com','User','2025-04-23'),(93,'validUser_1745405208779','$2a$10$o0Qqp/6RKuLECiV/2HgQ4.sB2HKtoJM5QvrP83NGkS2Pe1C3jWh7C','Test','User','avatar.png','test1745405208779@example.com','User','2025-04-23'),(94,'validUser_1745405208871','$2a$10$Mvm7e8kQUriEbsS8LscxmOFunIkLR7IgQnJRnqRQKAjcvJX89pOMW','Test','User','avatar.png','test1745405208871@example.com','User','2025-04-23'),(95,'validUser_1745405208969','$2a$10$p3Hk2vxsZ9ssEdOzZintZOIwKlZTZUtAThBDpTJgMDnivin3/KYkm','Test','User','avatar.png','test1745405208969@example.com','User','2025-04-23'),(96,'validUser_1745405209056','$2a$10$azmOyXRQohelASpKxJ3LaemjEijEMFWQ4ni/8QWyGIe6MeVm3EN.i','Test','User','avatar.png','test1745405209056@example.com','User','2025-04-23'),(97,'validUser_1745405209143','$2a$10$gxntx7CMDQH102glc3zL/.LAU9pVjzV83UolYSQCI.XmYiQU4qoBG','Test','User','avatar.png','test1745405209143@example.com','User','2025-04-23'),(98,'validUser_1745405209230','$2a$10$J4w3H4FxQeN3YLMqFnah4O8VhhsJ5FNYdv3iIkdnXYf8O3l45Wxtm','Test','User','avatar.png','test1745405209230@example.com','User','2025-04-23'),(99,'validUser_1745405272797','$2a$10$Oqu3YH8kSfUPbA3hZro4Ouc52rLgX1q5.1ZbnOefNL/Fjv33b/Ezu','Test','User','avatar.png','test1745405272798@example.com','User','2025-04-23'),(100,'validUser_1745405273215','$2a$10$FDenuHatxjcPXjGnq9dgT.EV8j/lSsKVHwlc7.uVDnKhGRy5gfrL.','Test','User','avatar.png','test1745405273215@example.com','User','2025-04-23'),(101,'validUser_1745412713718','$2a$10$dHMCuRWSEywGRENHTHVGfuM01AP71p282duUJyoA793XVP0QwjPdq','Test','User','avatar.png','test1745412713719@example.com','User','2025-04-23'),(102,'validUser_1745412714129','$2a$10$lHeO6V7ZSGpQdFdIEYuBBuIHn09shArd..OzFS.JcMeP3VvjzGYlG','Test','User','avatar.png','test1745412714129@example.com','User','2025-04-23'),(103,'validUser_1745412724111','$2a$10$VCjHiwvYDpeBX6RpuISqMe3rN3fk0oy/2IZYIaWSG7dLG98OJFt3S','Test','User','avatar.png','test1745412724112@example.com','User','2025-04-23'),(104,'validUser_1745412724665','$2a$10$K2eOI/oKGJ2Uo3tJp8OkquBqNTa0ItgEcYAFAa4spofonC.ocJFuW','Test','User','avatar.png','test1745412724665@example.com','User','2025-04-23'),(105,'validUser_1745412724766','$2a$10$5W/uMTiSuebm3UdWqMD2nuAcimZyrDNEjQv7cWMTvBgfXPF3oMYRe','Test','User','avatar.png','test1745412724766@example.com','User','2025-04-23'),(106,'validUser_1745412724867','$2a$10$1UKGMmYfB5kfZ3HrAnsk4u7d6/wQqT8UWrxjMyrvCcQVfZd3FypYW','Test','User','avatar.png','test1745412724867@example.com','User','2025-04-23'),(107,'validUser_1745412724965','$2a$10$jZx9ErQbi028F7ze/ZO3zOZQYfay5BFmZUgmWYy7dr51c96Ht4boq','Test','User','avatar.png','test1745412724965@example.com','User','2025-04-23'),(108,'validUser_1745412725060','$2a$10$GBOYnnzIuHkH5R2OrY4FIetuhszxiqWF8kQaunFCxDbMm0i3yS3W6','Test','User','avatar.png','test1745412725060@example.com','User','2025-04-23'),(109,'validUser_1745412725153','$2a$10$jgxl00sXsZE3E9orNwuleOQhWRZJTioHJa7PvrYYK7jFI5/TlDn72','Test','User','avatar.png','test1745412725153@example.com','User','2025-04-23'),(110,'validUser_1745484741022','$2a$10$I.MuvSDvpZQM1OhGBA1ooey/aEbzrOYKqOwboiWz9xgh3fBxrNzVu','Test','User','avatar.png','test1745484741025@example.com','User','2025-04-24'),(111,'validUser_1745484741797','$2a$10$Tl8lnYLbImD5IVwoFgpyIOnJ8zuHVrQWDLzLD4qohvNho2AGBtTo6','Test','User','avatar.png','test1745484741797@example.com','User','2025-04-24'),(112,'validUser_1745484741924','$2a$10$jWmIJtX8ORD0/2n/OgBMBOdmK3mmi598Jp.UqgQ8hdhR0q8WH2lNi','Test','User','avatar.png','test1745484741924@example.com','User','2025-04-24'),(113,'validUser_1745484742044','$2a$10$tw2Ma.Grq2rbLL1obrEv4upllBwOBY0T/lMyvseTsIX4DXjw62ExG','Test','User','avatar.png','test1745484742044@example.com','User','2025-04-24'),(114,'validUser_1745484742158','$2a$10$eloNYyR6S0apN8fRXpXr6eqIvz6D5HvqNzTLGwWkqm.Yfgg7YcYSy','Test','User','avatar.png','test1745484742158@example.com','User','2025-04-24'),(115,'validUser_1745484742277','$2a$10$c/Nb6TRHZrtL5ECmfQjyNuiJ2x70DgZRRYhIr0qCdOA1TfP/ePopO','Test','User','avatar.png','test1745484742277@example.com','User','2025-04-24'),(116,'validUser_1745484742392','$2a$10$6pL85dxCSetKzYX5r3Mlr.yJiJ61IZIS6YyDA93F21WpjYP4qXH5C','Test','User','avatar.png','test1745484742392@example.com','User','2025-04-24'),(117,'validUser_1745484742509','$2a$10$CMwnoIWLWSE2wxURMKackuWrLQPYulEj9z3eQnGikAZINBE3HDRqi','Test','User','avatar.png','test1745484742509@example.com','User','2025-04-24'),(118,'validUser_1745484742604','$2a$10$29mqgFu2MkxObOLVQnYSKe55k/aNyGvVLWxnRvGOlX2rOzTqp/E6q','Test','User','avatar.png','test1745484742604@example.com','User','2025-04-24'),(119,'validUser_1745484742802','$2a$10$9h3ZngXfEfHztIVYTxgmu.X.TmJDXlmBILMwm0waVvczHmaG8QXhO','Test','User','avatar.png','test1745484742802@example.com','User','2025-04-24'),(121,'validUser_1745484742989','$2a$10$MENHDBviZMdp4a.VVsMJXOYn2JZ./F9wYappon17F.afRBdieSg7K','Test','User','avatar.png','test1745484742989@example.com','User','2025-04-24'),(122,'validUser_1745484743168','$2a$10$JZSkzjgaG0J8w1VWTouD8O8BjGzQSc1TTqcS/n4x/zS6nwTmhdd1S','Test','User','avatar.png','test1745484743168@example.com','User','2025-04-24'),(124,'validUser_1745484743339','$2a$10$OU3Yvo.EWHWgS.dFTzu0t.K1Hti8ljszcKN4T2Ekf/dGqmkIGVDiC','Test','User','avatar.png','test1745484743339@example.com','User','2025-04-24'),(125,'validUser_1745484743546','$2a$10$7IkWH4vxmGN0nPpeK07mXOWPfPj/hcfckxXddKkfMF/qdMcZpOXJe','Test','User','avatar.png','test1745484743546@example.com','User','2025-04-24'),(126,'validUser_1745484743748','$2a$10$e.HQritxUPfXzi/L.n508eVTlelEBGQ9ReT0c24leZhFwAIrZ7mGy','Test','User','avatar.png','test1745484743748@example.com','User','2025-04-24'),(128,'validUser_1745484743956','$2a$10$hBGgdnEofMmRxLuvCCbZ9OS3fpNbOvhKPfakiuXGSCDUX07Z.tBZW','Test','User','avatar.png','test1745484743956@example.com','User','2025-04-24'),(130,'validUser_1745484744162','$2a$10$BythcuECqIFhApeD8MNzXuUl.IwMGK3ydmhAwyrA9/2pC5QihXVV6','Test','User','avatar.png','test1745484744162@example.com','User','2025-04-24'),(132,'validUser_1745485105572','$2a$10$dCguJulwySVTW3sxPob8k.akR1a0Y/DOLVjlZWLIRnbAagOzBKsJK','Test','User','avatar.png','test1745485105575@example.com','User','2025-04-24'),(133,'validUser_1745485106389','$2a$10$Er80pOEZiHwh9A/wsIq3/OcBy4BPYjHCpUoCMFPuw3tQv0cVZ/SDa','Test','User','avatar.png','test1745485106389@example.com','User','2025-04-24'),(134,'validUser_1745485106527','$2a$10$IZTm1WVM79Nue/EWOkz7Ae2PI4vHMFiKmTxexiQ.wU0rvNfuxtd3e','Test','User','avatar.png','test1745485106527@example.com','User','2025-04-24'),(135,'validUser_1745485106673','$2a$10$vMyOI8kIQPQ9NaRILxsyV.IMCWmnu4TRrgCCfp0Mfv76MfDJkOcPC','Test','User','avatar.png','test1745485106673@example.com','User','2025-04-24'),(136,'validUser_1745485106794','$2a$10$3Mj13neHgGjvQuuYHkyRHOSDx/8DogTCaJxMiZ3/JDe1hIktULksS','Test','User','avatar.png','test1745485106794@example.com','User','2025-04-24'),(137,'validUser_1745485106952','$2a$10$h9cxrOkaDdAZ.2.DiIrc7ui9L.dgEwJyoV4hsuqNeOVQn6iukL..2','Test','User','avatar.png','test1745485106952@example.com','User','2025-04-24'),(138,'validUser_1745485107081','$2a$10$gGlIyfOn0tUHUjphVgx.zeJes8wE2jdY9udoFJkJ23LQscsAFwaXa','Test','User','avatar.png','test1745485107081@example.com','User','2025-04-24'),(139,'validUser_1745485107199','$2a$10$JXSOLD3k7xH6fHflennhQezl3jYhB.gRGjxTh9JvpVeQd3MjeUS3G','Test','User','avatar.png','test1745485107199@example.com','User','2025-04-24'),(140,'validUser_1745485107318','$2a$10$rFC6f8A8DwQ8egzBgM0djudGcu6Js0aPHQhJ091t0yjKXUx2gOB2.','Test','User','avatar.png','test1745485107318@example.com','User','2025-04-24'),(141,'validUser_1745485107523','$2a$10$3Wh3S.LyGSFd.OnO1bS.p.BG8Ya7CPljr/zCs9fo0wlBLNiAkCGDW','Test','User','avatar.png','test1745485107523@example.com','User','2025-04-24'),(143,'validUser_1745485107744','$2a$10$o4ZVrKOMqi2eYLPzki26iOcgPTcnAeaKloP7eEaWmiKPHv17d7.7K','Test','User','avatar.png','test1745485107744@example.com','User','2025-04-24'),(144,'validUser_1745485107959','$2a$10$./L4Z19sY.1Vf73DpgIxhO2Gzpc/AsDfVD1UsnFLkfnhPFHxc6hDq','Test','User','avatar.png','test1745485107959@example.com','User','2025-04-24'),(146,'validUser_1745485108172','$2a$10$m70vUCM.3rhtceExHlPmJuSNUUKTz3mHxaPDFiplziUhnE5A4yR2S','Test','User','avatar.png','test1745485108172@example.com','User','2025-04-24'),(147,'validUser_1745485108365','$2a$10$SFIEJbxgwjqtzd6Ck0hwZOPch9Eq3TfhPapolxJe.u75hxGxe5YTS','Test','User','avatar.png','test1745485108365@example.com','User','2025-04-24'),(148,'validUser_1745485108566','$2a$10$WmcrdHoPYyP0hoAhEm7/3.AY0e0GI4TYClaJyVBb69Ji7AR3x/Gea','Test','User','avatar.png','test1745485108566@example.com','User','2025-04-24'),(150,'validUser_1745485108769','$2a$10$g815gQ7y9ZLwiVOvz9vLau46KcGLL7no2aVUEAhDgqgaBMp4HVA66','Test','User','avatar.png','test1745485108769@example.com','User','2025-04-24'),(152,'validUser_1745485108977','$2a$10$o16E74kJC0o.qKKTBfN41O2GyST3KSnsEvOOnLNOfhrnpys5VsGYe','Test','User','avatar.png','test1745485108977@example.com','User','2025-04-24'),(154,'validUser_1745504451014','$2a$10$ceXFoxnUjb0xPMFbu3Sbv.Aabsjd6NMMsbwTVnf.H8okE5PjLMHKa','Test','User','avatar.png','test1745504451016@example.com','User','2025-04-24'),(155,'validUser_1745504451754','$2a$10$LHT6emyoMgfnZ8yRkMYVo.ff4CC2N4KYIuL0AFeP3Aac8d44W/Tza','Test','User','avatar.png','test1745504451754@example.com','User','2025-04-24'),(156,'validUser_1745504451875','$2a$10$sm6gBgNkHRgzo4Ix52TBnODSdf93nPznyn1kCjvIf7rjUtwxqR1UK','Test','User','avatar.png','test1745504451875@example.com','User','2025-04-24'),(157,'validUser_1745504452008','$2a$10$Uwg63W8.wowyHGjc0hDtEuBPrMHL3Rvz.FMubQotKGAgonOmEmjyi','Test','User','avatar.png','test1745504452008@example.com','User','2025-04-24'),(158,'validUser_1745504452128','$2a$10$Xy8skvPZAvL5w7ZW6c.S5O.joUxR7R.Za6cS96kZXXG.YIVXqx5JC','Test','User','avatar.png','test1745504452128@example.com','User','2025-04-24'),(159,'validUser_1745504452241','$2a$10$xxD91xV9GzUyDlYwVQbshOxLUy0aK9T/bz6cdEquAHrVyDQoo1TB2','Test','User','avatar.png','test1745504452241@example.com','User','2025-04-24'),(160,'validUser_1745504452352','$2a$10$ntas3Ap7zLvP6FJSO4ipV.x4HmrGuDKsRVn5wfJtnY0ockpn718KW','Test','User','avatar.png','test1745504452352@example.com','User','2025-04-24'),(161,'validUser_1745504452469','$2a$10$etIowhs/tdojEjlEUB9sQOqV0wyCa5bzc6Ulo0K3T4F4zQAJG0pl2','Test','User','avatar.png','test1745504452469@example.com','User','2025-04-24'),(162,'validUser_1745504452564','$2a$10$HRoNZsRnikxExvBZmFY9NeK2sO1y/v0.6yk27gt7clrzHfEmGFFWa','Test','User','avatar.png','test1745504452564@example.com','User','2025-04-24'),(163,'validUser_1745504452761','$2a$10$dGW9R44kSjHIltP0TL6Jk.Q2u3QAzkN.DBMlwwPGb8bDedkGxqH3O','Test','User','avatar.png','test1745504452761@example.com','User','2025-04-24'),(165,'validUser_1745504452958','$2a$10$N.VyBYf9npvwHgGnRhw7S.xQjyKOR08ilzikmMe39Nhxz.EE7en/O','Test','User','avatar.png','test1745504452958@example.com','User','2025-04-24'),(166,'validUser_1745504453138','$2a$10$.NZlwBmAa9KXx54RaK4flu3JRB3wx19NoT6nAWHO9ydNZZq/KUXWK','Test','User','avatar.png','test1745504453138@example.com','User','2025-04-24'),(168,'validUser_1745504453316','$2a$10$NMMYMbzcIggv7TrjqijBcOvRZPeAcrTN.oCGxN/qEYFhr6zyOLWFS','Test','User','avatar.png','test1745504453316@example.com','User','2025-04-24'),(169,'validUser_1745504453503','$2a$10$MrtrY4mpJJY2VV9t0GSJMOHUHxG71qrtnzN9rEUnKf.BIHT2KEXze','Test','User','avatar.png','test1745504453503@example.com','User','2025-04-24'),(170,'validUser_1745504453689','$2a$10$7MlsWBCpCxr4961tDdvyteoTx2gCVlUmnHeE1igecBUBQvULouXrO','Test','User','avatar.png','test1745504453689@example.com','User','2025-04-24'),(172,'validUser_1745504453870','$2a$10$L9rAjPWrR0Fl4DgqqLb2U.BibvGmm4ybr7ArFNH823HPtfxJXKKHu','Test','User','avatar.png','test1745504453870@example.com','User','2025-04-24'),(174,'validUser_1745504454053','$2a$10$BaYC0yAwOagXKmscrofzSuQv.LOtSN7sQzcDUyvV0gAL.8Wx7vQxS','Test','User','avatar.png','test1745504454053@example.com','User','2025-04-24'),(176,'validUser_1745647242734','$2a$10$VnQu0AS7wZjUMpEppggyN.6qmFJIf62hdlu9HFUa30Q8t3QQfXLz.','Test','User','avatar.png','test1745647242736@example.com','User','2025-04-26'),(177,'validUser_1745647243494','$2a$10$Sb2fD50/141Zg6aYOT2H8.gp07GUjUr7G8FEvrVe0euYA.XxW/enW','Test','User','avatar.png','test1745647243494@example.com','User','2025-04-26'),(178,'validUser_1745647243619','$2a$10$T/eNgs860QnQMuRMOnbft.cmHXbpP9ADCETQ8xRHDaX7SJGhjXt5K','Test','User','avatar.png','test1745647243619@example.com','User','2025-04-26'),(179,'validUser_1745647243733','$2a$10$0LKsEFOCVN4Y2rtTA0N9cOiH4Ec9k7f6du8Xz8J9uSXIpX8ZgYuaG','Test','User','avatar.png','test1745647243733@example.com','User','2025-04-26'),(180,'validUser_1745647243863','$2a$10$k18vOl1nubmLF7/anOJkm.ImHEO3H2OngXLNJCQb85Mijw.E99fOK','Test','User','avatar.png','test1745647243863@example.com','User','2025-04-26'),(181,'validUser_1745647243980','$2a$10$PDOna42TnU2hxxdqAVGNBO6xjIbym1Fvn2bWQ0Pd.U3Tle88zHpVq','Test','User','avatar.png','test1745647243980@example.com','User','2025-04-26'),(182,'validUser_1745647244098','$2a$10$23P2fmU2O2aGG9jw1949h.nNI.cW0h64vaSLeRAqokmr7FTOhUQs.','Test','User','avatar.png','test1745647244098@example.com','User','2025-04-26'),(183,'validUser_1745647244218','$2a$10$lc01/KBl4nfXJh4gzxdY1uyjtlNvEmW21f.rJ0HVwHAJDY4bP3jdm','Test','User','avatar.png','test1745647244218@example.com','User','2025-04-26'),(184,'validUser_1745647244322','$2a$10$mp3ebqsY9XyRsrY0UgJ/2.tIqdg1djoQ5sWhhO36jzchxX3w7OKw6','Test','User','avatar.png','test1745647244322@example.com','User','2025-04-26'),(185,'validUser_1745647244515','$2a$10$ScytWHhGcZ4rFCcBDHnmf.qn6O6orKrJCl.a2QTtCR5Is6htXNtwS','Test','User','avatar.png','test1745647244515@example.com','User','2025-04-26'),(187,'validUser_1745647244719','$2a$10$isAkyT6a7vKQuvIKSb5bvehByihhjcVF/LSVhe.oUavXePXRKeLKi','Test','User','avatar.png','test1745647244719@example.com','User','2025-04-26'),(188,'validUser_1745647244907','$2a$10$Vyd3ZcvIaCho.l5R95xtX.RAQpFm0O5F.t5P8jaIPinmqTSat0tuW','Test','User','avatar.png','test1745647244907@example.com','User','2025-04-26'),(189,'validUser_1745647245085','$2a$10$ZhrjL8qv9QqPkE2M6Een.uBXFQYOtbBzk1KYrby2PSIbFfDjqWODq','Test','User','avatar.png','test1745647245085@example.com','User','2025-04-26'),(191,'validUser_1745647245262','$2a$10$/jHNC8UgSxxv.jV.ktfY5urQXKX.hd/1iJnKsfxGGgKcUUzt0KyHm','Test','User','avatar.png','test1745647245262@example.com','User','2025-04-26'),(192,'validUser_1745647245451','$2a$10$jZhq88Zz/yskWr/Q6YcS9eT9evjoiV2WsW4cnOeJqgP8uniWvpFme','Test','User','avatar.png','test1745647245451@example.com','User','2025-04-26'),(194,'validUser_1745647245641','$2a$10$Z/SqIiOtE1GerKeD0cI0zuQyVggUJquT2jF/iAJeSGGNjMw88Pl0K','Test','User','avatar.png','test1745647245641@example.com','User','2025-04-26'),(196,'validUser_1745647245840','$2a$10$l0QSSTL7DHVVjh/Qa.UHYe/W0adAJfOuUCq2HHlK7IaSwbkLGANvW','Test','User','avatar.png','test1745647245840@example.com','User','2025-04-26'),(198,'validUser_1745647246046','$2a$10$TvbW9cRjwdk9o9uO8bvLd./uWeIF1Yv6Pp.zZwJykaZvpz/rtqpK.','Test','User','avatar.png','test1745647246046@example.com','User','2025-04-26'),(199,'user_lower','$2a$10$VQkPtcd0sRTgTo/r0ZkmaOOggLfu4pwFK0vSRIZTg5zGTL8nfPb9e','Jane','Doe','avatar.jpg','noupper@test.com','User','2025-04-26'),(200,'validUser_1745647246258','$2a$10$9V1rgyS5W38o6uQNZY6WT.0xpE2gCIGpznY6B9iLsGQp50HaANvbS','Test','User','avatar.png','test1745647246258@example.com','User','2025-04-26'),(201,'user_upper','$2a$10$U5G/VMQiMBIjvSvdhzPXcOy6R.f/fUPENIBAllz5TFsE3OsFoisam','Bob','Smith','avatar.jpg','nolower@test.com','User','2025-04-26'),(202,'validUser_1745647246461','$2a$10$SXS23YAjrCfj5rD1DBXR9uhV7/Vaa1dNTj/X6eyOHXAhLb0yhqu/.','Test','User','avatar.png','test1745647246461@example.com','User','2025-04-26'),(203,'user_nodigit','$2a$10$74hB8bxndAV9uvildL2ZvulAMbsmt9n.im6fXWBjRV48qa8ZnkSO2','Alice','Jones','avatar.jpg','nodigit@test.com','User','2025-04-26'),(204,'validUser_1745647246653','$2a$10$/T4mu2N.rQiKvrTXFO6xpeR/zXSNdg0SMhecdkOzRz07fe/XhQIvy','Test','User','avatar.png','test1745647246653@example.com','User','2025-04-26'),(205,'user_nospecial','$2a$10$AVuj1ZtZWTin/vFVDjbVuej7o2hmk0rSWP0pLOGet8deG5ZqL1GI.','Charlie','Brown','avatar.jpg','nospecial@test.com','User','2025-04-26'),(206,'validUser_1745647381899','$2a$10$CAaYETFByk7Z/APigv1pbuiDLrDljBSoXuGE0mcWtnDOFL1zMTQJ.','Test','User','avatar.png','test1745647381903@example.com','User','2025-04-26'),(207,'validUser_1745647382783','$2a$10$3Nli6q.oVBKlMqo4KwT49eJ4s0vNk4bVRuRz61.61E5DJ/VPx9XWK','Test','User','avatar.png','test1745647382783@example.com','User','2025-04-26'),(208,'validUser_1745647382947','$2a$10$bl6fKD5D57r3sTpN1GQHE.tFq5Rqno3mUOcpjMauEEkAHxuXNOCJK','Test','User','avatar.png','test1745647382947@example.com','User','2025-04-26'),(209,'validUser_1745647383074','$2a$10$7Fu7SWbfxJ09cqJbNv04Wey2343Yc2dG9Po1fLIUQdcMm9031shS.','Test','User','avatar.png','test1745647383074@example.com','User','2025-04-26'),(210,'validUser_1745647383203','$2a$10$l34jyyHZwVokJ3PO4lTw.OCSl6y1dokXcFn6j5AW1UDfsU4O5feKm','Test','User','avatar.png','test1745647383203@example.com','User','2025-04-26'),(211,'validUser_1745647383322','$2a$10$d72TiYR6bdMOo2HiShoc0.H42CzKX1Z9/DgvrXdURtnHC5bJ2IKt2','Test','User','avatar.png','test1745647383322@example.com','User','2025-04-26'),(212,'validUser_1745647383442','$2a$10$NqfsWSNCI.ZDZx78/DYDjO4/SdOspJjgpLkT1O3bGCTbkPpz6zc4O','Test','User','avatar.png','test1745647383442@example.com','User','2025-04-26'),(213,'validUser_1745647383563','$2a$10$322zthcZzpHQp3VMFwuh4.VavGqR.Yx0DkVtTQB7CRIsZKSkV15AK','Test','User','avatar.png','test1745647383563@example.com','User','2025-04-26'),(214,'validUser_1745647383663','$2a$10$dUjgTxPnIEh2kJlXwqK5cum0cGezjYGQ6J4Iq6fyi4WfUbQsUCrOe','Test','User','avatar.png','test1745647383663@example.com','User','2025-04-26'),(215,'validUser_1745647383847','$2a$10$DoS8RIxleSRSCwxtuBxptu4TUg6KIGcJhGzWNZqrtvJM3m5lU4Akq','Test','User','avatar.png','test1745647383847@example.com','User','2025-04-26'),(217,'validUser_1745647384042','$2a$10$MIgz6BlpTzcjaKgm7GclAeFc5ob4oJE5wGKbfyW6oybtQJzURxiTa','Test','User','avatar.png','test1745647384042@example.com','User','2025-04-26'),(218,'validUser_1745647384240','$2a$10$UHlVhZzOQ/QsSwo458w2iuuOw644DhZRdJcS7RHDlbYXPj0OJm81C','Test','User','avatar.png','test1745647384240@example.com','User','2025-04-26'),(219,'validUser_1745647384433','$2a$10$q3MTd.tyXXsxgz7j6dUg/uay8qft059QfV3NQMNxxa5QY9PNU.42m','Test','User','avatar.png','test1745647384433@example.com','User','2025-04-26'),(221,'validUser_1745647384609','$2a$10$7vyZ7XydGz5NdJBKIPgVB.Qsjt9mUrtDN/nEgmyN1/l30my3wZfN2','Test','User','avatar.png','test1745647384609@example.com','User','2025-04-26'),(222,'validUser_1745647384715','$2a$10$I0i0o.ho7xepg6m.PKIwpOkSon16wyMvtV7sOf/yVAUsZxOje1Joy','Test','User','avatar.png','test1745647384715@example.com','User','2025-04-26'),(224,'validUser_1745647384913','$2a$10$zPvcYhpQf2jBFLmR/wFnJemYF.imr3p7yHFpLdqJDNVrhvEIEPsXC','Test','User','avatar.png','test1745647384913@example.com','User','2025-04-26'),(226,'validUser_1745647385107','$2a$10$upXsTzjZ4mXGNZ06Ph4zTulrd4f2B7X/iLeA9MLRuQBR4Q5S2tNRm','Test','User','avatar.png','test1745647385107@example.com','User','2025-04-26'),(228,'validUser_1745647385286','$2a$10$GUod.AYFjp5sia.TejVLIOfIlgH3nZ5GrBlEO54hjxc1kxlDVpobu','Test','User','avatar.png','test1745647385286@example.com','User','2025-04-26'),(230,'validUser_1745647385474','$2a$10$NupDu3k830YdQnrHM6awuOsRgSlS.um/5d306I0h1pcTX0frFb1T6','Test','User','avatar.png','test1745647385474@example.com','User','2025-04-26'),(232,'validUser_1745647385650','$2a$10$Cok5cr68ayvSZPo18fvHbuxc2U8/R/GnGs7F6O0eoN/N620zHprkq','Test','User','avatar.png','test1745647385650@example.com','User','2025-04-26'),(234,'validUser_1745647385828','$2a$10$fVK8nSwbyump5hM1eEnfCertTidOlB3VVTXBEDH./31YrMS.IcvOW','Test','User','avatar.png','test1745647385828@example.com','User','2025-04-26'),(236,'validUser_1745647912986','$2a$10$ffHSSI4YWzIBB0OSmFx11Oqed3R.zzRNwhyFqrLnN9UUSjZtzIjSy','Test','User','avatar.png','test1745647912988@example.com','User','2025-04-26'),(237,'validUser_1745647913801','$2a$10$Ee1XOVM/yFtbo3xU4oZAgebFOW5XFrbiUqJCiU6JT/xjavc4arXBW','Test','User','avatar.png','test1745647913801@example.com','User','2025-04-26'),(238,'validUser_1745647913933','$2a$10$5UrV89iGo9rZQl4AMSWNfuwqxMPPnbueQX2tUs2n7WgzvJ84FJuea','Test','User','avatar.png','test1745647913933@example.com','User','2025-04-26'),(239,'validUser_1745647914059','$2a$10$/CpYknOOjSiqANE4SAEUouYqOVjNqRvb3O3OK.kvzKOWnK46f7bfa','Test','User','avatar.png','test1745647914059@example.com','User','2025-04-26'),(240,'validUser_1745647914179','$2a$10$s2ZvDG0Mn5hjbrq5SQ8UM.OieLSvAjXHk1VMgQm.f5gM3y5YlnNXW','Test','User','avatar.png','test1745647914179@example.com','User','2025-04-26'),(241,'validUser_1745647914302','$2a$10$oJmfQbLgR8u2cCkZsLitbOPjriVwDTMj/qNJfPQv3FZDRRq2JxSmq','Test','User','avatar.png','test1745647914302@example.com','User','2025-04-26'),(242,'validUser_1745647914415','$2a$10$Q3pkYG9q24ZY99Yy86zkj.IFnkTP2FFUmfYXga7iP.dAS0r7gccAu','Test','User','avatar.png','test1745647914415@example.com','User','2025-04-26'),(243,'validUser_1745647914534','$2a$10$tvrzByYKird0aRZNuBXOAuXMdbjI9N9l5iOqVxRpWrlXU5viAER2i','Test','User','avatar.png','test1745647914534@example.com','User','2025-04-26'),(244,'validUser_1745647914641','$2a$10$h3GDziWpG52F1S1u7/UCZuNvkXlJX5kHdZxAFMSXoxOcX0qzzAfD6','Test','User','avatar.png','test1745647914641@example.com','User','2025-04-26'),(245,'validUser_1745647914837','$2a$10$LRaXWaaiRspF3e8EmhoHwOWcvyP/dEW8xNqpWsRj8XJA2HdHatUHq','Test','User','avatar.png','test1745647914837@example.com','User','2025-04-26'),(247,'validUser_1745647915033','$2a$10$Xte/qWPoqf07U9BUA6s7I.qo5cnhcdjeMhViUO0JF5.3RoE5nVkLe','Test','User','avatar.png','test1745647915033@example.com','User','2025-04-26'),(248,'validUser_1745647915213','$2a$10$mUCvO3h4iFBUvaZGKCjoLel4vgbKFGNvbB63DLvS9cEuSh.psMsgq','Test','User','avatar.png','test1745647915213@example.com','User','2025-04-26'),(249,'validUser_1745647915381','$2a$10$alkPBqcmASDk2hw7rbCO/O56KAAFJ1u/.LRU5FAjf1V9WZ4rMAN26','Test','User','avatar.png','test1745647915381@example.com','User','2025-04-26'),(251,'validUser_1745647915579','$2a$10$wbk.YRWbBUbzhf7dsp1RZu2pL4QEAUwKLVIB7CX9y6hx5j1nnw57G','Test','User','avatar.png','test1745647915579@example.com','User','2025-04-26'),(252,'validUser_1745647915767','$2a$10$NtTEji2.EIIBYedKmpevaecuvcPazVk3XX7X6U2Rt7gsBbO1dy.F2','Test','User','avatar.png','test1745647915767@example.com','User','2025-04-26'),(254,'validUser_1745647915948','$2a$10$.jwD54fVio1r6G2npDms9uinZhn25FGPd/3dtBRhgpgTqkhjK0qym','Test','User','avatar.png','test1745647915948@example.com','User','2025-04-26'),(256,'validUser_1745647916132','$2a$10$dj56hr9xPlfPNIGCJQN8yejWi8/UAnebJPekDprz4bdQAZU4JnZVy','Test','User','avatar.png','test1745647916132@example.com','User','2025-04-26'),(258,'validUser_1745647916318','$2a$10$8.W15goozFLmnA.F8Pq0FOjz/8OXEAR26XeMyw2HBKin2fsUKww3e','Test','User','avatar.png','test1745647916318@example.com','User','2025-04-26'),(260,'validUser_1745647916499','$2a$10$OnNCxOdWS0LypetQlQdNoOTMEIvH/lvKJgAseaga1uiXvCILuOIxO','Test','User','avatar.png','test1745647916499@example.com','User','2025-04-26'),(262,'validUser_1745647916683','$2a$10$87eB2Wlb/au3T7CStj.6f.khVvfeGQZZkL2yPlB1DL2xSF4kN9PO2','Test','User','avatar.png','test1745647916683@example.com','User','2025-04-26'),(264,'validUser_1745647916863','$2a$10$hpu2bikN5O.YZvkmgFnHwee842SEEEHtRw4Fh6IdAfpFGS3aV3OLi','Test','User','avatar.png','test1745647916863@example.com','User','2025-04-26');
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
INSERT INTO `wallet` VALUES (1,0,'2025-04-02'),(2,10708400,'2025-04-02'),(3,0,'2025-04-03'),(4,0,'2025-04-10'),(5,0,'2025-04-11'),(6,0,'2025-04-11'),(7,0,'2025-04-11'),(8,0,'2025-04-11'),(9,0,'2025-04-11'),(10,0,'2025-04-11'),(12,0,'2025-04-11'),(13,0,'2025-04-11'),(14,0,'2025-04-11'),(15,0,'2025-04-11'),(16,0,'2025-04-11'),(17,0,'2025-04-11'),(18,0,'2025-04-11'),(20,0,'2025-04-11'),(22,0,'2025-04-11'),(24,0,'2025-04-11'),(25,0,'2025-04-11'),(27,0,'2025-04-11'),(28,0,'2025-04-11'),(29,0,'2025-04-11'),(31,0,'2025-04-11'),(33,0,'2025-04-11'),(35,0,'2025-04-14'),(36,0,'2025-04-14'),(37,0,'2025-04-14'),(38,0,'2025-04-14'),(39,0,'2025-04-14'),(40,0,'2025-04-14'),(41,0,'2025-04-14'),(42,0,'2025-04-14'),(43,0,'2025-04-14'),(44,0,'2025-04-14'),(45,0,'2025-04-14'),(46,0,'2025-04-14'),(47,0,'2025-04-14'),(48,0,'2025-04-14'),(49,0,'2025-04-14'),(50,0,'2025-04-14'),(51,0,'2025-04-17'),(52,0,'2025-04-17'),(53,0,'2025-04-17'),(54,0,'2025-04-17'),(55,0,'2025-04-17'),(56,0,'2025-04-17'),(57,0,'2025-04-17'),(58,0,'2025-04-17'),(59,0,'2025-04-17'),(60,0,'2025-04-17'),(61,0,'2025-04-17'),(62,0,'2025-04-17'),(63,0,'2025-04-17'),(64,0,'2025-04-17'),(65,0,'2025-04-17'),(66,0,'2025-04-17'),(67,0,'2025-04-17'),(68,0,'2025-04-17'),(69,0,'2025-04-17'),(70,0,'2025-04-17'),(71,0,'2025-04-17'),(72,0,'2025-04-17'),(73,0,'2025-04-17'),(74,0,'2025-04-17'),(75,0,'2025-04-17'),(76,0,'2025-04-17'),(77,0,'2025-04-17'),(78,0,'2025-04-17'),(79,0,'2025-04-23'),(81,0,'2025-04-23'),(82,0,'2025-04-23'),(84,0,'2025-04-23'),(85,0,'2025-04-23'),(86,0,'2025-04-23'),(88,0,'2025-04-23'),(90,0,'2025-04-23'),(92,0,'2025-04-23'),(93,0,'2025-04-23'),(94,0,'2025-04-23'),(95,0,'2025-04-23'),(96,0,'2025-04-23'),(97,0,'2025-04-23'),(98,0,'2025-04-23'),(99,0,'2025-04-23'),(100,0,'2025-04-23'),(101,0,'2025-04-23'),(102,0,'2025-04-23'),(103,0,'2025-04-23'),(104,0,'2025-04-23'),(105,0,'2025-04-23'),(106,0,'2025-04-23'),(107,0,'2025-04-23'),(108,0,'2025-04-23'),(109,0,'2025-04-23'),(110,0,'2025-04-24'),(111,0,'2025-04-24'),(112,0,'2025-04-24'),(113,0,'2025-04-24'),(114,0,'2025-04-24'),(115,0,'2025-04-24'),(116,0,'2025-04-24'),(117,0,'2025-04-24'),(118,0,'2025-04-24'),(119,0,'2025-04-24'),(121,0,'2025-04-24'),(122,0,'2025-04-24'),(124,0,'2025-04-24'),(125,0,'2025-04-24'),(126,0,'2025-04-24'),(128,0,'2025-04-24'),(130,0,'2025-04-24'),(132,0,'2025-04-24'),(133,0,'2025-04-24'),(134,0,'2025-04-24'),(135,0,'2025-04-24'),(136,0,'2025-04-24'),(137,0,'2025-04-24'),(138,0,'2025-04-24'),(139,0,'2025-04-24'),(140,0,'2025-04-24'),(141,0,'2025-04-24'),(143,0,'2025-04-24'),(144,0,'2025-04-24'),(146,0,'2025-04-24'),(147,0,'2025-04-24'),(148,0,'2025-04-24'),(150,0,'2025-04-24'),(152,0,'2025-04-24'),(154,0,'2025-04-24'),(155,0,'2025-04-24'),(156,0,'2025-04-24'),(157,0,'2025-04-24'),(158,0,'2025-04-24'),(159,0,'2025-04-24'),(160,0,'2025-04-24'),(161,0,'2025-04-24'),(162,0,'2025-04-24'),(163,0,'2025-04-24'),(165,0,'2025-04-24'),(166,0,'2025-04-24'),(168,0,'2025-04-24'),(169,0,'2025-04-24'),(170,0,'2025-04-24'),(172,0,'2025-04-24'),(174,0,'2025-04-24'),(176,0,'2025-04-26'),(177,0,'2025-04-26'),(178,0,'2025-04-26'),(179,0,'2025-04-26'),(180,0,'2025-04-26'),(181,0,'2025-04-26'),(182,0,'2025-04-26'),(183,0,'2025-04-26'),(184,0,'2025-04-26'),(185,0,'2025-04-26'),(187,0,'2025-04-26'),(188,0,'2025-04-26'),(189,0,'2025-04-26'),(191,0,'2025-04-26'),(192,0,'2025-04-26'),(194,0,'2025-04-26'),(196,0,'2025-04-26'),(198,0,'2025-04-26'),(199,0,'2025-04-26'),(200,0,'2025-04-26'),(201,0,'2025-04-26'),(202,0,'2025-04-26'),(203,0,'2025-04-26'),(204,0,'2025-04-26'),(205,0,'2025-04-26'),(206,0,'2025-04-26'),(207,0,'2025-04-26'),(208,0,'2025-04-26'),(209,0,'2025-04-26'),(210,0,'2025-04-26'),(211,0,'2025-04-26'),(212,0,'2025-04-26'),(213,0,'2025-04-26'),(214,0,'2025-04-26'),(215,0,'2025-04-26'),(217,0,'2025-04-26'),(218,0,'2025-04-26'),(219,0,'2025-04-26'),(221,0,'2025-04-26'),(222,0,'2025-04-26'),(224,0,'2025-04-26'),(226,0,'2025-04-26'),(228,0,'2025-04-26'),(230,0,'2025-04-26'),(232,0,'2025-04-26'),(234,0,'2025-04-26'),(236,0,'2025-04-26'),(237,0,'2025-04-26'),(238,0,'2025-04-26'),(239,0,'2025-04-26'),(240,0,'2025-04-26'),(241,0,'2025-04-26'),(242,0,'2025-04-26'),(243,0,'2025-04-26'),(244,0,'2025-04-26'),(245,0,'2025-04-26'),(247,0,'2025-04-26'),(248,0,'2025-04-26'),(249,0,'2025-04-26'),(251,0,'2025-04-26'),(252,0,'2025-04-26'),(254,0,'2025-04-26'),(256,0,'2025-04-26'),(258,0,'2025-04-26'),(260,0,'2025-04-26'),(262,0,'2025-04-26'),(264,0,'2025-04-26');
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

	-- Kiểm tra ngân sách tối thiểu
	IF p_target <= 100000 THEN 
		SET p_success = false;
        SET p_message = 'Lỗi: Ngân sách đang nhỏ hơn giới hạn ngân sách cho phép.';
        ROLLBACK;
    END IF;
    
    -- Kiểm tra ngân sách tối đa
	IF p_target >= 100000000 THEN 
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
    
    IF EXISTS (SELECT 1 FROM category WHERE category.name = p_name) THEN
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

-- Dump completed on 2025-04-26 19:50:10
