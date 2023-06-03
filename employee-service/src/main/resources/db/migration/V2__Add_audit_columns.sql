ALTER TABLE employee
    ADD COLUMN created_on TIMESTAMP NOT NULL default now();

ALTER TABLE employee
    ADD COLUMN created_by varchar default current_user;

ALTER TABLE employee
    ADD COLUMN updated_on TIMESTAMP;

ALTER TABLE employee
    ADD COLUMN updated_by varchar;
