CREATE VIEW department_statistics AS
  SELECT
    d2.department_name          AS name,
    COALESCE(sum(em.salary), 0) AS expenses,
    count(*)                    AS count
  FROM employee em
    JOIN employee_position e ON em.position_id = e.id
    JOIN department d2 ON e.department_id = d2.id
  GROUP BY d2.department_name;