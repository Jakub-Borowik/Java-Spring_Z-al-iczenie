INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'coordinator','{noop}coordinator','COORDINATOR' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='coordinator');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}coordinator', role='COORDINATOR' WHERE login='coordinator';
INSERT INTO exam_room (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Sala pisemna','WRITTEN',80,3,1,60.00,true WHERE NOT EXISTS (SELECT 1 FROM exam_room WHERE name='Sala pisemna');
INSERT INTO exam_room (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Sala komputerowa','COMPUTER',35,5,1,140.00,true WHERE NOT EXISTS (SELECT 1 FROM exam_room WHERE name='Sala komputerowa');
INSERT INTO exam_room (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Sala ustna','ORAL',10,4,1,45.00,true WHERE NOT EXISTS (SELECT 1 FROM exam_room WHERE name='Sala ustna');
