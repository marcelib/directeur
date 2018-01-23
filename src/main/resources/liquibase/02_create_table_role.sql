CREATE TABLE role
(
  id            SERIAL PRIMARY KEY,
  name          VARCHAR(255),
  is_admin      BOOLEAN,
  is_accountant BOOLEAN,
  is_normal     BOOLEAN

);
