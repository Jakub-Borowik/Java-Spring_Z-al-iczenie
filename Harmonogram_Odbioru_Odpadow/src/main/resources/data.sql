INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'dispatcher','{noop}dispatcher','DISPATCHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='dispatcher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}dispatcher', role='DISPATCHER' WHERE login='dispatcher';
INSERT INTO garbage_truck (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Smieciarka miejska','MIXED',250,3,65,150.00,true WHERE NOT EXISTS (SELECT 1 FROM garbage_truck WHERE name='Smieciarka miejska');
INSERT INTO garbage_truck (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Bio odbior','BIO',140,4,45,130.00,true WHERE NOT EXISTS (SELECT 1 FROM garbage_truck WHERE name='Bio odbior');
INSERT INTO garbage_truck (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Elektro bus','ELECTRO',60,5,22,110.00,true WHERE NOT EXISTS (SELECT 1 FROM garbage_truck WHERE name='Elektro bus');
