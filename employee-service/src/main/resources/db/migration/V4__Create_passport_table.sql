CREATE TABLE IF NOT EXISTS employee_passports
(
    employee_id          uuid PRIMARY KEY NOT NULL,
    passport_number      bigint UNIQUE    NOT NULL,
    registration_address text             NOT NULL,
    CONSTRAINT fk_passports_employees
        FOREIGN KEY (employee_id)
            REFERENCES employees (employee_id)
);
