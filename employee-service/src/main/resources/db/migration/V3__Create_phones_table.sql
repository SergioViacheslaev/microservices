CREATE TABLE IF NOT EXISTS phones
(
    phone_id     SERIAL PRIMARY KEY NOT NULL,
    employee_id  uuid               NOT NULL,
    phone_number text UNIQUE        NOT NULL,
    CONSTRAINT fk_phones_employees FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
);
