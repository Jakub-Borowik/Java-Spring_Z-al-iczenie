INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'warehouse','{noop}warehouse','WAREHOUSE' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='warehouse');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}warehouse', role='WAREHOUSE' WHERE login='warehouse';
INSERT INTO storage_box (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Box maly','SMALL',10,2,1,18.00,true WHERE NOT EXISTS (SELECT 1 FROM storage_box WHERE name='Box maly');
INSERT INTO storage_box (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Box klimatyzowany','CLIMATE',25,5,1,55.00,true WHERE NOT EXISTS (SELECT 1 FROM storage_box WHERE name='Box klimatyzowany');
INSERT INTO storage_box (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Box paletowy','HEAVY',80,4,1,80.00,true WHERE NOT EXISTS (SELECT 1 FROM storage_box WHERE name='Box paletowy');
