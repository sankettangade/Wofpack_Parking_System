INSERT INTO Vehicle (carLicenseNo, model, color, manufacturer, year) VALUES
('SBF', 'GT-R-Nismo', 'Pearl White TriCoat', 'Nissan', 2024),
('Clay1', 'Model S', 'Ultra Red', 'Tesla', 2023),
('Hicks1', 'M2 Coupe', 'Zandvoort Blue', 'BMW', 2024),
('Garcia1', 'Continental GT Speed', 'Blue Fusion', 'Bentley', 2024),
('CRICKET', 'Civic SI', 'Sonic Gray Pearl', 'Honda', 2024),
('PROFX', 'Taycan Sport Turismo', 'Frozenblue Metallic', 'Porsche', 2024);


INSERT INTO User (userID, name) VALUES
('7729119111', 'Sam BankmanFried'),
('266399121', 'John Clay'),
('366399121', 'Julia Hicks'),
('466399121', 'Ivan Garcia'),
('122765234', 'Sachin Tendulkar'),
('9194789124', 'Charles Xavier'),
('488213007', 'Matthew Geller'),
('789214567', 'Virat Kohli'),
('985467810', 'Monica Perry'),
('985312442', 'Ishan Kishan');


INSERT INTO Driver (userID, disabilityStatus) VALUES
('7729119111', 'N'),
('266399121', 'N'),
('366399121', 'N'),
('466399121', 'N'),
('122765234', 'N'),
('9194789124', 'D');


INSERT INTO Visitor (phoneNumber, carLicenseNo) VALUES
('7729119111', 'SBF'),
('9194789124', 'PROFX');


INSERT INTO Employee (userID, carLicenseNo) VALUES
('266399121', 'Clay1'),
('366399121', 'Hicks1'),
('466399121', 'Garcia1');


INSERT INTO Student (userID, carLicenseNo) VALUES
('122765234', 'CRICKET');


INSERT INTO ParkingLot (parkingLotID, name, address) VALUES
('PL1', 'Poulton Deck', '1021 Main Campus Dr\nRaleigh, NC, 27606'),
('PL2', 'Partners Way Deck', '851 Partners Way\nRaleigh, NC, 27606'),
('PL3', 'Dan Allen Parking Deck', '110 Dan Allen Dr\nRaleigh, NC, 27607');


INSERT INTO Citations (citationNumber, carLicenseNo, parkingLotID, citationDate, citationTime, category, fee, paymentStatus) VALUES
('NP1', 'VAN-9910', 'PL3', '2021-10-11', '08:00:00', 'No Permit', 40, 'PAID'),
('EP1', 'CRICKET', 'PL1', '2023-10-01', '08:00:00', 'Expired Permit', 30, 'DUE');


INSERT INTO Zone (parkingLotID, zoneID) VALUES
('PL1', 'A'),
('PL1', 'B'),
('PL1', 'C'),
('PL1', 'AS'),
('PL1', 'V'),
('PL1', 'CS'),
('PL1', 'D'),
('PL1', 'BS'),
('PL2', 'A'),
('PL2', 'B'),
('PL2', 'AS'),
('PL2', 'BS'),
('PL2', 'V'),
('PL2', 'C'),
('PL3', 'D'),
('PL3', 'DS'),
('PL3', 'V'),
('PL3', 'A'),
('PL3', 'AS');


INSERT INTO Space (parkingLotID, zoneID, spaceNumber, spaceType, availability) VALUES
('PL1', 'A', 9, 'Handicap', 'Y'),
('PL1', 'A', 10, 'Regular', 'N'),
('PL1', 'A', 12, 'Electric', 'Y'),
('PL1', 'B', 1, 'Regular', 'Y'),
('PL1', 'C', 2, 'Electric', 'Y'),
('PL1', 'AS', 3, 'Regular', 'Y'),
('PL1', 'V', 1, 'Regular', 'N'),
('PL1', 'V', 2, 'Compact Car', 'Y'),
('PL1', 'CS', 20, 'Handicap', 'Y'),
('PL1', 'D', 21, 'Regular', 'Y'),
('PL1', 'BS', 22, 'Electric', 'Y'),
('PL2', 'A', 2, 'Electric', 'N'),
('PL2', 'A', 4, 'Regular', 'Y'),
('PL2', 'B', 11, 'Handicap', 'Y'),
('PL2', 'AS', 12, 'Regular', 'Y'),
('PL2', 'BS', 13, 'Electric', 'Y'),
('PL2', 'V', 9, 'Handicap', 'Y'),
('PL2', 'C', 10, 'Compact Car', 'Y'),
('PL3', 'D', 11, 'Regular', 'Y'),
('PL3', 'DS', 2, 'Regular', 'Y'),
('PL3', 'V', 3, 'Handicap', 'N'),
('PL3', 'A', 13, 'Regular', 'N'),
('PL3', 'A', 14, 'Regular', 'Y'),
('PL3', 'AS', 14, 'Compact Car', 'N'),
('PL3', 'AS', 17, 'Regular', 'Y');


INSERT INTO Permit (permitID, carLicenseNo, parkingLotID, zoneID, spaceNumber, spaceType, startDate, expDate, expTime, userID, permitType) VALUES
('VSBF1C', 'SBF', 'PL1', 'V', 1, 'Regular', '2023-01-01', '2024-01-01', '06:00:00', '7729119111', 'Commuter'),
('EJC1R', 'Clay1', 'PL2', 'A', 2, 'Electric', '2010-01-01', '2030-01-01', '06:00:00', '266399121', 'Residential'),
('EJH2C', 'Hicks1', 'PL1', 'A', 10, 'Regular', '2023-01-01', '2024-01-01', '06:00:00', '366399121', 'Commuter'),
('EIG3C', 'Garcia1', 'PL3', 'A', 13, 'Regular', '2023-01-01', '2024-01-01', '06:00:00', '466399121', 'Commuter'),
('SST1R', 'CRICKET', 'PL3', 'AS', 14, 'Compact Car', '2022-01-01', '2023-09-30', '06:00:00', '122765234', 'Residential'),
('VCX1SE', 'PROFX', 'PL3', 'V', 3, 'Handicap', '2023-01-01', '2023-11-15', '06:00:00', '9194789124', 'Special event');

