CREATE TABLE IF NOT EXISTS transactions (
  id SERIAL PRIMARY KEY,
  type INT,
  date DATE,
  amount DECIMAL,
  cpf BIGINT,
  card VARCHAR(20),
  time TIME,
  store_owner VARCHAR(150),
  store_name VARCHAR(150)
);
