-- Companies
INSERT INTO companies (id, name, description, industry, address, created_at, updated_at, version) VALUES
  (1, 'Acme Corp', 'A leading provider of widgets', 'Manufacturing', '123 Main St', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
  (2, 'Globex Inc', 'Global exporter of goods', 'Logistics', '456 Elm St', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- Departments
INSERT INTO departments (id, name, description, location, company_id, created_at, updated_at, version) VALUES
  (1, 'Engineering', 'Handles product development', 'HQ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
  (2, 'Sales', 'Handles sales and customer relations', 'HQ', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
  (3, 'Logistics', 'Manages shipping and receiving', 'Warehouse', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- Employees (SINGLE_TABLE inheritance)
INSERT INTO employees (id, employee_type, first_name, last_name, email, phone, hire_date, salary, job_title, department_id, created_at, updated_at, version, annual_bonus, stock_options, health_insurance, retirement_plan, hours_per_week, flexible_schedule, remote_work, contract_end_date) VALUES
  (1, 'FULL_TIME', 'Alice', 'Smith', 'alice.smith@acme.com', '555-1234', '2022-01-10', 90000, 'Software Engineer', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 5000, 100, true, true, NULL, NULL, NULL, NULL),
  (2, 'FULL_TIME', 'Bob', 'Johnson', 'bob.johnson@acme.com', '555-2345', '2021-03-15', 95000, 'Sales Manager', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 7000, 50, true, true, NULL, NULL, NULL, NULL),
  (3, 'PART_TIME', 'Carol', 'Williams', 'carol.williams@globex.com', '555-3456', '2023-05-01', 40000, 'Warehouse Assistant', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, NULL, NULL, NULL, NULL, 20, true, false, '2024-12-31');