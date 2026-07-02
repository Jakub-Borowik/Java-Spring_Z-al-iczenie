INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'technician','{noop}technician','TECHNICIAN' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='technician');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}technician', role='TECHNICIAN' WHERE login='technician';
INSERT INTO solar_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Przeglad paneli','INSPECTION',60,3,20,130.00,true WHERE NOT EXISTS (SELECT 1 FROM solar_technician WHERE name='Przeglad paneli');
INSERT INTO solar_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Falownik','INVERTER',20,5,5,190.00,true WHERE NOT EXISTS (SELECT 1 FROM solar_technician WHERE name='Falownik');
INSERT INTO solar_technician (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Praca na dachu','ROOF',45,5,10,230.00,true WHERE NOT EXISTS (SELECT 1 FROM solar_technician WHERE name='Praca na dachu');
