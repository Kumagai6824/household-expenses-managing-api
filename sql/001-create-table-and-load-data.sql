DROP TABLE IF EXISTS income;
DROP TABLE IF EXISTS expense;


CREATE TABLE income (
  id int AUTO_INCREMENT,
  type enum('PROJECTED', 'ACTUAL'),
  category VARCHAR(50) NOT NULL,
  amount DECIMAL(10,2),
  used_date DATE,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO income (id, type, category, amount , used_date, created_at, updated_at ) VALUES (1, "actual", "salary", 5000, '2024-1-10', null, null);

CREATE TABLE expense (
  id int AUTO_INCREMENT,
  type enum('PROJECTED', 'ACTUAL'),
  category VARCHAR(50) NOT NULL,
  amount DECIMAL(10,2),
  used_date DATE,
  created_at TIMESTAMP,
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO expense (id, type, category, amount , used_date, created_at, updated_at ) VALUES (1, "actual", "rent", 3000, '2024-1-10', null, null);
