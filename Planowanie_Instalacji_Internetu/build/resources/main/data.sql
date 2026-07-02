INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO internet_installer (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Swiatlowod dom','FIBER',8,4,3,120.00,true WHERE NOT EXISTS (SELECT 1 FROM internet_installer WHERE name='Swiatlowod dom');
INSERT INTO internet_installer (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Radio teren','RADIO',6,3,2,100.00,true WHERE NOT EXISTS (SELECT 1 FROM internet_installer WHERE name='Radio teren');
INSERT INTO internet_installer (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Siec biznesowa','BUSINESS',20,5,5,220.00,true WHERE NOT EXISTS (SELECT 1 FROM internet_installer WHERE name='Siec biznesowa');
