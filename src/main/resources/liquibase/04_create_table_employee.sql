CREATE TABLE employee
(
  id          SERIAL       NOT NULL PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  surname     VARCHAR(255),
  email       VARCHAR(255),
  position_id INTEGER REFERENCES employee_position,
  salary      INTEGER NOT NULL
);
COMMENT ON TABLE employee IS 'A table to contain all employees';