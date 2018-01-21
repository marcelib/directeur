CREATE TABLE employee
(
  id          SERIAL       NOT NULL PRIMARY KEY ,
  name        VARCHAR(255) NOT NULL,
  surname     VARCHAR(255),
  email       VARCHAR(255),
  position_id INTEGER,
  FOREIGN KEY (position_id) REFERENCES employee_position
);
COMMENT ON TABLE employee IS 'A table to contain all employees';