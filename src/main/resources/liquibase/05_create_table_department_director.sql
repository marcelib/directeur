CREATE TABLE department_director
(
  employee_id INTEGER REFERENCES employee,
  department_id INTEGER REFERENCES department
);
