CREATE TABLE employee_position
(
  id            SERIAL       NOT NULL PRIMARY KEY,
  position_name VARCHAR(255) NOT NULL,
  min_salary    INTEGER      NOT NULL,
  role_id       INTEGER      NOT NULL REFERENCES role,
  department_id INTEGER      NOT NULL REFERENCES department
);