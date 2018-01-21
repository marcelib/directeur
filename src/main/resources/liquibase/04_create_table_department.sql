CREATE TABLE department
(
  id              SERIAL       NOT NULL PRIMARY KEY,
  department_name VARCHAR(255) NOT NULL,
  director_id     INT          NOT NULL
);
