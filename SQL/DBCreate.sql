-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.30-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2013-07-21 14:05:44
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for javasamp
DROP DATABASE IF EXISTS `javasamp`;
CREATE DATABASE IF NOT EXISTS `javasamp` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `javasamp`;


-- Dumping structure for table javasamp.accchar
DROP TABLE IF EXISTS `accchar`;
CREATE TABLE IF NOT EXISTS `accchar` (
  `acountID` int(10) NOT NULL DEFAULT '0',
  `charId` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`acountID`,`charId`),
  KEY `FK__characters` (`charId`),
  CONSTRAINT `FK__accounts` FOREIGN KEY (`acountID`) REFERENCES `accounts` (`AcountID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__characters` FOREIGN KEY (`charId`) REFERENCES `characters` (`charId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table javasamp.accounts
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE IF NOT EXISTS `accounts` (
  `AcountID` int(10) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Email` varchar(200) NOT NULL,
  KEY `AcountID` (`AcountID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.


-- Dumping structure for table javasamp.characters
DROP TABLE IF EXISTS `characters`;
CREATE TABLE IF NOT EXISTS `characters` (
  `charId` int(10) NOT NULL AUTO_INCREMENT,
  `CharacterName` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `ModelID` int(10) DEFAULT NULL,
  `Birthdate` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
