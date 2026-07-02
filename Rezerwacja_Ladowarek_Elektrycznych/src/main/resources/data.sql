INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'operator','{noop}operator','OPERATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='operator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}operator', role='OPERATOR' WHERE login='operator';
INSERT INTO charging_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'AC centrum','CITY',22,2,18,35.00,true WHERE NOT EXISTS (SELECT 1 FROM charging_station WHERE name='AC centrum');
INSERT INTO charging_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'DC Fast','FAST',120,4,75,80.00,true WHERE NOT EXISTS (SELECT 1 FROM charging_station WHERE name='DC Fast');
INSERT INTO charging_station (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ultra Hub','ULTRA',300,5,160,140.00,true WHERE NOT EXISTS (SELECT 1 FROM charging_station WHERE name='Ultra Hub');
