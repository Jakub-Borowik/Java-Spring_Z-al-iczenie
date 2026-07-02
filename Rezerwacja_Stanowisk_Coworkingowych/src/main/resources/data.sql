INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'manager','{noop}manager','MANAGER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='manager');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}manager', role='MANAGER' WHERE login='manager';
INSERT INTO work_desk (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Biurko open space','OPEN_SPACE',1,2,1,25.00,true WHERE NOT EXISTS (SELECT 1 FROM work_desk WHERE name='Biurko open space');
INSERT INTO work_desk (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Pokoj cichy','QUIET',2,4,1,55.00,true WHERE NOT EXISTS (SELECT 1 FROM work_desk WHERE name='Pokoj cichy');
INSERT INTO work_desk (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Sala projektowa','MEETING',8,5,1,120.00,true WHERE NOT EXISTS (SELECT 1 FROM work_desk WHERE name='Sala projektowa');
