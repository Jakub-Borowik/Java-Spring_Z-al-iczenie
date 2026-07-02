insert into app_user (id, login, password, role)
values (1, 'client', '{noop}client', 'CLIENT')
on conflict (id) do nothing;

insert into app_user (id, login, password, role)
values (2, 'admin', '{noop}admin', 'ADMIN')
on conflict (id) do nothing;

insert into hall (id, name, rows_count, seats_per_row)
values (1, 'Sala 1', 4, 6)
on conflict (id) do nothing;

insert into seat (id, row_number, seat_number, hall_id) values
(1, 1, 1, 1), (2, 1, 2, 1), (3, 1, 3, 1), (4, 1, 4, 1), (5, 1, 5, 1), (6, 1, 6, 1),
(7, 2, 1, 1), (8, 2, 2, 1), (9, 2, 3, 1), (10, 2, 4, 1), (11, 2, 5, 1), (12, 2, 6, 1),
(13, 3, 1, 1), (14, 3, 2, 1), (15, 3, 3, 1), (16, 3, 4, 1), (17, 3, 5, 1), (18, 3, 6, 1),
(19, 4, 1, 1), (20, 4, 2, 1), (21, 4, 3, 1), (22, 4, 4, 1), (23, 4, 5, 1), (24, 4, 6, 1)
on conflict (id) do nothing;

insert into screening (id, movie_title, start_time, hall_id) values
(1, 'Inception', '2026-07-10 18:00:00', 1),
(2, 'Interstellar', '2026-07-10 20:30:00', 1),
(3, 'Avatar: Istota wody', '2026-07-11 17:15:00', 1),
(4, 'Diuna: Czesc druga', '2026-07-11 20:00:00', 1)
on conflict (id) do update
set movie_title = excluded.movie_title,
    start_time = excluded.start_time,
    hall_id = excluded.hall_id;

select setval(pg_get_serial_sequence('app_user', 'id'), (select max(id) from app_user));
select setval(pg_get_serial_sequence('hall', 'id'), (select max(id) from hall));
select setval(pg_get_serial_sequence('seat', 'id'), (select max(id) from seat));
select setval(pg_get_serial_sequence('screening', 'id'), (select max(id) from screening));
