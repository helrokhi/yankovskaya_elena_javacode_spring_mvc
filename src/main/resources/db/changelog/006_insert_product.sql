--liquibase formatted sql
--preconditions onFail:HALT onError:HALT
--changeset Elena_Yankovskaya:006

INSERT INTO products (id, name, description, price, quantity_stock) VALUES
  ('10000000-0000-0000-0000-000000000001','Laptop','Gaming Laptop',1200.00,50),
  ('10000000-0000-0000-0000-000000000002','Smartphone','Android Phone',800.00,100),
  ('10000000-0000-0000-0000-000000000003','Headphones','Noise-cancelling',150.00,200),
  ('10000000-0000-0000-0000-000000000004','Keyboard','Mechanical keyboard',90.00,150),
  ('10000000-0000-0000-0000-000000000005','Mouse','Wireless mouse',60.00,180),
  ('10000000-0000-0000-0000-000000000006','Monitor','27-inch 4K',400.00,70),
  ('10000000-0000-0000-0000-000000000007','Printer','Laser printer',200.00,30),
  ('10000000-0000-0000-0000-000000000008','Tablet','10-inch tablet',350.00,90),
  ('10000000-0000-0000-0000-000000000009','Camera','DSLR camera',900.00,25),
  ('10000000-0000-0000-0000-000000000010','Speaker','Bluetooth speaker',120.00,120)
ON CONFLICT DO NOTHING;