CREATE TABLE IF NOT EXISTS departments
(
    department_id   SERIAL PRIMARY KEY NOT NULL,
    department_name text UNIQUE        NOT NULL
);

CREATE TABLE IF NOT EXISTS employees_departments
(
    employee_id   uuid   NOT NULL,
    department_id bigint NOT NULL,
    primary key (employee_id, department_id),
    CONSTRAINT ed_employee_fk_id FOREIGN KEY (employee_id) REFERENCES employees (employee_id),
    CONSTRAINT ed_department_fk_id FOREIGN KEY (department_id) REFERENCES departments (department_id)
);


