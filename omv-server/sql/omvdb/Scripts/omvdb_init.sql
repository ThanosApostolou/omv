CREATE DATABASE IF NOT EXISTS omvdb;
USE omvdb;
CREATE USER IF NOT EXISTS 'omv'@'localhost' IDENTIFIED BY 'omv';
GRANT ALL PRIVILEGES ON omvdb.* TO 'omv'@'localhost';

CREATE TABLE IF NOT EXISTS USER (
	id int(10) unsigned NOT NULL AUTO_INCREMENT,
	email varchar(255) DEFAULT NULL,
	username varchar(255) DEFAULT NULL,
	password varchar(255) DEFAULT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY email (email)
);

