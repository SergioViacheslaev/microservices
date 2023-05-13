CREATE TABLE IF NOT EXISTS employee
(
    employee_id   uuid PRIMARY KEY NOT NULL,
    employee_name varchar,
    employee_surname varchar,
    employee_birth_date date
);
