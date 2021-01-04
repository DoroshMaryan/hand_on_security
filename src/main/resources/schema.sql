# drop schema spring_security_hand_on;
# create schema spring_security_hand_on;
# use spring_security_hand_on;

CREATE TABLE IF NOT EXISTS `spring_security_hand_on`.`user` (
`username` VARCHAR(45) NOT NULL,
`password` TEXT NULL,
PRIMARY KEY (`username`));

CREATE TABLE IF NOT EXISTS `spring_security_hand_on`.`otp` (
`username` VARCHAR(45) NOT NULL,
`code` VARCHAR(45) NULL,
PRIMARY KEY (`username`));