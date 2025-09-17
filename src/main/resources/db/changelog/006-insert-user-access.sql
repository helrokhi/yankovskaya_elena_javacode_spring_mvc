--liquibase formatted sql
--preconditions onFail:HALT onError:HALT
--changeset Elena_Yankovskaya:006

INSERT INTO user_access
    (id, login, password, role, is_account_non_locked, created_at, updated_at)
VALUES
  ('11111111-1111-1111-1111-111111111111','alice@example.com','$2a$12$z84kuXZLf7ZmCUfb52OGAOjrVtonFURkI/mfewPzotiiijZz5LZ2m','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('22222222-2222-2222-2222-222222222222','bob@example.com','$2a$12$8URx26fUlWT5Iwz1tQiOReJ9122Coi6WhwOOSh8cgHIMzy5WE2EdW','USER', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('33333333-3333-3333-3333-333333333333','charlie@example.com','$2a$12$40cI0LGq7KFF1PUCM5XG0.JiIV8hNb0/TC/wveVUpoo7tZQMH4Peq','MODERATOR', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  ('44444444-4444-4444-4444-444444444444','diana@example.com','$2a$12$sTtehIYVq0fn657/UiMgTOCFaZ0prf2rrfVgyJlPl76clNmdc7wo6','SUPER_ADMIN', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT DO NOTHING;