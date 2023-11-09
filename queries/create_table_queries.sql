
CREATE TABLE User (
           	userID VARCHAR(16),
           	name VARCHAR(128) NOT NULL,
            status CHAR(2),
            password VARCHAR(50),
           	PRIMARY KEY(userID)
);



CREATE TABLE ParkingLot (
           	parkingLotID VARCHAR(16),
           	name VARCHAR(64),
           	address VARCHAR(128) NOT NULL,
PRIMARY KEY(parkingLotID)
);



CREATE TABLE Zone (
           	parkingLotID VARCHAR(16) NOT NULL,
           	zoneID VARCHAR(16) NOT NULL,
PRIMARY KEY(parkingLotID, zoneID),
FOREIGN KEY(parkingLotID) 
REFERENCES ParkingLot(parkingLotID)
                          	ON UPDATE CASCADE
			ON DELETE CASCADE
);


CREATE TABLE Space (
           	parkingLotID VARCHAR(16) NOT NULL,
           	zoneID VARCHAR(16) NOT NULL,
spaceNumber INT NOT NULL,
spaceType VARCHAR(16) NOT NULL DEFAULT 'Regular',
availability VARCHAR(16),
PRIMARY KEY(parkingLotID, zoneID, spaceNumber),
FOREIGN KEY(parkingLotID, zoneID) 
REFERENCES Zone(parkingLotID,zoneID)
             ON UPDATE CASCADE
	ON DELETE CASCADE
);



CREATE TABLE Vehicle (
           	carLicenseNo VARCHAR(16),
           	manufacturer VARCHAR(64),
           	model VARCHAR(64),
           	color VARCHAR(64),
           	year INT,
PRIMARY KEY(carLicenseNo)
);



CREATE TABLE Citations (
  citationNumber VARCHAR(16),
  carLicenseNo VARCHAR(16) NOT NULL,
  -- userID VARCHAR(16) NOT NULL,
  parkingLotID VARCHAR(16) NOT NULL,
  citationDate DATE NOT NULL,
  citationTime TIME NOT NULL,
  category VARCHAR(16),
  fee INT NOT NULL,
  paymentStatus VARCHAR(16),
  appeal VARCHAR(5) DEFAULT 'no',
  PRIMARY KEY(citationNumber),
  -- FOREIGN KEY(carLicenseNo) REFERENCES Vehicle(carLicenseNo)
  --   ON UPDATE CASCADE
  --   ON DELETE CASCADE,
  -- FOREIGN KEY(userID) REFERENCES User(userID)
  --   ON UPDATE CASCADE
  --   ON DELETE CASCADE,
  FOREIGN KEY(parkingLotID) REFERENCES ParkingLot(parkingLotID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);




CREATE TABLE Driver (
           	userID VARCHAR(16),
           	disabilityStatus VARCHAR(128) NOT NULL,
           	PRIMARY KEY(userID),
           	FOREIGN KEY(userID) 
REFERENCES User(userID)
                          	ON UPDATE CASCADE
			ON DELETE CASCADE
);




CREATE TABLE Permit (
  permitID VARCHAR(16),
  carLicenseNo VARCHAR(16) NOT NULL,
  parkingLotID VARCHAR(16) NOT NULL,
  zoneID VARCHAR(16) NOT NULL,
  spaceNumber INT NOT NULL,
  spaceType VARCHAR(16),
  startDate DATE NOT NULL,
  expDate DATE,
  expTime TIME,
  userID VARCHAR(16) NOT NULL,
  permitType VARCHAR(16),
  PRIMARY KEY(permitID),
  FOREIGN KEY(carLicenseNo) REFERENCES Vehicle(carLicenseNo)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY(parkingLotID, zoneID, spaceNumber) REFERENCES Space(parkingLotID, zoneID, spaceNumber)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY(userID) REFERENCES User(userID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);





CREATE TABLE manages (
           	userID VARCHAR(16) NOT NULL,
           	citationNumber VARCHAR(16) NOT NULL,
PRIMARY KEY(userID, citationNumber),
FOREIGN KEY(userID) 
REFERENCES User(userID)
    ON UPDATE CASCADE
	ON DELETE CASCADE,
FOREIGN KEY(citationNumber) 
REFERENCES Citations(citationNumber)
    ON UPDATE CASCADE
	ON DELETE CASCADE
);



CREATE TABLE hasAccessTo (
           	userID VARCHAR(16) NOT NULL,
           	parkingLotID VARCHAR(16) NOT NULL,
PRIMARY KEY(userID, parkingLotID),
FOREIGN KEY(userID) 
REFERENCES User(userID)
    ON UPDATE CASCADE
	ON DELETE CASCADE,
FOREIGN KEY(parkingLotID) 
REFERENCES ParkingLot(parkingLotID)
    ON UPDATE CASCADE
	ON DELETE CASCADE	
);




CREATE TABLE registered (
           	userID VARCHAR(16),
           	carLicenseNo VARCHAR(16),
PRIMARY KEY(userID, carLicenseNo),
FOREIGN KEY(userID) 
REFERENCES User(userID)
	ON UPDATE CASCADE
	ON DELETE CASCADE,
FOREIGN KEY(carLicenseNo) 
REFERENCES Vehicle(carLicenseNo)
    ON UPDATE CASCADE
	ON DELETE CASCADE
);




CREATE TABLE Visitor (
    	phoneNumber VARCHAR(16),
    	carLicenseNo VARCHAR(16) NOT NULL,
    	PRIMARY KEY(phoneNumber),
    	FOREIGN KEY(phoneNumber) 
REFERENCES Driver(userID) 
	ON UPDATE CASCADE
	ON DELETE CASCADE,
    	FOREIGN KEY(carLicenseNo) 
REFERENCES Vehicle(carLicenseNo) 
	ON UPDATE CASCADE
	ON DELETE CASCADE
);




CREATE TABLE Employee (
           	userID VARCHAR(16),
           	carLicenseNo VARCHAR(16) NOT NULL,
           	PRIMARY KEY(userID),
           	FOREIGN KEY(userID) 
REFERENCES Driver(userID)
    ON UPDATE CASCADE
	ON DELETE CASCADE,
FOREIGN KEY(carLicenseNo) 
REFERENCES Vehicle(carLicenseNo)
    ON UPDATE CASCADE
 	ON DELETE CASCADE
);




CREATE TABLE Student (
  userID VARCHAR(16),
  carLicenseNo VARCHAR(16) NOT NULL,
  PRIMARY KEY(userID),
  FOREIGN KEY(userID) REFERENCES Driver(userID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY(carLicenseNo) REFERENCES Vehicle(carLicenseNo)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);





CREATE TABLE SecurityPersonnel (
           	userID VARCHAR(16),
           	PRIMARY KEY(userID),
           	FOREIGN KEY(userID) 
REFERENCES User(userID)
    ON UPDATE CASCADE
	ON DELETE CASCADE
);




CREATE TABLE Admin (
           	userID VARCHAR(16),
           	PRIMARY KEY(userID),
           	FOREIGN KEY(userID) 
REFERENCES User(userID)
            ON UPDATE CASCADE
			ON DELETE CASCADE
);









