-- -----------------------------------------------------
-- Table `Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Account`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `IBAN` VARCHAR(34) NULL DEFAULT NULL,
    `name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `Payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Payment`
(
    `id`               INT          NOT NULL AUTO_INCREMENT,
    `date`             DATETIME     NULL DEFAULT NULL,
    `amount`           FLOAT        NULL DEFAULT NULL,
    `currency`         VARCHAR(45)  NULL DEFAULT NULL,
    `detail`           VARCHAR(255) NULL DEFAULT NULL,
    `accountId`        INT          NOT NULL,
    `counterAccountId` INT          NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Payment_Account_idx` (`accountId` ASC),
    INDEX `fk_Payment_Counter_Account_idx` (`counterAccountId` ASC),
    CONSTRAINT `fk_Payment_Account_Id`
        FOREIGN KEY (`accountId`)
            REFERENCES `Account` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_Payment_CounterAccount_Id`
        FOREIGN KEY (`counterAccountId`)
            REFERENCES `Account` (`id`)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

INSERT INTO `Account`
VALUES (1, 'dummyIBAN', 'dummyName');

INSERT INTO `Account`
VALUES (2, 'dummyIBAN2', 'dummyName2');


INSERT INTO `Payment`
VALUES (1, '2020-06-18T10:34:09', 29.99, 'eur', 'payment 1', 1, 2);

INSERT INTO `Payment`
VALUES (2, '2020-03-12T12:25:36', 149.99, 'eur', 'payment 2', 1, 2);