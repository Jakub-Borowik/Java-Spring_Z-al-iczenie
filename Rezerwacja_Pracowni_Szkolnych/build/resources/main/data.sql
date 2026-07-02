INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'teacher','{noop}teacher','TEACHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='teacher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}teacher', role='TEACHER' WHERE login='teacher';
INSERT INTO school_lab (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Chemia','CHEMISTRY',24,5,1,75.00,true WHERE NOT EXISTS (SELECT 1 FROM school_lab WHERE name='Chemia');
INSERT INTO school_lab (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Informatyka','COMPUTER',30,4,1,65.00,true WHERE NOT EXISTS (SELECT 1 FROM school_lab WHERE name='Informatyka');
INSERT INTO school_lab (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Warsztat','WORKSHOP',18,5,1,90.00,true WHERE NOT EXISTS (SELECT 1 FROM school_lab WHERE name='Warsztat');
