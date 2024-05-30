
-- -----------------------------------------------------
-- Schema suseok
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `suseok` DEFAULT CHARACTER SET utf8 ;
USE `suseok` ;

-- ----------------------------------------------------
-- Table `suseok`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`User` (
  `user_seq` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(100) NOT NULL,
  `user_pwd` VARCHAR(100) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_nick` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
  `address` VARCHAR(150) NULL,
  `img` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `exposure` BIT NOT NULL,
  PRIMARY KEY (`user_seq`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) ,
  UNIQUE INDEX `user_nick_UNIQUE` (`user_nick` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `suseok`.`Group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`Group` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(100) NOT NULL,
  `group_admin` int NOT NULL,
  `goal_pace` DOUBLE NULL DEFAULT 0,
  `goal_frequency` DOUBLE NULL DEFAULT 0,
  `goal_total_distance` DOUBLE NULL DEFAULT 0,
  `con_pace` DOUBLE NULL,
  `con_frequency` DOUBLE NULL,
  `con_total_distance` DOUBLE NULL,
  `pace` DOUBLE NULL DEFAULT 0,
  `frequency` DOUBLE NULL DEFAULT 0,
  `total_distance` DOUBLE NULL DEFAULT 0,
  PRIMARY KEY (`group_id`),
  UNIQUE INDEX `group_name_UNIQUE` (`group_name` ASC) ,
  INDEX `fk_Group_User1_idx` (`group_admin` ASC) ,
  CONSTRAINT `fk_Group_User1`
    FOREIGN KEY (`group_admin`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `suseok`.`Group_member_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`Group_member_info` (
  `group_id` INT NOT NULL,
  `user_seq` INT NOT NULL,
  PRIMARY KEY (`group_id`, `user_seq`),
  INDEX `fk_Group_memeber_info_User1_idx` (`user_seq` ASC) ,
  CONSTRAINT `fk_Group_memeber_info_Group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `suseok`.`Group` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Group_memeber_info_User1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `suseok`.`user_rank_record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`user_rank_record` (
  `user_seq` INT NOT NULL,
  `frequency` DOUBLE NOT NULL,
  `total_distance` DOUBLE NOT NULL,
  `highest_pace` DOUBLE NOT NULL,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_seq`),
  INDEX `fk_record2_User1_idx` (`user_seq` ASC) ,
  CONSTRAINT `fk_record2_User1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `suseok`.`Rival_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`Rival_info` (
  `user_seq` INT NOT NULL,
  `rival_seq` INT NOT NULL,
  PRIMARY KEY (`user_seq`, `rival_seq`),
  INDEX `fk_Rival_info_User2_idx` (`rival_seq` ASC) ,
  CONSTRAINT `fk_Rival_info_User1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Rival_info_User2`
    FOREIGN KEY (`rival_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `suseok`.`board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `group_id` INT NOT NULL,
  `writer_seq` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `content` TEXT NOT NULL,
  `img` VARCHAR(45) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notice` BIT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_board_User1_idx` (`writer_seq` ASC) ,
  INDEX `fk_board_Group1_idx` (`group_id` ASC) ,
  CONSTRAINT `fk_board_User1`
    FOREIGN KEY (`writer_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_board_Group1`
    FOREIGN KEY (`group_id`)
    REFERENCES `suseok`.`Group` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `suseok`.`reply`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`reply` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `board_id` INT NOT NULL,
  `writer_seq` INT NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_reply_board1_idx` (`board_id` ASC) ,
  INDEX `fk_reply_User1_idx` (`writer_seq` ASC) ,
  CONSTRAINT `fk_reply_board1`
    FOREIGN KEY (`board_id`)
    REFERENCES `suseok`.`board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reply_User1`
    FOREIGN KEY (`writer_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `suseok`.`jwt_tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `suseok`.`jwt` (
  `refresh_token` VARCHAR(256) NOT NULL,
  `user_seq` int NOT NULL,
  PRIMARY KEY (`refresh_token`),
  INDEX `fk_jwt_dto_User1_idx` (`user_seq` ASC) ,
  CONSTRAINT `fk_jwt_dto_User1`
    FOREIGN KEY (`user_seq`)
    REFERENCES `suseok`.`User` (`user_seq`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

select * from board;
select * from user;
select * from user_rank_record;



