INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'dispatcher','{noop}dispatcher','DISPATCHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='dispatcher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}dispatcher', role='DISPATCHER' WHERE login='dispatcher';
INSERT INTO snow_removal_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mini plug','SIDEWALK',30,3,10,85.00,true WHERE NOT EXISTS (SELECT 1 FROM snow_removal_vehicle WHERE name='Mini plug');
INSERT INTO snow_removal_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Parking plug','PARKING',120,4,35,170.00,true WHERE NOT EXISTS (SELECT 1 FROM snow_removal_vehicle WHERE name='Parking plug');
INSERT INTO snow_removal_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Solarka drogowa','ROAD',400,5,80,320.00,true WHERE NOT EXISTS (SELECT 1 FROM snow_removal_vehicle WHERE name='Solarka drogowa');
