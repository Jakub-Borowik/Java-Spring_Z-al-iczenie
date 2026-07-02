INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO ac_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Serwis czyszczenia','CLEANING',10,3,5,95.00,true WHERE NOT EXISTS (SELECT 1 FROM ac_technician WHERE name='Serwis czyszczenia');
INSERT INTO ac_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Monter split','INSTALLATION',4,5,2,160.00,true WHERE NOT EXISTS (SELECT 1 FROM ac_technician WHERE name='Monter split');
INSERT INTO ac_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Awaria premium','FAILURE',3,5,2,190.00,true WHERE NOT EXISTS (SELECT 1 FROM ac_technician WHERE name='Awaria premium');
