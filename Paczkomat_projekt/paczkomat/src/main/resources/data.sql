INSERT INTO app_user (login, password, role)
SELECT 'courier', '{noop}courier', 'COURIER'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'courier');

INSERT INTO app_user (login, password, role)
SELECT 'client', '{noop}client', 'CLIENT'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'client');

INSERT INTO app_user (login, password, role)
SELECT 'client1', '{noop}client', 'CLIENT'
WHERE NOT EXISTS (SELECT 1 FROM app_user WHERE login = 'client');

INSERT INTO locker (code, size, occupied)
SELECT 'S1', 'SMALL', false
WHERE NOT EXISTS (SELECT 1 FROM locker WHERE code = 'S1');

INSERT INTO locker (code, size, occupied)
SELECT 'S2', 'SMALL', false
WHERE NOT EXISTS (SELECT 1 FROM locker WHERE code = 'S2');

INSERT INTO locker (code, size, occupied)
SELECT 'M1', 'MEDIUM', false
WHERE NOT EXISTS (SELECT 1 FROM locker WHERE code = 'M1');

INSERT INTO locker (code, size, occupied)
SELECT 'M2', 'MEDIUM', false
WHERE NOT EXISTS (SELECT 1 FROM locker WHERE code = 'M2');

INSERT INTO locker (code, size, occupied)
SELECT 'L1', 'LARGE', false
WHERE NOT EXISTS (SELECT 1 FROM locker WHERE code = 'L1');

INSERT INTO locker (code, size, occupied)
SELECT 'L2', 'LARGE', false
WHERE NOT EXISTS (SELECT 1 FROM locker WHERE code = 'L2');