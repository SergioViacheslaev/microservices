INSERT INTO departments (department_id, department_name)
VALUES (100, 'Управление автоматизации'),
       (101, 'Департамент разработки ПО');

INSERT INTO employees (employee_id, employee_name, employee_surname, employee_birth_date)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1',
        'Ivan',
        'Ivanov',
        '2000-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2',
        'Petr',
        'Ivanov',
        '1987-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e3',
        'Oleg',
        'Ivanov',
        '1990-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e4',
        'Sergei',
        'Ivanov',
        '1998-09-11'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e5',
        'Sergei',
        'Ivanov',
        '2005-09-11');

INSERT INTO phones (employee_id, phone_number)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', '792112345671'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e1', '796598387472');

INSERT INTO passports (employee_id, passport_number, registration_address)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', 123456789, 'Улица Пушкина, дом Колотушкина');

INSERT INTO employees_departments (employee_id, department_id)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', 100),
       ('b16355a9-3edf-418d-bafc-52e46f6703e1', 101);
