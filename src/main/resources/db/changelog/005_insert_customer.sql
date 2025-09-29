--liquibase formatted sql
--preconditions onFail:HALT onError:HALT
--changeset Elena_Yankovskaya:005

INSERT INTO customers
    (id, first_name, last_name, email, password, contact_number, role, is_account_non_locked, created_at, updated_at)
VALUES
  ('11111111-1111-1111-1111-111111111111','Alice','Smith','alice@example.com','$2a$12$EXAMPLEHASH1','+10000000001','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('22222222-2222-2222-2222-222222222222','Bob','Johnson','bob@example.com','$2a$12$EXAMPLEHASH2','+10000000002','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('33333333-3333-3333-3333-333333333333','Charlie','Williams','charlie@example.com','$2a$12$EXAMPLEHASH3','+10000000003','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('44444444-4444-4444-4444-444444444444','Diana','Brown','diana@example.com','$2a$12$EXAMPLEHASH4','+10000000004','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
  ON CONFLICT DO NOTHING;
