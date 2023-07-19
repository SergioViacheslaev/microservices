INSERT INTO departments (department_id, department_name)
VALUES (100, 'Управление автоматизации'),
       (101, 'Департамент разработки ПО');

INSERT INTO employees (employee_id, employee_name, employee_surname, employee_birth_date)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1',
        'Ivan',
        'Testov',
        '2000-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2',
        'Petr',
        'Testov',
        '1987-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e3',
        'Oleg',
        'Testov',
        '1990-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e4',
        'Sergei',
        'Testov',
        '1998-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e5',
        'Dmitriy',
        'Testov',
        '2005-09-11');

INSERT INTO phones (employee_id, phone_number, phone_type)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', '792112345671', 'WORK'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e1', '796598387472', 'HOME');

INSERT INTO passports (employee_id, passport_number, registration_address)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', 1234567891, 'Улица Пушкина, дом Колотушкина');

INSERT INTO employees_departments (employee_id, department_id)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', 100),
       ('b16355a9-3edf-418d-bafc-52e46f6703e1', 101);
