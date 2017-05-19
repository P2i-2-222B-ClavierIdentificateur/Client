use G222_B_BD3;

CREATE TABLE IF NOT EXISTS `CompteSystem` (
  `Login` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Login`));

CREATE TABLE IF NOT EXISTS `Compte` (
  `Login` VARCHAR(45) NOT NULL,
  `masterPassword` VARCHAR(100) NOT NULL,
  `Index` INT(11) ,
  `domainHash` INT(11) NOT NULL,
  `passwordLength` VARCHAR(45) NOT NULL,
  `CompteSystem_Login` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Index`),
    FOREIGN KEY (`CompteSystem_Login`)
    REFERENCES `CompteSystem` (`Login`));

CREATE TABLE IF NOT EXISTS `Session` (
  `index` INT(11) NOT NULL AUTO_INCREMENT,
  `sucess` VARCHAR(45) ,
  `Compte_Index` INT(11) NOT NULL,
  PRIMARY KEY (`index`),
    FOREIGN KEY (`Compte_Index`)
    REFERENCES `Compte` (`Index`));

CREATE TABLE IF NOT EXISTS `Entree` (
  `Index` INT(11) NOT NULL AUTO_INCREMENT,
  `Local` VARCHAR(45) NULL DEFAULT NULL,
  `try` INT(11) NULL DEFAULT NULL,
  `Session_index` INT(11) NOT NULL,
  PRIMARY KEY (`Index`),
  FOREIGN KEY (`Session_index`)
    REFERENCES `Session` (`index`));


CREATE TABLE IF NOT EXISTS `Touche` (
  `index` INT(11) NOT NULL AUTO_INCREMENT,
  `timeUp` VARCHAR(45) ,
  `timeDown` VARCHAR(45) ,
  `pressure` VARCHAR(45) ,
  `modifierSequence` VARCHAR(45) ,
  `shiftUp` VARCHAR(45) ,
  `shiftDown` VARCHAR(45) ,
  `shiftLocation` VARCHAR(45) ,
  `ctrlUp` VARCHAR(45) ,
  `ctrlDown` VARCHAR(45) ,
  `ctrlLocation` VARCHAR(45) ,
  `altUp` VARCHAR(45) ,
  `altDown` VARCHAR(45) ,
  `altLocation` VARCHAR(45) ,
  `capslockUp` VARCHAR(45) ,
  `capslockDown` VARCHAR(45) ,
  `Entree_Index` INT(11) NOT NULL,
  PRIMARY KEY (`index`),
    FOREIGN KEY (`Entree_Index`)
    REFERENCES `Entree` (`Index`));