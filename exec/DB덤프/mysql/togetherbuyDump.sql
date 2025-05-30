-- MySQL dump 10.13  Distrib 9.2.0, for Linux (x86_64)
--
-- Host: localhost    Database: TogetherBuyMain
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_point`
--

DROP TABLE IF EXISTS `admin_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_point` (
  `point` bigint DEFAULT NULL,
  `point_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`point_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_point`
--

LOCK TABLES `admin_point` WRITE;
/*!40000 ALTER TABLE `admin_point` DISABLE KEYS */;
INSERT INTO `admin_point` VALUES (0,1);
/*!40000 ALTER TABLE `admin_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apply`
--

DROP TABLE IF EXISTS `apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `apply` (
  `applied_at` date NOT NULL,
  `amount` bigint DEFAULT NULL,
  `apply_id` bigint NOT NULL AUTO_INCREMENT,
  `cost` bigint DEFAULT NULL,
  `form_id` bigint NOT NULL,
  `group_buying_board_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  `status` enum('NON_RECEIPT','RECEIPT') NOT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `FKp2kewq1o0aq2w28byjnvtdlda` (`form_id`),
  KEY `FKs33qaa3exd2r4krc8c3no6wk7` (`group_buying_board_id`),
  KEY `FKd0c5b59aerf7c0fe6vbmdvwyh` (`member_id`),
  CONSTRAINT `FKd0c5b59aerf7c0fe6vbmdvwyh` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKp2kewq1o0aq2w28byjnvtdlda` FOREIGN KEY (`form_id`) REFERENCES `form` (`form_id`),
  CONSTRAINT `FKs33qaa3exd2r4krc8c3no6wk7` FOREIGN KEY (`group_buying_board_id`) REFERENCES `group_buying_board` (`group_buying_board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apply`
--

LOCK TABLES `apply` WRITE;
/*!40000 ALTER TABLE `apply` DISABLE KEYS */;
INSERT INTO `apply` VALUES ('2025-02-20',12,2,90000,2,1,3,'NON_RECEIPT'),('2025-02-20',2,25,29600,24,20,3,'NON_RECEIPT'),('2025-02-21',1,26,11000,25,22,3,'NON_RECEIPT');
/*!40000 ALTER TABLE `apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_image` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%95%EC%9C%A1%EA%B3%84%EB%9E%80.webp','정육/계란'),(2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9D%BC%EC%B1%84%EC%86%8C.png','과일/채소'),(3,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%EB%83%89%EC%9E%A5%EA%B0%84%ED%8E%B8%EC%8B%9D.png','냉장/냉동/간편식'),(4,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%EC%9D%8C%EB%A3%8C.png','커피/음료'),(5,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B0%84%EC%8B%9D%EB%B2%A0%EC%9D%B4%EC%BB%A4%EB%A6%AC.png','간식/베이커리');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `chat_room_id` bigint NOT NULL AUTO_INCREMENT,
  `creator_id` bigint DEFAULT NULL,
  `group_buying_board_id` bigint DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`chat_room_id`),
  UNIQUE KEY `UKl90djyu2cn1ful53ssgaqo9dm` (`group_buying_board_id`),
  UNIQUE KEY `UKryw80an7wvjwh21f1w0r8rc9p` (`order_id`),
  KEY `FK5banydql40qhxn5uus8fyhqk8` (`creator_id`),
  CONSTRAINT `FK1x4s0m78d1hfep6vp4wrl6pl8` FOREIGN KEY (`group_buying_board_id`) REFERENCES `group_buying_board` (`group_buying_board_id`),
  CONSTRAINT `FK501x1o526u1958xw9wr9f1o7` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK5banydql40qhxn5uus8fyhqk8` FOREIGN KEY (`creator_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (1,3,1,NULL),(18,3,20,NULL),(19,3,22,NULL);
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatroom_participants`
--

DROP TABLE IF EXISTS `chatroom_participants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatroom_participants` (
  `chat_room_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  PRIMARY KEY (`chat_room_id`,`member_id`),
  KEY `FKfxv7wpx8nesy0saii5y74kb1h` (`member_id`),
  CONSTRAINT `FK23pqj4qduuyeq1dseummdqeq8` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`chat_room_id`),
  CONSTRAINT `FKfxv7wpx8nesy0saii5y74kb1h` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatroom_participants`
--

LOCK TABLES `chatroom_participants` WRITE;
/*!40000 ALTER TABLE `chatroom_participants` DISABLE KEYS */;
INSERT INTO `chatroom_participants` VALUES (1,3),(18,3),(19,3),(19,6);
/*!40000 ALTER TABLE `chatroom_participants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `created_at` datetime(6) DEFAULT NULL,
  `free_board_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK116t94dbkxa5swvordvw6cui6` (`free_board_id`),
  KEY `FKmrrrpi513ssu63i2783jyiv9m` (`member_id`),
  CONSTRAINT `FK116t94dbkxa5swvordvw6cui6` FOREIGN KEY (`free_board_id`) REFERENCES `free_board` (`id`),
  CONSTRAINT `FKmrrrpi513ssu63i2783jyiv9m` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form`
--

DROP TABLE IF EXISTS `form`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form` (
  `current_amount` bigint DEFAULT NULL,
  `end_time` bigint DEFAULT NULL,
  `form_id` bigint NOT NULL AUTO_INCREMENT,
  `group_buying_board_id` bigint NOT NULL,
  `meeting_location_id` bigint DEFAULT NULL,
  `order_order_id` bigint DEFAULT NULL,
  `recipient_id` bigint DEFAULT NULL,
  `start_time` bigint DEFAULT NULL,
  `total_amount` bigint DEFAULT NULL,
  PRIMARY KEY (`form_id`),
  UNIQUE KEY `UK8dwda6h92u0vvn6f3yiltsfqi` (`group_buying_board_id`),
  UNIQUE KEY `UK5sa5n18oann9b4dm8bq496t81` (`meeting_location_id`),
  UNIQUE KEY `UKhho78i1vsgppr7ftckdm04faj` (`order_order_id`),
  UNIQUE KEY `UKpr4vi2ul0vyfodpdipikc5qa1` (`recipient_id`),
  CONSTRAINT `FK9547sed0fcfdfgpgrr6dl8tda` FOREIGN KEY (`group_buying_board_id`) REFERENCES `group_buying_board` (`group_buying_board_id`),
  CONSTRAINT `FKbn535hgduh2t9hmch15n73kc0` FOREIGN KEY (`recipient_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FKm0lpr4ys4b2al8huj50yl8rp7` FOREIGN KEY (`meeting_location_id`) REFERENCES `meeting_location` (`meeting_location_id`),
  CONSTRAINT `FKr15cowxuc4fckb0tsri8vcr0k` FOREIGN KEY (`order_order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form`
--

LOCK TABLES `form` WRITE;
/*!40000 ALTER TABLE `form` DISABLE KEYS */;
INSERT INTO `form` VALUES (12,15,2,1,2,NULL,NULL,13,14),(2,18,24,20,24,NULL,NULL,15,13),(1,15,25,22,25,NULL,NULL,11,12);
/*!40000 ALTER TABLE `form` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_days`
--

DROP TABLE IF EXISTS `form_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_days` (
  `form_id` bigint NOT NULL,
  `day_of_week` enum('FRIDAY','MONDAY','SATURDAY','SUNDAY','THURSDAY','TUESDAY','WEDNESDAY') DEFAULT NULL,
  KEY `FKsrob5758ay5j1lruynbv65bgt` (`form_id`),
  CONSTRAINT `FKsrob5758ay5j1lruynbv65bgt` FOREIGN KEY (`form_id`) REFERENCES `form` (`form_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_days`
--

LOCK TABLES `form_days` WRITE;
/*!40000 ALTER TABLE `form_days` DISABLE KEYS */;
INSERT INTO `form_days` VALUES (2,'TUESDAY'),(2,'MONDAY'),(24,'WEDNESDAY'),(24,'TUESDAY'),(25,'MONDAY'),(25,'WEDNESDAY'),(25,'SUNDAY');
/*!40000 ALTER TABLE `form_days` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `free_board`
--

DROP TABLE IF EXISTS `free_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `free_board` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `likes` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `views` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqnums9o3fgd0y3i15as6daxxd` (`member_id`),
  KEY `FKfuf5aaxxh9qhmqnex0w1usq1u` (`product_id`),
  CONSTRAINT `FKfuf5aaxxh9qhmqnex0w1usq1u` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKqnums9o3fgd0y3i15as6daxxd` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `free_board`
--

LOCK TABLES `free_board` WRITE;
/*!40000 ALTER TABLE `free_board` DISABLE KEYS */;
/*!40000 ALTER TABLE `free_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `free_board_image`
--

DROP TABLE IF EXISTS `free_board_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `free_board_image` (
  `free_board_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs82ds56t80r2tkogt8c3d6t8r` (`free_board_id`),
  CONSTRAINT `FKs82ds56t80r2tkogt8c3d6t8r` FOREIGN KEY (`free_board_id`) REFERENCES `free_board` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `free_board_image`
--

LOCK TABLES `free_board_image` WRITE;
/*!40000 ALTER TABLE `free_board_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `free_board_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `free_board_like`
--

DROP TABLE IF EXISTS `free_board_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `free_board_like` (
  `created_at` timestamp NOT NULL,
  `free_board_id` bigint DEFAULT NULL,
  `free_board_like_id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`free_board_like_id`),
  KEY `FK5pf5qajxg5wewqf4i1vcsexxl` (`free_board_id`),
  KEY `FK7t2ru01h4yrrqtefrf21fadbb` (`member_id`),
  CONSTRAINT `FK5pf5qajxg5wewqf4i1vcsexxl` FOREIGN KEY (`free_board_id`) REFERENCES `free_board` (`id`),
  CONSTRAINT `FK7t2ru01h4yrrqtefrf21fadbb` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `free_board_like`
--

LOCK TABLES `free_board_like` WRITE;
/*!40000 ALTER TABLE `free_board_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `free_board_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buying_board`
--

DROP TABLE IF EXISTS `group_buying_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buying_board` (
  `chat_room_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `creator_id` bigint NOT NULL,
  `group_buying_board_id` bigint NOT NULL AUTO_INCREMENT,
  `likes` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `views` bigint DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_buying_board_id`),
  UNIQUE KEY `UKtpvhnvw9um0m6a6txqb4y38hg` (`chat_room_id`),
  KEY `FKmq714hydqwtsmpr8h8kf7u9pm` (`creator_id`),
  KEY `FKaar15qh11uvquybmvsihjccud` (`product_id`),
  CONSTRAINT `FKaar15qh11uvquybmvsihjccud` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKj4r06f1kh44ptnsvhhfll9ot` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_room` (`chat_room_id`),
  CONSTRAINT `FKmq714hydqwtsmpr8h8kf7u9pm` FOREIGN KEY (`creator_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buying_board`
--

LOCK TABLES `group_buying_board` WRITE;
/*!40000 ALTER TABLE `group_buying_board` DISABLE KEYS */;
INSERT INTO `group_buying_board` VALUES (NULL,'2025-02-20 05:43:32.140879',3,1,0,8,'2025-02-20 06:33:35.562712',0,'저도 좋아하긴 하는데 한달동안 먹을 생각하니 눈앞이 깜깜하네요 \n\n같이 드실분 연락 바랍니다 많관부','닭가슴살 좋아하시는 분들 많나요?'),(18,'2025-02-20 23:35:57.804579',3,20,0,12,'2025-02-20 23:36:20.149295',0,'즘 훈제 오리가 대세라던데..','오리 같이 드실분 있으실까요'),(NULL,'2025-02-21 01:08:54.688123',6,21,0,3,'2025-02-21 01:08:54.688149',0,'한우가 먹고 싶어요ㅠ','소고기 사실 분 구해요'),(19,'2025-02-21 01:14:44.537248',3,22,0,13,'2025-02-21 01:15:09.844758',0,'많은 관심 부탁드립니다.','사과가 맛있습니다');
/*!40000 ALTER TABLE `group_buying_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buying_board_image`
--

DROP TABLE IF EXISTS `group_buying_board_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buying_board_image` (
  `group_buying_board_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj9lhg33kvhfqc9dx62jobpggo` (`group_buying_board_id`),
  CONSTRAINT `FKj9lhg33kvhfqc9dx62jobpggo` FOREIGN KEY (`group_buying_board_id`) REFERENCES `group_buying_board` (`group_buying_board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buying_board_image`
--

LOCK TABLES `group_buying_board_image` WRITE;
/*!40000 ALTER TABLE `group_buying_board_image` DISABLE KEYS */;
INSERT INTO `group_buying_board_image` VALUES (21,5,'image/jpeg','36e4a5b9-1bbb-4cd9-9789-8f1936692a93.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/6_83210b76-2920-4796-82cb-163bc3829c21_%EC%86%8C%EA%B3%A0%EA%B8%B0.jpg'),(21,6,'image/jpeg','36e4a5b9-1bbb-4cd9-9789-8f1936692a93.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/6_83210b76-2920-4796-82cb-163bc3829c21_%EC%86%8C%EA%B3%A0%EA%B8%B0.jpg');
/*!40000 ALTER TABLE `group_buying_board_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_buying_board_like`
--

DROP TABLE IF EXISTS `group_buying_board_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_buying_board_like` (
  `group_buying_board_id` bigint DEFAULT NULL,
  `group_buying_like_id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  PRIMARY KEY (`group_buying_like_id`),
  KEY `FKbh48vnh562y8cx9rv0d8eye4h` (`group_buying_board_id`),
  KEY `FKmbe3heftf445myhxg3w8t0xvf` (`member_id`),
  CONSTRAINT `FKbh48vnh562y8cx9rv0d8eye4h` FOREIGN KEY (`group_buying_board_id`) REFERENCES `group_buying_board` (`group_buying_board_id`),
  CONSTRAINT `FKmbe3heftf445myhxg3w8t0xvf` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_buying_board_like`
--

LOCK TABLES `group_buying_board_like` WRITE;
/*!40000 ALTER TABLE `group_buying_board_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_buying_board_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meeting_location`
--

DROP TABLE IF EXISTS `meeting_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meeting_location` (
  `meeting_location_id` bigint NOT NULL AUTO_INCREMENT,
  `eupmyeondong` varchar(255) DEFAULT NULL,
  `load_name` varchar(255) DEFAULT NULL,
  `load_number` varchar(255) DEFAULT NULL,
  `ri` varchar(255) DEFAULT NULL,
  `sido` varchar(255) DEFAULT NULL,
  `sigungu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`meeting_location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meeting_location`
--

LOCK TABLES `meeting_location` WRITE;
/*!40000 ALTER TABLE `meeting_location` DISABLE KEYS */;
INSERT INTO `meeting_location` VALUES (2,'진접읍','진벌로','85-2',NULL,'경기','남양주시'),(24,NULL,'역삼로1길','8',NULL,'서울','강남구'),(25,NULL,'역삼로1길','8',NULL,'서울','강남구');
/*!40000 ALTER TABLE `meeting_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `birth` date DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `provider` tinyint DEFAULT NULL,
  `member_id` bigint NOT NULL AUTO_INCREMENT,
  `member_point_id` bigint DEFAULT NULL,
  `profile_image_id` bigint DEFAULT NULL,
  `verification_code_expire_time` datetime(6) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `UKrew3ln543ayvpmq2lliondhag` (`member_point_id`),
  UNIQUE KEY `UK6cheof1rxqhjd4h5wfi308jo2` (`profile_image_id`),
  UNIQUE KEY `UKmbmcqelty0fbrvxp1q58dn57t` (`email`),
  CONSTRAINT `FKh70r94h2co6ggu0pjx3a7sfky` FOREIGN KEY (`profile_image_id`) REFERENCES `member_image` (`id`),
  CONSTRAINT `FKmcbb6i6ngtgp7kfr94ei2vc6q` FOREIGN KEY (`member_point_id`) REFERENCES `member_point` (`member_point_id`),
  CONSTRAINT `member_chk_1` CHECK ((`provider` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('1997-04-13',0,0,2,2,2,'2025-02-20 04:21:35.993425','25340104112322','국민은행','gyoo413@gmail.com','뀨뀨','109438582016733261572','010-3937-2662','184610'),('1999-11-11',1,0,3,3,3,NULL,'135724683579','기업은행','chorjgml01@gmail.com','주거니받거니','115593210315859722561','010-1357-2468',NULL),('1999-05-18',0,0,4,4,4,'2025-02-20 06:52:25.239092','9790282221324','기업은행','cks3680@gmail.com','Chan','102862146406091721056','010-1234-5678','352772'),(NULL,NULL,1,5,NULL,NULL,NULL,NULL,NULL,'vkvl6414@naver.com',NULL,'i-ya10QbRh8wEJGW_mqSJECHI_pG-390IfNz373Xjqo',NULL,NULL),('1998-11-04',0,1,6,5,5,NULL,'01063992768','카카오뱅크','ehfql6363@naver.com','동동열','X22oj7FN6sI74TEfym3oNmLfyG0NWXcQvfHm2UX06pw','010-6399-2768',NULL),('2011-01-02',0,0,7,6,6,NULL,'77200204123456','신한은행','abelorchestra@gmail.com','gunhee2','110785011800328362464','010-1324-3535',NULL),('1993-02-05',0,1,8,7,7,NULL,'11032023225','기업은행','skydh507@jr.naver.com','ssafyTop','kozpyl1vhshiC_WBjGVmvNbbUzMfo5S2L5eTjBtiI00','010-9732-2383',NULL),('1988-08-08',0,0,9,8,8,'2025-02-21 01:20:32.264151','1234567890','신한은행','kyeong8139@gmail.com','누구게22','103326160054522282397','010-1234-5678','574964'),('2000-04-02',0,0,10,9,9,'2025-02-21 01:27:27.116984','110123456789','신한은행','antiskj42@gmail.com','지은','103094424803972007134','010-1234-5678','179243');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_image`
--

DROP TABLE IF EXISTS `member_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `url` text,
  PRIMARY KEY (`id`),
  KEY `FKr9nbgi536kuu13spg7w3bbxow` (`member_id`),
  CONSTRAINT `FKr9nbgi536kuu13spg7w3bbxow` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_image`
--

LOCK TABLES `member_image` WRITE;
/*!40000 ALTER TABLE `member_image` DISABLE KEYS */;
INSERT INTO `member_image` VALUES (2,2,'image/jpeg','ㄱㅇㅇ.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/2_f193315f-6411-41e3-bdf1-5afd2c19942e_%E3%84%B1%E3%85%87%E3%85%87.jpg'),(3,3,'image/jpeg','토종닭.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/3_232df92f-2f2d-4554-ace0-3a251514e1ec_%ED%86%A0%EC%A2%85%EB%8B%AD.jpg'),(4,4,'image/jpeg','White-dog-puppy-standing-grass_2560x1440.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/4_97ad2deb-9893-4e4f-a6c7-7fa48e960a51_White-dog-puppy-standing-grass_2560x1440.jpg'),(5,6,'image/jpeg','8db66873-360c-4325-82bb-dd6ec2a27ba7.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/6_db054514-d018-4c47-bc20-eaadd3ed9871_8db66873-360c-4325-82bb-dd6ec2a27ba7.jpg'),(6,7,'image/png','MAIN_ICON.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/7_5af526c7-6d72-440b-b723-08a94e3de3ee_MAIN_ICON.png'),(7,8,'image/png','images (1).png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/8_9e6f6113-43d0-42b2-b52d-ad7a5b24d77c_images%20%281%29.png'),(8,9,'image/webp','DALL·E 2025-01-20 17.18.54 - The number 13 drawn in an orange, clean, and moderately styled hand-drawn aesthetic. The design is simple yet refined, with smooth and consistent line.webp','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/9_7303deaa-157a-4b8d-a7a2-6ec6dbcf8334_DALL%C2%B7E%202025-01-20%2017.18.54%20-%20The%20number%2013%20drawn%20in%20an%20orange%2C%20clean%2C%20and%20moderately%20styled%20hand-drawn%20aesthetic.%20The%20design%20is%20simple%20yet%20refined%2C%20with%20smooth%20and%20consistent%20line.webp'),(9,10,'image/png','지은.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/10_4d2cd405-3aba-4427-a9bc-b35fb48459bc_%EC%A7%80%EC%9D%80.png');
/*!40000 ALTER TABLE `member_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_location`
--

DROP TABLE IF EXISTS `member_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_location` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint DEFAULT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
  `eupmyeondong` varchar(255) DEFAULT NULL,
  `ri` varchar(255) DEFAULT NULL,
  `road_name` varchar(255) DEFAULT NULL,
  `road_number` varchar(255) DEFAULT NULL,
  `sido` varchar(255) DEFAULT NULL,
  `sigungu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo6mxytmnk546jt18pp1cnnd3y` (`member_id`),
  CONSTRAINT `FKo6mxytmnk546jt18pp1cnnd3y` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_location`
--

LOCK TABLES `member_location` WRITE;
/*!40000 ALTER TABLE `member_location` DISABLE KEYS */;
INSERT INTO `member_location` VALUES (2,2,NULL,'통진읍',NULL,'소서명로','3','경기','김포시'),(3,3,NULL,NULL,NULL,'관악로30길','47-16','서울','동작구'),(4,4,'(부영그린타운아파트 상가)',NULL,NULL,'번화1로84번길','7','경남','김해시'),(5,6,'(신창비바패밀리아파트)','진접읍',NULL,'진벌로','85-2','경기','남양주시'),(6,7,NULL,'황간면',NULL,'구교리길','38-6','충북','영동군'),(7,8,NULL,'진접읍',NULL,'진벌로','15-1','경기','남양주시'),(8,9,'(멀티캠퍼스)',NULL,NULL,'테헤란로','212','서울','강남구'),(9,10,'(송도 SK VIEW)',NULL,NULL,'랜드마크로','19','인천','연수구');
/*!40000 ALTER TABLE `member_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_point`
--

DROP TABLE IF EXISTS `member_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_point` (
  `balance` bigint DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `member_point_id` bigint NOT NULL AUTO_INCREMENT,
  `billing_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_point_id`),
  UNIQUE KEY `UK7paaq8v0cvq04nar6k2j007wk` (`member_id`),
  CONSTRAINT `FKknd8uwmufmy5w8uyreq783h3c` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_point`
--

LOCK TABLES `member_point` WRITE;
/*!40000 ALTER TABLE `member_point` DISABLE KEYS */;
INSERT INTO `member_point` VALUES (10000000000,2,2,NULL),(9999889600,3,3,NULL),(10000000000,4,4,NULL),(0,6,5,NULL),(0,7,6,NULL),(0,8,7,NULL),(0,9,8,NULL),(0,10,9,NULL);
/*!40000 ALTER TABLE `member_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2attribute`
--

DROP TABLE IF EXISTS `oauth2attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2attribute`
--

LOCK TABLES `oauth2attribute` WRITE;
/*!40000 ALTER TABLE `oauth2attribute` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_date` date DEFAULT NULL,
  `member_id` bigint DEFAULT NULL,
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `order_price` bigint DEFAULT NULL,
  `order_quantity` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `seller_id` bigint NOT NULL,
  `order_address` varchar(255) DEFAULT NULL,
  `way_bill_number` varchar(255) DEFAULT NULL,
  `delivery_status` enum('BEFORE','COMPLETE','DELIVERY') DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKpktxwhj3x9m4gth5ff6bkqgeb` (`member_id`),
  KEY `FK787ibr3guwp6xobrpbofnv7le` (`product_id`),
  KEY `FKiqoyghlcoagihjeufolwetin7` (`seller_id`),
  CONSTRAINT `FK787ibr3guwp6xobrpbofnv7le` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKiqoyghlcoagihjeufolwetin7` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`seller_id`),
  CONSTRAINT `FKpktxwhj3x9m4gth5ff6bkqgeb` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_point`
--

DROP TABLE IF EXISTS `payment_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_point` (
  `cost` bigint DEFAULT NULL,
  `point` bigint DEFAULT NULL,
  `point_id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_point`
--

LOCK TABLES `payment_point` WRITE;
/*!40000 ALTER TABLE `payment_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `point_charge_history`
--

DROP TABLE IF EXISTS `point_charge_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_charge_history` (
  `charge_status` tinyint DEFAULT NULL,
  `admin_point_id` bigint DEFAULT NULL,
  `charge_amount` bigint DEFAULT NULL,
  `charge_date_time` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_point_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5bjkb6gckdof9bjo0wtfw9vt6` (`admin_point_id`),
  KEY `FKlfimnptu6qmqfy64ne6f0rbms` (`member_point_id`),
  CONSTRAINT `FK5bjkb6gckdof9bjo0wtfw9vt6` FOREIGN KEY (`admin_point_id`) REFERENCES `admin_point` (`point_id`),
  CONSTRAINT `FKlfimnptu6qmqfy64ne6f0rbms` FOREIGN KEY (`member_point_id`) REFERENCES `member_point` (`member_point_id`),
  CONSTRAINT `point_charge_history_chk_1` CHECK ((`charge_status` between 0 and 1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `point_charge_history`
--

LOCK TABLES `point_charge_history` WRITE;
/*!40000 ALTER TABLE `point_charge_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `point_charge_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `discount_amount` bigint DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `price_per_unit` bigint DEFAULT NULL,
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `sale_price` bigint DEFAULT NULL,
  `seller_id` bigint DEFAULT NULL,
  `sub_category_id` bigint NOT NULL,
  `total` bigint DEFAULT NULL,
  `unit_amount` bigint DEFAULT NULL,
  `detail_image` varchar(255) DEFAULT NULL,
  `discount_unit` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `unit_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKesd6fy52tk7esoo2gcls4lfe3` (`seller_id`),
  KEY `FKd9gfnsjgfwjtaxl57udrbocsp` (`sub_category_id`),
  CONSTRAINT `FKd9gfnsjgfwjtaxl57udrbocsp` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_category` (`id`),
  CONSTRAINT `FKesd6fy52tk7esoo2gcls4lfe3` FOREIGN KEY (`seller_id`) REFERENCES `seller` (`seller_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (26000,325000,23000,3,299000,1,1,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/2_5759f5ac-6545-40a2-a7d0-03d4f0af01d9_%ED%95%9C%EC%9A%B0%EB%93%B1%EC%8B%AC.jpg',NULL,'한우등심','개'),(32500,364000,25500,4,331500,1,1,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/3_547baa0b-a984-4ab6-b97a-1f27fef02305_%ED%95%9C%EC%9A%B0%EC%B1%84%EB%81%9D.png',NULL,'한우채끝','개'),(18000,216000,16500,5,198000,1,2,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/4_6e4c8ac3-186b-40b7-8ab8-ebb0ce183de1_%EC%82%BC%EA%B2%B9%EC%82%B4.jpg',NULL,'삼겹살','개'),(12000,192000,15000,6,180000,1,2,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/5_14506a1a-1029-4f81-a0a1-af089e5608bf_%EB%AA%A9%EC%82%B4.png',NULL,'목살','개'),(11000,132000,11000,7,121000,1,3,11,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/6_8421418c-10ce-4b93-a3b7-8094a1756ca3_%ED%86%A0%EC%A2%85%EB%8B%AD.jpg',NULL,'토종닭','개'),(7000,112000,7500,8,105000,1,3,14,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/7_7a5d2658-f632-483f-9c1b-b0ad3b5be933_%EB%8B%AD%EA%B0%80%EC%8A%B4%EC%82%B4.jpg',NULL,'닭가슴살','개'),(6500,65000,4500,9,58500,1,4,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/8_f300055f-846b-4060-b257-27ad33482904_%EC%9C%A0%EC%A0%95%EB%9E%8010%EA%B5%AC.jpg',NULL,'유정란 10구','개'),(12000,144000,11000,10,132000,1,4,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/9_2b236ace-e17e-49f5-8e87-8e87de761750_%EC%9D%BC%EB%B0%98%EB%9E%8030%EA%B5%AC.jpg',NULL,'일반란30구','개'),(11000,154000,13000,11,143000,1,5,11,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/10_665f992c-b806-48b5-be10-256d3244e0b8_%EC%98%A4%EB%A6%AC%EA%B3%A0%EA%B8%B0%EC%8A%AC%EB%9D%BC%EC%9D%B4%EC%8A%A4.jpg',NULL,'오리고기 슬라이스','개'),(15600,208000,14800,12,192400,1,5,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/11_2da2eed9-97ea-4dc5-b9f9-ecc876b8f531_%ED%9B%88%EC%A0%9C%EC%98%A4%EB%A6%AC.jpg',NULL,'훈제 오리','개'),(12000,144000,11000,13,132000,1,6,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/12_33478192-79f5-473b-8e94-2a020666950e_%EC%A0%9C%EC%B2%A0%EC%82%AC%EA%B3%BC.jpg',NULL,'제철 사과','개'),(10400,117000,8200,14,106600,1,6,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/13_3ed52043-17cc-4eae-90d4-0d9cf2a20db6_%EC%A0%9C%EC%B2%A0%EA%B7%A4.jpg',NULL,'제철 귤','개'),(15600,195000,13800,15,179400,1,7,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/14_b49f18ec-df58-42ea-a55a-f125c7509303_%EA%B5%AD%EB%82%B4%EC%82%B0%EB%B0%B0.jpg','원','국내산 배','개'),(11700,143000,10100,16,131300,1,7,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/15_c356e5d2-6824-4c0d-b06b-03f17db02d59_%EA%B5%AD%EB%82%B4%EC%82%B0%ED%8F%AC%EB%8F%84.jpg',NULL,'국내산 포도','개'),(12000,156000,36000,17,144000,1,8,12,3,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/16_fe05b4f2-ede8-43f8-a003-a0878dc73476_%EC%88%98%EC%9E%85%EC%98%A4%EB%A0%8C%EC%A7%80.jpg',NULL,'수입 오렌지','개'),(28000,280000,21000,18,252000,1,8,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/17_ee41d215-47e6-4eb0-8f84-557288de08de_%EC%88%98%EC%9E%85%EC%B2%B4%EB%A6%AC.png',NULL,'수입 체리','개'),(6000,96000,15000,19,90000,1,9,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/18_c0918f93-c81f-4001-8408-2f0aee842bf8_%EA%B5%AD%EB%82%B4%EC%82%B0%EA%B0%90%EC%9E%90.jpg',NULL,'국내산 감자','개'),(6000,72000,11000,20,66000,1,9,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/19_e2263020-1b9d-4d26-83b8-13239776c5ec_%EA%B5%AD%EB%82%B4%EC%82%B0%EB%8B%B9%EA%B7%BC.jpg',NULL,'국내산 당근','개'),(6500,78000,5500,21,71500,1,10,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/21_1972234b-ceb4-4002-b975-995bde798a04_%EC%8C%88%EC%B1%84%EC%86%8C%EB%AA%A8%EC%9D%8C.jpg','원','쌈채소 모음','개'),(6000,60000,5400,22,54000,1,11,10,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/21_f7c303ee-1606-4d56-9bed-1386d945a237_%EC%A6%89%EC%84%9D%EC%B9%B4%EB%A0%88.jpg',NULL,'즉석 카레','개'),(9100,91000,12600,23,81900,1,11,13,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/22_c793686c-a0ce-4103-818f-f2442e71d098_%EC%A6%89%EC%84%9D%EA%B5%AD%EB%B0%A5.png',NULL,'즉석 국밥','개'),(10400,104000,7200,24,93600,1,12,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/23_cf55ac44-88ca-4e46-b9d6-8a10330d1f22_%EA%B3%A0%EA%B8%B0%EB%A7%8C%EB%91%90.png',NULL,'고기만두','개'),(11700,117000,8100,25,105300,1,12,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/24_7309c76d-4ac3-44a9-a8df-6590c9ba53cc_%ED%8A%80%EA%B9%80%EC%98%A4%EC%A7%95%EC%96%B4.jpg',NULL,'튀김 오징어','개'),(12000,144000,11000,26,132000,1,13,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/25_270682c4-f8da-46f0-879e-43999b255010_%EB%B6%88%EA%B3%A0%EA%B8%B0%ED%94%BC%EC%9E%90.jpg',NULL,'불고기 피자','개'),(10800,132000,10100,27,121200,1,13,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/26_834c001f-f5ad-4228-afd4-69d37c6ab5e2_%EC%B9%98%EC%A6%88%ED%94%BC%EC%9E%90.jpg',NULL,'치즈 피자','개'),(6500,65000,13500,28,58500,1,14,13,3,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/27_8a6f1f7e-b618-431e-b9d8-f0fa0b417f5f_%EC%B4%88%EC%BD%94%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.jpg',NULL,'초코 아이스크림','개'),(5200,62400,13200,29,57200,1,14,13,3,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/28_2e453498-825c-4f56-be77-7e85f7349c32_%EB%B0%94%EB%8B%90%EB%9D%BC%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.jpg',NULL,'바닐라 아이스크림','개'),(6000,90000,7000,30,84000,1,15,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/29_4f7912f8-9bb6-4ce6-932d-1e7c11fe164c_%EB%B6%88%EA%B3%A0%EA%B8%B0%EB%8F%84%EC%8B%9C%EB%9D%BD.png',NULL,'불고기 도시락','개'),(7200,93600,7200,31,86400,1,15,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/30_9008cf8d-2843-4cd9-929e-ee3597fc9825_%EC%B9%98%ED%82%A8%EB%8F%84%EC%8B%9C%EB%9D%BD.jpg',NULL,'치킨 도시락','개'),(6500,65000,13500,32,58500,1,16,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/31_c5d7ef61-94b1-4e31-b529-f6ded9136e42_%EC%95%84%EB%A9%94%EB%A6%AC%EC%B9%B4%EB%85%B8.jpg',NULL,'아메리카노','개'),(6500,71500,5000,33,65000,1,16,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/32_5fbc726f-a886-4b3e-b9c7-971217fc8078_%EC%B9%B4%ED%8E%98%EB%9D%BC%EB%96%BC.jpg',NULL,'카페라떼','개'),(1200,12000,1800,34,10800,1,17,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/33_9a69609b-4e45-4733-ac6d-fedff0c21072_%EC%83%9D%EC%88%98.jpg',NULL,'생수','개'),(1300,15600,1100,35,14300,1,17,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/34_f272fd3d-ab1d-417f-98c1-3b009959b20b_%ED%83%84%EC%82%B0%EC%88%98.jpg',NULL,'탄산수','개'),(2400,18000,2600,36,15600,1,18,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/35_827f4ab2-8e03-47dd-a07d-6b2f43e1ff7e_%EC%98%A4%EB%A0%8C%EC%A7%80%EC%A3%BC%EC%8A%A4.jpg',NULL,'오렌지 주스','개'),(2600,19500,1300,37,16900,1,18,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/36_a613f32d-de84-4bd4-bad2-76cc9fed72e2_%EC%82%AC%EA%B3%BC%EC%A3%BC%EC%8A%A4.jpg',NULL,'사과 주스','개'),(1800,14400,3150,38,12600,1,19,12,3,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/37_3aa431d2-9eb8-4be6-b73b-e8c5d2079cab_%EC%BD%9C%EB%9D%BC.jpg',NULL,'콜라','개'),(1950,15600,1050,39,13650,1,19,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/38_e84b92b3-b458-4a02-99f5-41ab6d12bdb2_%EC%82%AC%EC%9D%B4%EB%8B%A4.jpg',NULL,'사이다','개'),(3900,39000,2700,40,35100,1,20,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/39_34693cb4-4d03-4167-b478-61fcb987c5b1_%EB%85%B9%EC%B0%A8.jpg',NULL,'녹차','개'),(4550,45500,3150,41,40950,1,20,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/41_b0a9aca8-8198-4864-a331-cf55995a00e5_%ED%99%8D%EC%B0%A8.jpg',NULL,'홍차','개'),(2400,24000,5400,42,21600,1,21,12,3,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/42_d2660acc-fe14-49b8-a343-bd7c5fca16cc_%EC%8B%9D%EB%B9%B5.jpg',NULL,'식빵','개'),(2600,32500,2300,43,29900,1,21,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/43_6423e9d5-7828-471c-988b-f2d2295ff462_%EB%B0%94%EA%B2%8C%ED%8A%B8.jpg',NULL,'바게트','개'),(1200,18000,2800,44,16800,1,22,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/44_40d5f330-a20b-4c41-9363-75c25dcbc5ee_%EC%83%88%EC%9A%B0%EA%B9%A1.jpg',NULL,'새우깡','개'),(1300,23400,1700,45,22100,1,22,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/45_e52054e9-a8e0-46a0-bc73-653fa9bdb4d4_%ED%8F%AC%EC%B9%B4%EC%B9%A9.jpg',NULL,'포카칩','개'),(2400,26400,4000,46,24000,1,23,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/46_d297ba87-8c37-420b-beeb-d6971176dd6e_%EB%8B%A4%ED%81%AC%EC%B4%88%EC%BD%9C%EB%A6%BF.jpg',NULL,'다크 초콜릿','개'),(2600,26000,1800,47,23400,1,23,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/47_676e03b0-af91-40ec-9b99-a3c96a6d5742_%EB%B0%80%ED%81%AC%EC%B4%88%EC%BD%9C%EB%A6%BF.jpg',NULL,'밀크 초콜릿','개'),(12000,144000,11000,48,132000,1,24,12,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/48_c252eecf-7db0-434c-8849-4ecd36cfc16c_%EC%B4%88%EC%BD%94%EC%BC%80%EC%9D%B4%ED%81%AC.jpg',NULL,'초코 케이크','개'),(19500,195000,13500,49,175500,1,24,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/49_5a07e428-bf1e-4f64-900d-ce2c54911eb9_%EC%83%9D%ED%81%AC%EB%A6%BC%EC%BC%80%EC%9D%B4%ED%81%AC.jpg',NULL,'생크림 케이크','개'),(6000,60000,9000,50,54000,1,25,12,2,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/50_fe9a3fdf-b9ca-4c67-8b3a-01761ff01447_%EB%B0%B1%EC%84%A4%EA%B8%B0%EB%96%A1.jpg',NULL,'백설기 떡','개'),(7800,78000,5400,51,70200,1,25,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/51_398fd041-9dd1-4746-b30d-78cd30aedd73_%EC%86%A1%ED%8E%B8%EB%96%A1.jpg',NULL,'송편 떡','개'),(3900,52000,3700,52,48100,1,10,13,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/52_32ad8670-a7ff-4fe2-86fe-b352de862bf7_%EA%B9%BB%EC%9E%8E.jpg',NULL,'깻잎','개'),(1000,50000,4900,57,49000,3,2,10,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/57_f692725d-13e8-4d8c-bd75-5384e0e2a1b0_777b12a2-50f5-42c2-a357-07629875f395.jpg',NULL,'대패 삼겹살 400g, 10팩','개'),(15000,100000,8500,58,85000,1,2,10,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/58_2a55320c-732c-43da-9a8c-a60449dec5cd_MAIN_ICON.png',NULL,'돼지고기','개'),(50,1000,100,59,500,5,1,5,1,'https://togetherbuy.s3.ap-northeast-2.amazonaws.com/59_d9e205e5-72b7-4f90-b104-2ccc4778d90a_logo-512.png',NULL,'계란','개');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL,
  `size` bigint NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (3,3,41255,'image/jpeg','한우등심.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/2_ed441949-1461-42a2-bb05-755d3bea484e_%ED%95%9C%EC%9A%B0%EB%93%B1%EC%8B%AC.jpg'),(4,4,196790,'image/png','한우채끝.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/3_efc32937-6551-4779-86f1-1707e9a41e68_%ED%95%9C%EC%9A%B0%EC%B1%84%EB%81%9D.png'),(5,5,76782,'image/jpeg','삼겹살.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/4_f2391ed6-5267-4aff-9477-6cec9c920a40_%EC%82%BC%EA%B2%B9%EC%82%B4.jpg'),(6,6,201274,'image/png','목살.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/5_55d666cc-4f1d-4371-ba27-7133e99ff37d_%EB%AA%A9%EC%82%B4.png'),(7,7,35679,'image/jpeg','토종닭.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/6_b3de890c-ac83-4a23-a1d6-f3592952d731_%ED%86%A0%EC%A2%85%EB%8B%AD.jpg'),(8,8,18534,'image/jpeg','닭가슴살.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/7_073c1edd-d635-4258-914c-05b093133b52_%EB%8B%AD%EA%B0%80%EC%8A%B4%EC%82%B4.jpg'),(9,9,30312,'image/jpeg','유정란10구.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/8_a8f6cb58-0867-46fc-add7-950fc4f56b4c_%EC%9C%A0%EC%A0%95%EB%9E%8010%EA%B5%AC.jpg'),(10,10,32381,'image/jpeg','일반란30구.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/9_66f8f8e1-5847-4c2a-8cfa-cfa6b8c6fcfc_%EC%9D%BC%EB%B0%98%EB%9E%8030%EA%B5%AC.jpg'),(11,11,56041,'image/jpeg','오리고기슬라이스.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/10_3fc30de2-4c05-4d4e-aa8a-fb6e01d1ee4a_%EC%98%A4%EB%A6%AC%EA%B3%A0%EA%B8%B0%EC%8A%AC%EB%9D%BC%EC%9D%B4%EC%8A%A4.jpg'),(12,12,34497,'image/jpeg','훈제오리.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/11_6b28ca17-ccec-4631-a901-a858dd5fcb60_%ED%9B%88%EC%A0%9C%EC%98%A4%EB%A6%AC.jpg'),(13,13,30059,'image/jpeg','제철사과.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/12_c7272c5a-013e-4aae-b42f-7f681704669e_%EC%A0%9C%EC%B2%A0%EC%82%AC%EA%B3%BC.jpg'),(14,14,27889,'image/jpeg','제철귤.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/13_c69ce9cb-6616-4d16-8787-86fdcf91b39d_%EC%A0%9C%EC%B2%A0%EA%B7%A4.jpg'),(15,15,29085,'image/jpeg','국내산배.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/14_91b53827-3cfa-48ed-8ae1-6d51cb3a520c_%EA%B5%AD%EB%82%B4%EC%82%B0%EB%B0%B0.jpg'),(16,16,30227,'image/jpeg','국내산포도.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/15_9b51114d-b37a-47d4-a9b5-36e7f5a47e47_%EA%B5%AD%EB%82%B4%EC%82%B0%ED%8F%AC%EB%8F%84.jpg'),(17,17,24013,'image/jpeg','수입오렌지.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/16_e47d5101-ea35-4dc1-9fca-8f73cbf724c6_%EC%88%98%EC%9E%85%EC%98%A4%EB%A0%8C%EC%A7%80.jpg'),(18,18,186993,'image/png','수입체리.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/17_ade5f966-3587-44b1-a2ac-989f00ca1e48_%EC%88%98%EC%9E%85%EC%B2%B4%EB%A6%AC.png'),(19,19,30889,'image/jpeg','국내산감자.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/18_1011f400-659c-4178-989c-f0e804a18510_%EA%B5%AD%EB%82%B4%EC%82%B0%EA%B0%90%EC%9E%90.jpg'),(20,20,55027,'image/jpeg','국내산당근.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/19_1ca27e98-5ccb-4496-90a4-31fd97c661de_%EA%B5%AD%EB%82%B4%EC%82%B0%EB%8B%B9%EA%B7%BC.jpg'),(22,22,27415,'image/jpeg','즉석카레.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/21_9129c218-84b0-46e1-bb24-d4858815aa3c_%EC%A6%89%EC%84%9D%EC%B9%B4%EB%A0%88.jpg'),(23,23,204644,'image/png','즉석국밥.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/22_97a42584-937c-4c05-aac2-9053da2c263e_%EC%A6%89%EC%84%9D%EA%B5%AD%EB%B0%A5.png'),(24,24,167210,'image/png','고기만두.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/23_1e278f5c-6a57-4791-bac9-3e2f7457c05b_%EA%B3%A0%EA%B8%B0%EB%A7%8C%EB%91%90.png'),(25,25,48446,'image/jpeg','튀김오징어.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/24_9b915eaf-de9b-4871-9b81-7b7a778254cc_%ED%8A%80%EA%B9%80%EC%98%A4%EC%A7%95%EC%96%B4.jpg'),(26,26,90385,'image/jpeg','불고기피자.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/25_8994ac78-1cf6-48c6-b444-d48bc280a733_%EB%B6%88%EA%B3%A0%EA%B8%B0%ED%94%BC%EC%9E%90.jpg'),(27,27,49448,'image/jpeg','치즈피자.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/26_300abba8-225c-4dde-946e-94872705c8c2_%EC%B9%98%EC%A6%88%ED%94%BC%EC%9E%90.jpg'),(28,28,33679,'image/jpeg','초코아이스크림.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/27_4eb32917-1ecf-47de-bcf0-28b77596309e_%EC%B4%88%EC%BD%94%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.jpg'),(29,29,18264,'image/jpeg','바닐라아이스크림.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/28_15a92b50-00f5-4acd-95b3-09b431ea9daf_%EB%B0%94%EB%8B%90%EB%9D%BC%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.jpg'),(30,30,199327,'image/png','불고기도시락.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/29_de05e63b-a78a-472b-8dc8-f68082449bed_%EB%B6%88%EA%B3%A0%EA%B8%B0%EB%8F%84%EC%8B%9C%EB%9D%BD.png'),(31,31,42254,'image/jpeg','치킨도시락.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/30_b2a1b228-4119-4258-87ff-d0bf8cfde31d_%EC%B9%98%ED%82%A8%EB%8F%84%EC%8B%9C%EB%9D%BD.jpg'),(32,32,23021,'image/jpeg','아메리카노.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/31_e3357ed3-748a-4beb-8e81-db5cd6b7ee6c_%EC%95%84%EB%A9%94%EB%A6%AC%EC%B9%B4%EB%85%B8.jpg'),(33,33,28232,'image/jpeg','카페라떼.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/32_c3f0a5a1-184b-46fc-a6d9-936e07b201e5_%EC%B9%B4%ED%8E%98%EB%9D%BC%EB%96%BC.jpg'),(34,34,21142,'image/jpeg','생수.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/33_84ea2148-ef46-4f06-8632-e39ffcf87360_%EC%83%9D%EC%88%98.jpg'),(35,35,14647,'image/jpeg','탄산수.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/34_144618c2-a275-4591-a8b4-53b429773a3c_%ED%83%84%EC%82%B0%EC%88%98.jpg'),(36,36,13858,'image/jpeg','오렌지주스.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/35_dd2d006b-44ff-4b3f-81f7-f287f8fd9982_%EC%98%A4%EB%A0%8C%EC%A7%80%EC%A3%BC%EC%8A%A4.jpg'),(37,37,30514,'image/jpeg','사과주스.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/36_4515ead7-0308-4e1f-bf0e-bd1f28aa8d42_%EC%82%AC%EA%B3%BC%EC%A3%BC%EC%8A%A4.jpg'),(38,38,32826,'image/jpeg','콜라.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/37_68e659a1-6635-4724-9bd3-96cb02994369_%EC%BD%9C%EB%9D%BC.jpg'),(39,39,46270,'image/jpeg','사이다.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/38_5065c52b-de40-4370-a87c-06df886a4d54_%EC%82%AC%EC%9D%B4%EB%8B%A4.jpg'),(40,40,18376,'image/jpeg','녹차.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/39_7495944f-2998-45d9-916b-0f41564f6c9a_%EB%85%B9%EC%B0%A8.jpg'),(41,41,18710,'image/jpeg','홍차.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/41_802453ad-5015-4db8-8ca0-6b213287d69f_%ED%99%8D%EC%B0%A8.jpg'),(42,42,24534,'image/jpeg','식빵.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/42_de98fb29-7efe-4294-b09d-92584eba3c91_%EC%8B%9D%EB%B9%B5.jpg'),(43,43,47442,'image/jpeg','바게트.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/43_483b9a0b-1b5e-486c-9a87-afddd9421b98_%EB%B0%94%EA%B2%8C%ED%8A%B8.jpg'),(44,44,30542,'image/jpeg','새우깡.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/44_ab6e959f-a285-438b-b9ca-3ca9b4edcb60_%EC%83%88%EC%9A%B0%EA%B9%A1.jpg'),(45,45,32230,'image/jpeg','포카칩.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/45_7fd76604-a859-4b63-bc5a-569c5f8a773e_%ED%8F%AC%EC%B9%B4%EC%B9%A9.jpg'),(46,46,27962,'image/jpeg','다크초콜릿.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/46_f3cddd71-5319-430b-b3f3-3371e62dc4ad_%EB%8B%A4%ED%81%AC%EC%B4%88%EC%BD%9C%EB%A6%BF.jpg'),(47,47,31041,'image/jpeg','밀크초콜릿.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/47_087d3cda-e9b3-4053-a1a9-7c953d9ef22f_%EB%B0%80%ED%81%AC%EC%B4%88%EC%BD%9C%EB%A6%BF.jpg'),(48,48,42484,'image/jpeg','초코케이크.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/48_6b3facf1-1d88-4506-a000-0e0b740fd8e2_%EC%B4%88%EC%BD%94%EC%BC%80%EC%9D%B4%ED%81%AC.jpg'),(49,49,32909,'image/jpeg','생크림케이크.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/49_b69f565c-205c-46b4-8f07-a2a0c850059a_%EC%83%9D%ED%81%AC%EB%A6%BC%EC%BC%80%EC%9D%B4%ED%81%AC.jpg'),(50,50,31870,'image/jpeg','백설기떡.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/50_d64b324e-4320-4382-ac74-e3380ab253b1_%EB%B0%B1%EC%84%A4%EA%B8%B0%EB%96%A1.jpg'),(51,51,45535,'image/jpeg','송편떡.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/51_6e2c6b5f-3743-4c62-8b9e-943ac0fa3379_%EC%86%A1%ED%8E%B8%EB%96%A1.jpg'),(52,52,50574,'image/jpeg','깻잎.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/52_ec761d89-2306-447f-862b-734ea46e944f_%EA%B9%BB%EC%9E%8E.jpg'),(54,21,36654,'image/jpeg','쌈채소모음.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/21_7584349e-c2d7-4759-b159-e2788b17c732_%EC%8C%88%EC%B1%84%EC%86%8C%EB%AA%A8%EC%9D%8C.jpg'),(59,57,425942,'image/jpeg','777b12a2-50f5-42c2-a357-07629875f395.jpg','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/57_222d36b1-039f-4b15-a894-737e6ad3bfae_777b12a2-50f5-42c2-a357-07629875f395.jpg'),(60,58,51813,'image/png','MAIN_ICON.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/58_37fc4fcd-741f-41c4-838b-3ba007ba1065_MAIN_ICON.png'),(61,59,8867,'image/png','logo-512.png','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/59_ca1870bc-6d20-4dc9-ba6b-2fdf4e121ebd_logo-512.png');
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_wish`
--

DROP TABLE IF EXISTS `product_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_wish` (
  `member_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  KEY `FK7h090ge9pl4r1wmje5292cp1j` (`product_id`),
  KEY `FKikhebjw2j7majskmbv4saymhp` (`member_id`),
  CONSTRAINT `FK7h090ge9pl4r1wmje5292cp1j` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  CONSTRAINT `FKikhebjw2j7majskmbv4saymhp` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_wish`
--

LOCK TABLES `product_wish` WRITE;
/*!40000 ALTER TABLE `product_wish` DISABLE KEYS */;
INSERT INTO `product_wish` VALUES (7,10),(3,8),(3,3),(3,13),(3,4),(3,7),(3,6);
/*!40000 ALTER TABLE `product_wish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `provider` tinyint NOT NULL,
  `expiry` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` bigint NOT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `refresh_token` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKdnbbikqdsc2r2cee1afysqfk9` (`member_id`),
  CONSTRAINT `FK5gdbafb2i76hk1ai18ah6an4w` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `refresh_token_chk_1` CHECK ((`provider` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (0,606540045078086,16,4,'102862146406091721056','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDI4NjIxNDY0MDYwOTE3MjEwNTYiLCJpYXQiOjE3NDAwNDUwNzgsImV4cCI6MjM0NDg0NTA3OH0.v3L1wg8IB6zjlFmnOX8yhED68Zpk4ZG-5VrKvZAH2OZrLHnRryEQymtNDjme_GvBdpXtB1Soua6n6po4DWLDIg'),(0,606540045179305,19,3,'115593210315859722561','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTU1OTMyMTAzMTU4NTk3MjI1NjEiLCJpYXQiOjE3NDAwNDUxNzksImV4cCI6MjM0NDg0NTE3OX0.FTvEobM3Eha2-u4xShIGlvd6p6Zfy4I1mkIBANBtE7XDxIejVtHXdJDiEL9UqnX0s4cT8Y6usGh-T0wwxYdhqw'),(0,606540060890012,23,7,'110785011800328362464','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTA3ODUwMTE4MDAzMjgzNjI0NjQiLCJpYXQiOjE3NDAwNjA4OTAsImV4cCI6MjM0NDg2MDg5MH0.wga2YJJguNTmcKKsvpe_QO5gnfqQ-0qs18W5CmE5eXDw6niUmLWvkPoHfYPowIYp42SnkefRNQ3bDiG2CWnU9g'),(1,606540100053090,24,8,'kozpyl1vhshiC_WBjGVmvNbbUzMfo5S2L5eTjBtiI00','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrb3pweWwxdmhzaGlDX1dCakdWbXZOYmJVek1mbzVTMkw1ZVRqQnRpSTAwIiwiaWF0IjoxNzQwMTAwMDUzLCJleHAiOjIzNDQ5MDAwNTN9.8mOddd3pQt-MdvC5uG3i1wpyuiv6IEsWm51t-xEiDzA_dVyzst0oxwSPpGSDwFdrjEDQnfmAHOA_qVku0mQ01g'),(0,606540100424227,26,9,'103326160054522282397','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDMzMjYxNjAwNTQ1MjIyODIzOTciLCJpYXQiOjE3NDAxMDA0MjQsImV4cCI6MjM0NDkwMDQyNH0.JTgKXtNeKt89HvoFOYLgY4pCVChXP-Y2ovB2jW_2873TDqvMabSUgHtC82w5dhv3H3epFQuw80NUFCoLTEg4rw'),(0,606540100758088,27,10,'103094424803972007134','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDMwOTQ0MjQ4MDM5NzIwMDcxMzQiLCJpYXQiOjE3NDAxMDA3NTgsImV4cCI6MjM0NDkwMDc1OH0.QTtC6tvOgdN0cfs69WN4sIQMYGnS9TYpOKBK1l_7nCnrCUrhrjoAnsbNJQQ-o07VV4HKICM0QzUz8OFOaL1lyg'),(1,606540101159029,28,6,'X22oj7FN6sI74TEfym3oNmLfyG0NWXcQvfHm2UX06pw','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJYMjJvajdGTjZzSTc0VEVmeW0zb05tTGZ5RzBOV1hjUXZmSG0yVVgwNnB3IiwiaWF0IjoxNzQwMTAxMTU5LCJleHAiOjIzNDQ5MDExNTl9.pmZx6WUIa5HHvQEqjZlKFPEBLyxFxrwEwWdtzl4n8gMczREDd0j9aThClMXixJgtaA7FGZUO3LhUPVs1ntTCiw');
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seller` (
  `member_id` bigint DEFAULT NULL,
  `seller_id` bigint NOT NULL AUTO_INCREMENT,
  `boss_name` varchar(255) DEFAULT NULL,
  `business_address` varchar(255) DEFAULT NULL,
  `business_email` varchar(255) DEFAULT NULL,
  `business_name` varchar(255) DEFAULT NULL,
  `business_number` varchar(255) DEFAULT NULL,
  `business_tel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seller_id`),
  UNIQUE KEY `UK4177d8wubtfvv2kc4ekrroo85` (`member_id`),
  CONSTRAINT `FK8xy0y20dnm987syb4xvpkglab` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
INSERT INTO `seller` VALUES (2,1,'이연규','서울 금천구 독산로1길 3 (조형유치원)','gyoo97413@gmail.com','쿠팡','1234321','02-123-4567'),(4,3,'김싸피','강원특별자치도 강릉시 사천면 가마골길 8','cks3680@gmail.com','좋은축산','12352367431','02-123-4567'),(9,4,'김갑수','서울 강남구 테헤란로 212 (멀티캠퍼스)','adfadfadf@dsfadsf.com','싸다싸','234523452345',NULL),(10,5,'싸피','서울 강남구 역삼로1길 8 (넛지캠퍼스 빌딩)','antiskj42@gmail.com','싸피','123456789','02-123-4567');
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_category` (
  `category_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sub_category_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl65dyy5me2ypoyj8ou1hnt64e` (`category_id`),
  CONSTRAINT `FKl65dyy5me2ypoyj8ou1hnt64e` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
INSERT INTO `sub_category` VALUES (1,1,'국내산 소고기','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%86%8C%EA%B3%A0%EA%B8%B0.png'),(1,2,'돼지 고기','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8F%BC%EC%A7%80%EA%B3%A0%EA%B8%B0.png'),(1,3,'닭고기','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8B%AD%EA%B3%A0%EA%B8%B0.png'),(1,4,'계란','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%84%EB%9E%80.png'),(1,5,'오리고기','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%98%A4%EB%A6%AC%EA%B3%A0%EA%B8%B0.png'),(2,6,'제철 과일','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%9C%EC%B2%A0%EA%B3%BC%EC%9D%BC.png'),(2,7,'국내산 과일','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B5%AD%EB%82%B4%EC%82%B0%EA%B3%BC%EC%9D%BC.png'),(2,8,'수입 과일','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%88%98%EC%9E%85%EA%B3%BC%EC%9D%BC.png'),(2,9,'뿌리 채소','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%BF%8C%EB%A6%AC%EC%B1%84%EC%86%8C.png'),(2,10,'쌈 채소','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%8C%88%EC%B1%84%EC%86%8C.png'),(3,11,'즉석조리식품','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A6%89%EC%84%9D%EC%A1%B0%EB%A6%AC%EC%8B%9D%ED%92%88.png'),(3,12,'냉동만두/튀김','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%EB%A7%8C%EB%91%90%ED%8A%80%EA%B9%80.png'),(3,13,'냉동피자','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%83%89%EB%8F%99%ED%94%BC%EC%9E%90.png'),(3,14,'아이스크림','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%95%84%EC%9D%B4%EC%8A%A4%ED%81%AC%EB%A6%BC.png'),(3,15,'도시락','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%8F%84%EC%8B%9C%EB%9D%BD.png'),(4,16,'커피원두','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BB%A4%ED%94%BC%EC%9B%90%EB%91%90.png'),(4,17,'생수/탄산수','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%83%9D%EC%88%98%ED%83%84%EC%82%B0%EC%88%98.png'),(4,18,'과일주스','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9D%BC%EC%A3%BC%EC%8A%A4.png'),(4,19,'탄산음료','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%ED%83%84%EC%82%B0%EC%9D%8C%EB%A3%8C.png'),(4,20,'전통차','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%A0%84%ED%86%B5%EC%B0%A8.png'),(5,21,'빵','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%B9%B5.png'),(5,22,'과자/스낵','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EA%B3%BC%EC%9E%90%EC%8A%A4%EB%82%B5.png'),(5,23,'초콜릿/사탕','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%B4%88%EC%BD%9C%EB%A6%BF%EC%82%AC%ED%83%95.png'),(5,24,'케이크','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EC%BC%80%EC%9D%B4%ED%81%AC.png'),(5,25,'떡','https://togetherbuy.s3.ap-northeast-2.amazonaws.com/%EB%96%A1.png');
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-21  1:43:54
