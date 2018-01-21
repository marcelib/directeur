INSERT INTO role(id, name, is_admin, is_accountant, is_normal)
    VALUES (1, 'BLABLA',TRUE , FALSE , FALSE);
INSERT INTO department(id, department_name)
    VALUES (1, 'Zakon Feniksa');
INSERT INTO employee_position(id, position_name, min_salary, role_id, department_id)
    VALUES (10, 'Marceli', 9000, 1, 1),(20, 'Witalis', 5, 1, 1);