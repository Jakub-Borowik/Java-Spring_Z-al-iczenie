INSERT INTO app_user (login,password,role) SELECT 'client','{noop}client','CLIENT' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='client');
INSERT INTO app_user (login,password,role) SELECT 'dispatcher','{noop}dispatcher','DISPATCHER' WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login='dispatcher');
UPDATE app_user SET password='{noop}client', role='CLIENT' WHERE login='client';
UPDATE app_user SET password='{noop}dispatcher', role='DISPATCHER' WHERE login='dispatcher';
INSERT INTO medical_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Transport siedzacy','SITTING',3,3,45,120.00,true WHERE NOT EXISTS (SELECT 1 FROM medical_vehicle WHERE name='Transport siedzacy');
INSERT INTO medical_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Transport lezacy','LYING',4,5,35,180.00,true WHERE NOT EXISTS (SELECT 1 FROM medical_vehicle WHERE name='Transport lezacy');
INSERT INTO medical_vehicle (name,category,capacity,quality,units_per_hour,price_per_hour,available) SELECT 'Karetka specjalna','INTENSIVE',5,5,25,280.00,true WHERE NOT EXISTS (SELECT 1 FROM medical_vehicle WHERE name='Karetka specjalna');
