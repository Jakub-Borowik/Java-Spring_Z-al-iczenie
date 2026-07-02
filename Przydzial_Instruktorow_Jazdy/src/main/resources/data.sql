INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'instructor','{noop}instructor','INSTRUCTOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='instructor');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}instructor', role='INSTRUCTOR' WHERE login='instructor';
INSERT INTO driving_instructor (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Instruktor miasto','CITY',3,4,6,85.00,true WHERE NOT EXISTS (SELECT 1 FROM driving_instructor WHERE name='Instruktor miasto');
INSERT INTO driving_instructor (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Plac manewrowy','MANEUVERS',2,3,5,70.00,true WHERE NOT EXISTS (SELECT 1 FROM driving_instructor WHERE name='Plac manewrowy');
INSERT INTO driving_instructor (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Przygotowanie egzamin','EXAM',1,5,4,110.00,true WHERE NOT EXISTS (SELECT 1 FROM driving_instructor WHERE name='Przygotowanie egzamin');
