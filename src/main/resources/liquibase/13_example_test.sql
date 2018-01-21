INSERT INTO role(id, is_admin, is_accountant, is_normal)
    VALUES (1, TRUE , FALSE , FALSE);
INSERT INTO department(id, department_name, director_id)
    VALUES (1, 'Zakon Feniksa', 1);
INSERT INTO employee_position(id, position_name, salary, role_id, department_id)
    VALUES (10, 'Marceli', 9000, 1, 1),(20, 'Witalis', 5, 2, 2);