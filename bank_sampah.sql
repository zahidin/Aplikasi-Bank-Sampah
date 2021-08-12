-- MariaDB dump 10.19  Distrib 10.5.9-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: bank_sampah
-- ------------------------------------------------------
-- Server version	10.5.9-MariaDB-1:10.5.9+maria~focal

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category_trash`
--

DROP TABLE IF EXISTS `category_trash`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_trash` (
  `id_category` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `price_buy` int(11) DEFAULT 0,
  `price_sell` int(11) DEFAULT 0,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_trash`
--

LOCK TABLES `category_trash` WRITE;
/*!40000 ALTER TABLE `category_trash` DISABLE KEYS */;
INSERT INTO `category_trash` VALUES (1,'P1','Gelas Bening','2021-07-24 05:48:38',3000,3500),(2,'P2','Gelas Warna','2021-07-24 06:11:58',3000,4000),(3,'P3','Bodong Bening','2021-07-24 06:12:14',5000,6000),(4,'P5','Putihan','2021-07-24 06:12:25',2000,3500),(5,'P6','Emberan','2021-07-24 06:12:41',4000,5000),(6,'P7','Kristal','2021-07-24 06:12:56',4000,5000),(7,'P8','Ember Hitam','2021-07-24 06:13:33',4500,5500),(8,'P9','Kerasan','2021-07-24 06:13:45',3000,4000),(9,'K1','HVS','2021-07-24 06:13:58',3500,4000),(10,'K2','Buram','2021-07-24 06:14:11',2000,3000),(11,'K3','Duplek','2021-07-24 06:14:21',2500,3000),(12,'K4','Kardus','2021-07-24 06:14:37',4000,5000),(13,'K5','Koran','2021-07-24 06:14:52',5000,6000),(16,'B2','Besi B','2021-07-24 06:17:30',5000,7000),(17,'B3','Kaleng','2021-07-24 06:17:40',6000,7000),(18,'B4','Seng','2021-07-24 06:17:54',7000,8000),(19,'L1','Tembaga','2021-07-24 06:18:09',9000,10000),(20,'L2','Tembaga Bakar ','2021-07-24 06:18:33',8000,9000),(21,'L3','Almunium','2021-07-24 06:18:52',9500,10500),(22,'L4','Aki','2021-07-24 06:18:59',12000,15000);
/*!40000 ALTER TABLE `category_trash` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary_employee`
--

DROP TABLE IF EXISTS `salary_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT NULL,
  `basic_salary` int(11) NOT NULL DEFAULT 0,
  `overtime_salary` int(11) DEFAULT 0,
  `meal_salary` int(11) DEFAULT 0,
  `tax` int(11) DEFAULT 0,
  `insurance` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `no_transaction` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary_employee`
--

LOCK TABLES `salary_employee` WRITE;
/*!40000 ALTER TABLE `salary_employee` DISABLE KEYS */;
INSERT INTO `salary_employee` VALUES (1,14,3000000,500000,300000,100000,80000,'2021-08-06 12:57:52','2021-08-06 12:57:52','SLRY-458473'),(2,15,7000000,0,300000,200000,100000,'2021-08-07 04:37:07','2021-08-07 04:37:07','SLRY-618913'),(3,20,5000000,100000,100000,100000,80000,'2021-08-10 13:19:20','2021-08-10 13:19:20','SLRY-830708');
/*!40000 ALTER TABLE `salary_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_trash`
--

DROP TABLE IF EXISTS `stock_trash`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_trash` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_category` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `id_category` (`id_category`),
  CONSTRAINT `stock_trash_ibfk_1` FOREIGN KEY (`id_category`) REFERENCES `category_trash` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `stock_trash_ibfk_2` FOREIGN KEY (`id_category`) REFERENCES `category_trash` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_trash`
--

LOCK TABLES `stock_trash` WRITE;
/*!40000 ALTER TABLE `stock_trash` DISABLE KEYS */;
INSERT INTO `stock_trash` VALUES (1,1,4,'2021-07-31 04:01:44'),(2,2,4,'2021-07-31 04:06:31'),(3,5,5,'2021-08-10 12:24:07');
/*!40000 ALTER TABLE `stock_trash` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_trash_purchase`
--

DROP TABLE IF EXISTS `transaction_trash_purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_trash_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_transaction` varchar(20) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `id_category` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `transaction_trash_purchase_FK` (`id_user`),
  KEY `transaction_trash_purchase_FK_1` (`id_category`),
  CONSTRAINT `transaction_trash_purchase_FK` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `transaction_trash_purchase_FK_1` FOREIGN KEY (`id_category`) REFERENCES `category_trash` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_trash_purchase`
--

LOCK TABLES `transaction_trash_purchase` WRITE;
/*!40000 ALTER TABLE `transaction_trash_purchase` DISABLE KEYS */;
INSERT INTO `transaction_trash_purchase` VALUES (1,'TRX-736073',3,1,5,50000,'2021-07-31 04:01:44'),(2,'TRX-40198',7,2,5,15000,'2021-07-31 04:06:31'),(3,'TRX-802034',10,5,5,20000,'2021-08-10 12:24:07'),(4,'TRX-641315',9,5,10,40000,'2021-08-10 12:47:04');
/*!40000 ALTER TABLE `transaction_trash_purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_trash_sales`
--

DROP TABLE IF EXISTS `transaction_trash_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_trash_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_transaction` varchar(20) DEFAULT NULL,
  `id_category` int(11) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `id_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `transaction_trash_sales_FK` (`id_category`),
  KEY `transaction_trash_sales_FK_1` (`id_user`),
  CONSTRAINT `transaction_trash_sales_FK` FOREIGN KEY (`id_category`) REFERENCES `category_trash` (`id_category`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `transaction_trash_sales_FK_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_trash_sales`
--

LOCK TABLES `transaction_trash_sales` WRITE;
/*!40000 ALTER TABLE `transaction_trash_sales` DISABLE KEYS */;
INSERT INTO `transaction_trash_sales` VALUES (1,'123123',1,1,10000,'2021-07-31 16:13:57',16),(2,'TRX-67264',1,1,15000,'2021-07-31 17:40:38',16),(3,'TRX-215541',2,1,4000,'2021-08-07 06:45:11',16),(4,'TRX-403650',5,5,25000,'2021-08-10 12:27:30',16),(5,'TRX-842495',5,5,25000,'2021-08-10 12:50:50',16);
/*!40000 ALTER TABLE `transaction_trash_sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `no_account` varchar(100) DEFAULT '0',
  `address` text DEFAULT NULL,
  `telp` varchar(15) DEFAULT NULL,
  `saldo` int(20) DEFAULT 0,
  `role` varchar(50) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','21232f297a57a5a743894a0e4a801fc3','001','Jln. Duren Sawit','08984231473',0,'ADMIN','2021-06-19 13:11:24'),(3,'Makira','Makira','63ee451939ed580ef3c4b6f0109d1fd0','0000635231','jln. sabang jakarta pusat','08575342615',35000,'NASABAH','2021-07-18 06:53:11'),(7,'Ahmad Zakki','zakki','202cb962ac59075b964b07152d234b70','0000998849','Jln. Ramadhan raya no.1','08989834638',15000,'NASABAH','2021-07-23 12:39:26'),(8,'Nia Ramadhani','nia','202cb962ac59075b964b07152d234b70','0000508585','Jl. Kramat V no.31	','08126483917',0,'NASABAH','2021-07-23 12:40:57'),(9,'Kuto Aji','kuto','d9b1d7db4cd6e70935368a1efb10e377','0000175954','Jl. Pengaten ali no.2','08127468364',25000,'NASABAH','2021-07-23 12:41:54'),(10,'Adelia Oktaviani','adelia','202cb962ac59075b964b07152d234b70','0000027395','Jln. Setu raya no.20','08987644352',17000,'NASABAH','2021-07-23 12:43:09'),(14,'Budi Laksono','budi','96105459de9a210f72901a1d2b279846','0000861233','Jln.Duren Sawit','0898984372',0,'PETUGAS_SORTIR','2021-08-05 14:44:45'),(15,'Dwitri Rhaiza Asha','asha','e6366d5e284915139decdfd0bcf07fb6','0000565668','Jln. Jatiwaringin','0898321341',0,'ADMIN','2021-08-07 04:35:53'),(16,'PT. Recycle Indonesian','recycle','219d6e0ff898bf3c0dd5869be9a6bb3c','0000057079','Jawa Tengah, Temanggung','0812381941',0,'MERCHANT','2021-08-07 06:02:01'),(20,'Andi','andi','03339dc0dff443f15c254baccde9bece','0000579856','JLn. Duren','08989812312',0,'ADMIN','2021-08-10 12:14:11');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `withdrawal`
--

DROP TABLE IF EXISTS `withdrawal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `withdrawal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no_transaction` varchar(20) DEFAULT NULL,
  `id_user` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `withdrawal_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `withdrawal_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `withdrawal`
--

LOCK TABLES `withdrawal` WRITE;
/*!40000 ALTER TABLE `withdrawal` DISABLE KEYS */;
INSERT INTO `withdrawal` VALUES (1,'TRX-179121',3,10000,'2021-08-01 07:58:44'),(2,'TRX-319824',3,5000,'2021-08-01 08:00:40'),(3,'TRX-285647',10,1000,'2021-08-10 12:29:33'),(4,'TRX-929718',10,2000,'2021-08-10 12:54:52'),(5,'TRX-956360',9,5000,'2021-08-10 13:09:26'),(6,'TRX-15529',9,10000,'2021-08-10 13:17:09');
/*!40000 ALTER TABLE `withdrawal` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-11  8:50:24
