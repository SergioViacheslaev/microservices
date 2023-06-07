CREATE TABLE IF NOT EXISTS employee_phones
(
    phone_id     SERIAL PRIMARY KEY NOT NULL,
    employee_id  uuid               NOT NULL,
    phone_number text               NOT NULL,
    CONSTRAINT fk_employee_id
        FOREIGN KEY (employee_id)
            REFERENCES employees (employee_id)
);
