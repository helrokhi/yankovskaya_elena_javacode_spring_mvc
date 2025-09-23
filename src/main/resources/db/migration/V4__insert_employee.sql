INSERT INTO employees (id, first_name, last_name, position, salary, department_id) VALUES
    -- IT
    (uuid_generate_v4(), 'Alice', 'Johnson', 'Developer', 175000.00, '10000000-0000-0000-0000-000000000001'),
    (uuid_generate_v4(), 'Bob', 'Smith', 'QA Engineer', 165000.00, '10000000-0000-0000-0000-000000000001'),
    (uuid_generate_v4(), 'Charlie', 'Brown', 'DevOps Engineer', 180000.00, '10000000-0000-0000-0000-000000000001'),

    -- HR
    (uuid_generate_v4(), 'Diana', 'White', 'HR Manager', 160000.00, '10000000-0000-0000-0000-000000000002'),
    (uuid_generate_v4(), 'Evan', 'Black', 'Recruiter', 150000.00, '10000000-0000-0000-0000-000000000002'),
    (uuid_generate_v4(), 'Fiona', 'Green', 'HR Specialist', 155000.00, '10000000-0000-0000-0000-000000000002'),

    -- Finance
    (uuid_generate_v4(), 'George', 'Adams', 'Financial Analyst', 170000.00, '10000000-0000-0000-0000-000000000003'),
    (uuid_generate_v4(), 'Hannah', 'Scott', 'Accountant', 168000.00, '10000000-0000-0000-0000-000000000003'),
    (uuid_generate_v4(), 'Ian', 'Davis', 'Chief Accountant', 190000.00, '10000000-0000-0000-0000-000000000003'),

    -- Marketing
    (uuid_generate_v4(), 'Jane', 'Taylor', 'Marketing Manager', 172000.00, '10000000-0000-0000-0000-000000000004'),
    (uuid_generate_v4(), 'Kevin', 'Moore', 'SEO Specialist', 158000.00, '10000000-0000-0000-0000-000000000004'),
    (uuid_generate_v4(), 'Laura', 'Miller', 'Content Manager', 160000.00, '10000000-0000-0000-0000-000000000004'),

    -- Sales
    (uuid_generate_v4(), 'Mike', 'Wilson', 'Sales Manager', 175000.00, '10000000-0000-0000-0000-000000000005'),
    (uuid_generate_v4(), 'Nina', 'Clark', 'Account Executive', 165000.00, '10000000-0000-0000-0000-000000000005'),
    (uuid_generate_v4(), 'Oscar', 'Lewis', 'Sales Representative', 155000.00, '10000000-0000-0000-0000-000000000005');