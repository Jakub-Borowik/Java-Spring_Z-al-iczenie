INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'inspector','{noop}inspector','INSPECTOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='inspector');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}inspector', role='INSPECTOR' WHERE login='inspector';
INSERT INTO inspection_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Osobowe','CAR',4,3,3,90.00,true WHERE NOT EXISTS (SELECT 1 FROM inspection_station WHERE name='Osobowe');
INSERT INTO inspection_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ciezarowe','TRUCK',30,5,2,190.00,true WHERE NOT EXISTS (SELECT 1 FROM inspection_station WHERE name='Ciezarowe');
INSERT INTO inspection_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Specjalne','SPECIAL',20,5,1,230.00,true WHERE NOT EXISTS (SELECT 1 FROM inspection_station WHERE name='Specjalne');
