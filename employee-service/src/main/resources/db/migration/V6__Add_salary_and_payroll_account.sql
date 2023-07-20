ALTER TABLE employees
    ADD COLUMN monthly_salary NUMERIC(10,3) NOT NULL DEFAULT '0';

ALTER TABLE employees
    ADD COLUMN payroll_account VARCHAR NOT NULL DEFAULT '';


