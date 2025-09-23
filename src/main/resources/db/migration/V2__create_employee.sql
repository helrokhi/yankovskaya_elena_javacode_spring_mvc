CREATE TABLE employees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    position VARCHAR(255),
    salary NUMERIC(15,2) NOT NULL CHECK (salary >= 0),
    department_id UUID NOT NULL,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
        REFERENCES departments (id)
        ON DELETE CASCADE
);