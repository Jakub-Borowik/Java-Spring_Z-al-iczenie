INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'coordinator','{noop}coordinator','COORDINATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='coordinator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}coordinator', role='COORDINATOR' WHERE login='coordinator';
INSERT INTO renovation_crew (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ekipa malarska','PAINTING',80,3,20,120.00,true WHERE NOT EXISTS (SELECT 1 FROM renovation_crew WHERE name='Ekipa malarska');
INSERT INTO renovation_crew (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ekipa lazienkowa','BATHROOM',35,5,8,180.00,true WHERE NOT EXISTS (SELECT 1 FROM renovation_crew WHERE name='Ekipa lazienkowa');
INSERT INTO renovation_crew (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Ekipa generalna','GENERAL',120,5,14,240.00,true WHERE NOT EXISTS (SELECT 1 FROM renovation_crew WHERE name='Ekipa generalna');
