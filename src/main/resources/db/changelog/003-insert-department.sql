--liquibase formatted sql
--preconditions onFail:HALT onError:HALT
--changeset Elena_Yankovskaya:003

INSERT INTO departments (id, name) VALUES
    ('10000000-0000-0000-0000-000000000001', 'IT'),
    ('10000000-0000-0000-0000-000000000002', 'HR'),
    ('10000000-0000-0000-0000-000000000003', 'Finance'),
    ('10000000-0000-0000-0000-000000000004', 'Marketing'),
    ('10000000-0000-0000-0000-000000000005', 'Sales')
    ON CONFLICT DO NOTHING;