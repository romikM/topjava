DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
('2020-08-12 09:30:43', 'Завтрак', 500, 100001),
('2020-08-12 13:00:00', 'Обед', 1000, 100001),
('2020-08-12 19:10:11', 'Ужин', 500, 100001),
('2020-08-12 00:00:00', 'Еда на граничное значение', 100, 100001),
('2020-08-13 09:00:00', 'Завтрак', 1000, 100001),
('2020-08-13 12:30:33', 'Обед', 500, 100001),
('2020-08-13 18:30:42', 'Ужин', 410, 100001),
('2020-08-13 13:35:00', 'Обед админа', 500, 100002),
('2020-08-13 19:42:42', 'Ужин админа', 410, 100002);