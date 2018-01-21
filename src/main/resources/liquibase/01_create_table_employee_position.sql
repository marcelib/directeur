CREATE TABLE employee_position
(
  id            SERIAL       NOT NULL PRIMARY KEY,
  position_name VARCHAR(255) NOT NULL,
  salary        INTEGER      NOT NULL,
  role_id       INTEGER      NOT NULL REFERENCES role (id),
  department_id INTEGER      NOT NULL REFERENCES department(id)
);