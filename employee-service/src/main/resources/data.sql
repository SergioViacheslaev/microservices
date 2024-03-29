INSERT INTO departments (department_id, department_name)
VALUES ('0ff29382-4537-42b8-bdac-81a229ba0bd1', 'Управление автоматизации'),
       ('0ff29382-4537-42b8-bdac-81a229ba0bd2', 'Департамент разработки ПО');

INSERT INTO employees (employee_id, employee_name, employee_surname, employee_birth_date, monthly_salary,
                       payroll_account)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1',
        'Ivan',
        'Ivanov',
        '2000-09-11',
        100000.100,
        '1234 1234567891'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2',
        'Petr',
        'Ivanov',
        '1987-09-11',
        '100000.200',
        '1234 1234567892'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e3',
        'Oleg',
        'Ivanov',
        '1990-09-11',
        '100000',
        '1234 1234567893'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e4',
        'Sergei',
        'Ivanov',
        '1998-09-11',
        '200000',
        '1234 1234567894'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e5',
        'Sergei',
        'Ivanov',
        '2005-09-11',
        '500000',
        '1234 1234567895');

INSERT INTO phones (employee_id, phone_number, phone_type)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', '792112345671', 'WORK'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e1', '796598387472', 'HOME'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2', '796598387432', 'HOME'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e3', '796598387433', 'HOME'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e4', '796598387434', 'HOME'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e5', '796598387435', 'HOME');

INSERT INTO passports (employee_id, passport_number, registration_address)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', 1234567891, 'Улица Пушкина, дом Колотушкина 1'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2', 1234567892, 'Улица Пушкина, дом Колотушкина 2'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e3', 1234567893, 'Улица Пушкина, дом Колотушкина 3'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e4', 1234567894, 'Улица Пушкина, дом Колотушкина 4'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e5', 1234567895, 'Улица Пушкина, дом Колотушкина 5');

INSERT INTO employees_departments (employee_id, department_id)
VALUES ('b16355a9-3edf-418d-bafc-52e46f6703e1', '0ff29382-4537-42b8-bdac-81a229ba0bd1'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e1', '0ff29382-4537-42b8-bdac-81a229ba0bd2'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2', '0ff29382-4537-42b8-bdac-81a229ba0bd1'),
       ('b16355a9-3edf-418d-bafc-52e46f6703e2', '0ff29382-4537-42b8-bdac-81a229ba0bd2');
