CREATE TABLE IF NOT EXISTS orders
(
    id   SERIAL PRIMARY KEY,
    date DATE,
    cost NUMERIC
);

CREATE TABLE IF NOT EXISTS product
(
    id     SERIAL PRIMARY KEY,
    orders integer references orders (id),
    name   TEXT,
    cost   NUMERIC
);