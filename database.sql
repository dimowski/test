-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema time_manager
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema time_manager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `time_manager` DEFAULT CHARACTER SET utf8 ;
USE `time_manager` ;

-- -----------------------------------------------------
-- Table `time_manager`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_manager`.`user` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(50) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `pwd` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `time_manager`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_manager`.`category` (
  `category_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(45) NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`category_id`),
  INDEX `category_user_idx` (`user_id` ASC),
  CONSTRAINT `category_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `time_manager`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `time_manager`.`subcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_manager`.`subcategory` (
  `subcategory_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `subcategory` VARCHAR(45) NOT NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`subcategory_id`),
  INDEX `subcategory_user_idx` (`user_id` ASC),
  CONSTRAINT `subcategory_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `time_manager`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `time_manager`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_manager`.`activity` (
  `activity_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` INT UNSIGNED NULL,
  `subcategory_id` INT UNSIGNED NULL DEFAULT NULL,
  `start_time` DATETIME NOT NULL,
  `finish_time` DATETIME NULL,
  `description` VARCHAR(100) NULL,
  `user_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`activity_id`),
  INDEX `activity_user_idx` (`user_id` ASC),
  INDEX `activity_category_idx` (`category_id` ASC),
  INDEX `activity_subcategory_idx` (`subcategory_id` ASC),
  CONSTRAINT `activity_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `time_manager`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `activity_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `time_manager`.`category` (`category_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `activity_subcategory`
    FOREIGN KEY (`subcategory_id`)
    REFERENCES `time_manager`.`subcategory` (`subcategory_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
