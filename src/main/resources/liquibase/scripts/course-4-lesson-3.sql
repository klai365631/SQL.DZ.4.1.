-- liquibase formatted sql

--changeset klimov:1
CREATE INDEX idx_students_name ON students (name);

--changeset klimov:2
CREATE UNIQUE INDEX idx_faculties_name_color ON faculties (name, color);