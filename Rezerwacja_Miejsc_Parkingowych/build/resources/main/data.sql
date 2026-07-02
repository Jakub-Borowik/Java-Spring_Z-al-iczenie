INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'guard','{noop}guard','GUARD' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='guard');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}guard', role='GUARD' WHERE login='guard';
INSERT INTO parking_spot (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Miejsce standard','STANDARD',2,2,1,12.00,true WHERE NOT EXISTS (SELECT 1 FROM parking_spot WHERE name='Miejsce standard');
INSERT INTO parking_spot (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Miejsce zadaszone','COVERED',3,4,1,25.00,true WHERE NOT EXISTS (SELECT 1 FROM parking_spot WHERE name='Miejsce zadaszone');
INSERT INTO parking_spot (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Miejsce EV','EV',3,5,1,35.00,true WHERE NOT EXISTS (SELECT 1 FROM parking_spot WHERE name='Miejsce EV');
