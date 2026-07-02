INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'dispatcher','{noop}dispatcher','DISPATCHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='dispatcher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}dispatcher', role='DISPATCHER' WHERE login='dispatcher';
INSERT INTO meal_route (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Trasa centrum','CENTER',55,3,24,75.00,true WHERE NOT EXISTS (SELECT 1 FROM meal_route WHERE name='Trasa centrum');
INSERT INTO meal_route (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Trasa podmiejska','SUBURBS',80,4,20,95.00,true WHERE NOT EXISTS (SELECT 1 FROM meal_route WHERE name='Trasa podmiejska');
INSERT INTO meal_route (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Trasa biurowa','BUSINESS',70,5,30,115.00,true WHERE NOT EXISTS (SELECT 1 FROM meal_route WHERE name='Trasa biurowa');
