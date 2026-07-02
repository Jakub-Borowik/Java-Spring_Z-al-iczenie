INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'supervisor','{noop}supervisor','SUPERVISOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='supervisor');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}supervisor', role='SUPERVISOR' WHERE login='supervisor';
INSERT INTO window_cleaning_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Mieszkania','FLAT',30,2,12,80.00,true WHERE NOT EXISTS (SELECT 1 FROM window_cleaning_team WHERE name='Mieszkania');
INSERT INTO window_cleaning_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Biura','OFFICE',90,4,25,150.00,true WHERE NOT EXISTS (SELECT 1 FROM window_cleaning_team WHERE name='Biura');
INSERT INTO window_cleaning_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Prace wysokosciowe','HEIGHT',50,5,10,260.00,true WHERE NOT EXISTS (SELECT 1 FROM window_cleaning_team WHERE name='Prace wysokosciowe');
