INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'therapist','{noop}therapist','THERAPIST' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='therapist');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}therapist', role='THERAPIST' WHERE login='therapist';
INSERT INTO therapist (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Terapia indywidualna','INDIVIDUAL',2,4,1,140.00,true WHERE NOT EXISTS (SELECT 1 FROM therapist WHERE name='Terapia indywidualna');
INSERT INTO therapist (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Terapia par','COUPLE',3,5,1,190.00,true WHERE NOT EXISTS (SELECT 1 FROM therapist WHERE name='Terapia par');
INSERT INTO therapist (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Interwencja kryzysowa','CRISIS',5,5,1,260.00,true WHERE NOT EXISTS (SELECT 1 FROM therapist WHERE name='Interwencja kryzysowa');
