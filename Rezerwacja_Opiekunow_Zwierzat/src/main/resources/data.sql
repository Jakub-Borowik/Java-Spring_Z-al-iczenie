INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'sitter','{noop}sitter','SITTER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='sitter');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}sitter', role='SITTER' WHERE login='sitter';
INSERT INTO pet_sitter (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Spacer z psem','DOG',3,3,4,45.00,true WHERE NOT EXISTS (SELECT 1 FROM pet_sitter WHERE name='Spacer z psem');
INSERT INTO pet_sitter (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Opieka nad kotem','CAT',5,3,5,40.00,true WHERE NOT EXISTS (SELECT 1 FROM pet_sitter WHERE name='Opieka nad kotem');
INSERT INTO pet_sitter (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Opieka specjalna','SPECIAL',2,5,2,90.00,true WHERE NOT EXISTS (SELECT 1 FROM pet_sitter WHERE name='Opieka specjalna');
