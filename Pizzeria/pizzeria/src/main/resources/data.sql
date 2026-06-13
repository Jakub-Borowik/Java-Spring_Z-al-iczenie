-- Czyszczenie tabeli przed każdym odpaleniem.
TRUNCATE TABLE pizza_toppings CASCADE;
TRUNCATE TABLE pizza CASCADE;
TRUNCATE TABLE topping CASCADE;

-- DODANIE SKŁADNIKÓW
INSERT INTO topping (id, name, price) VALUES (1, 'Ser', 3.00);
INSERT INTO topping (id, name, price) VALUES (2, 'Pieczarki', 3.50);
INSERT INTO topping (id, name, price) VALUES (3, 'Szynka', 4.00);
INSERT INTO topping (id, name, price) VALUES (4, 'Salami', 4.50);
INSERT INTO topping (id, name, price) VALUES (5, 'Cebula', 2.00);
INSERT INTO topping (id, name, price) VALUES (6, 'Papryka Jalapeno', 3.50);
INSERT INTO topping (id, name, price) VALUES (7, 'Oliwki', 3.00);
INSERT INTO topping (id, name, price) VALUES (8, 'Kurczak', 5.00);
INSERT INTO topping (id, name, price) VALUES (9, 'Ananas', 4.00);

-- DODANIE GOTOWYCH PIZZ
INSERT INTO pizza (id, name, base_price) VALUES (1, 'Margherita', 20.00);
INSERT INTO pizza (id, name, base_price) VALUES (2, 'Capricciosa', 25.00);
INSERT INTO pizza (id, name, base_price) VALUES (3, 'Pepperoni', 24.00);
INSERT INTO pizza (id, name, base_price) VALUES (4, 'Hawajska', 26.00);
INSERT INTO pizza (id, name, base_price) VALUES (5, 'Diavola', 28.00);

-- ŁĄCZENIE PIZZ ZE SKŁADNIKAMI

-- Margherita (Ser)
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (1, 1);

-- Capricciosa (Ser, Pieczarki, Szynka)
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (2, 1);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (2, 2);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (2, 3);

-- Pepperoni (Ser, Salami)
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (3, 1);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (3, 4);

-- Hawajska (Ser, Szynka, Ananas)
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (4, 1);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (4, 3);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (4, 9);

-- Diavola (Ser, Salami, Cebula, Jalapeno)
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (5, 1);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (5, 4);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (5, 5);
INSERT INTO pizza_toppings (pizza_id, topping_id) VALUES (5, 6);

-- Wymuszenie na tworzeniu nowych pizz z kreatora od ID 50 w zwyż aby nie kolidować z podstawowymi
ALTER SEQUENCE pizza_id_seq RESTART WITH 50;
ALTER SEQUENCE topping_id_seq RESTART WITH 50;