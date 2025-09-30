--liquibase formatted sql
--preconditions onFail:HALT onError:HALT
--changeset Elena_Yankovskaya:006

INSERT INTO user_access
    (id, login, password, role, is_account_non_locked, created_at, updated_at)
VALUES
  ('11111111-1111-1111-1111-111111111111','alice@example.com','$2a$12$EXAMPLEHASH1','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('22222222-2222-2222-2222-222222222222','bob@example.com','$2a$12$EXAMPLEHASH2','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('33333333-3333-3333-3333-333333333333','charlie@example.com','$2a$12$EXAMPLEHASH3','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('44444444-4444-4444-4444-444444444444','diana@example.com','$2a$12$EXAMPLEHASH4','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;