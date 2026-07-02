INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'nurse','{noop}nurse','NURSE' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='nurse');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}nurse', role='NURSE' WHERE login='nurse';
INSERT INTO nurse_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Zespol podstawowy','BASIC',4,3,6,90.00,true WHERE NOT EXISTS (SELECT 1 FROM nurse_team WHERE name='Zespol podstawowy');
INSERT INTO nurse_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Rehabilitacja domowa','REHAB',3,5,4,130.00,true WHERE NOT EXISTS (SELECT 1 FROM nurse_team WHERE name='Rehabilitacja domowa');
INSERT INTO nurse_team (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Opieka pooperacyjna','POST_SURGERY',2,5,3,160.00,true WHERE NOT EXISTS (SELECT 1 FROM nurse_team WHERE name='Opieka pooperacyjna');
