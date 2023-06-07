ALTER TABLE employees
    ADD COLUMN created_on TIMESTAMP NOT NULL default now();

ALTER TABLE employees
    ADD COLUMN created_by varchar default current_user;

ALTER TABLE employees
    ADD COLUMN updated_on TIMESTAMP;

ALTER TABLE employees
    ADD COLUMN updated_by varchar;
