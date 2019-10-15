DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  phone                 VARCHAR(15) NOT NULL UNIQUE,
  password              VARCHAR(80),
  email                 VARCHAR(50) UNIQUE,
  first_name            VARCHAR(50),
  last_name             VARCHAR(50),
  PRIMARY KEY (id)
);

INSERT INTO users (phone, password, email, first_name, last_name)
VALUES
('+79998884444','$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com', 'Admin','Admin');

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    serial,
  name                  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN'), ('ROLE_CUSTOMER');

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
  user_id               INT NOT NULL,
  role_id               INT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id)
);

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(1, 3);

DROP TABLE IF EXISTS products;
CREATE TABLE products (
    id bigserial,
    title varchar(255) NOT NULL,
    price numeric(8,2) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO products (title, price) VALUES
('Смартфон Samsung Galaxy A50', 15800.0),
('Смартфон Samsung Galaxy A10', 8600.0),
('Смартфон Samsung Galaxy A30', 14800.0),
('Смартфон Apple iPhone Xr', 55000.0),
('Смартфон Apple iPhone 7', 30000.0),
('Смартфон Apple iPhone SE', 21000.0),
('Смартфон Apple iPhone X', 64000.0),
('Смартфон HUAWEI P30 lite', 20000.0),
('Смартфон HUAWEI P30', 40000.0),
('Смартфон HUAWEI Y7', 10000.0),
('Смартфон HUAWEI Nova 3', 22000.0),
('Смартфон Sony Xperia XZ2', 35000.0),
('Смартфон Sony Xperia XA2 Ultra', 10000.0),
('Смартфон Xiaomi Mi 9T', 20000.0),
('Смартфон Xiaomi Redmi Note 7', 14000.0),
('Наушники JBL T450BT', 2000.0),
('Наушники Sony WH-1000XM3', 28000.0);

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id bigserial,
    title varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO categories (title) VALUES
('Electronics'),
('Smartphones'),
('Phones'),
('Headphones');

DROP TABLE IF EXISTS products_categories;
CREATE TABLE products_categories (
  product_id               INT NOT NULL,
  category_id               INT NOT NULL,
  PRIMARY KEY (product_id, category_id),
  FOREIGN KEY (product_id) REFERENCES products (id),
  FOREIGN KEY (category_id) REFERENCES categories (id)
);

INSERT INTO products_categories (product_id, category_id)
VALUES
(1, 1),(2, 1),(3, 1),(4, 1),(5, 1),
(6, 1),(7, 1),(8, 1),(9, 1),(10, 1),
(11, 1),(12, 1),(13, 1),(14, 1),(15, 1),
(1, 2),(2, 2),(3, 2),(4, 2),(5, 2),
(6, 2),(7, 2),(8, 2),(9, 2),(10, 2),
(11, 2),(12, 2),(13, 2),(14, 2),(15, 2),
(1, 3),(2, 3),(3, 3),(4, 3),(5, 3),
(6, 3),(7, 3),(8, 3),(9, 3),(10, 3),
(11, 3),(12, 3),(13, 3),(14, 3),(15, 3),
(16, 1),(17, 1),(16, 4),(17, 4);

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    id bigserial PRIMARY KEY,
    user_id bigint,
    price numeric(8, 2) NOT NULL,
    phone varchar(15) NOT NULL,
    address varchar(255),
    status varchar(255) NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

DROP TABLE IF EXISTS order_items;
CREATE  TABLE order_items (
    id bigserial PRIMARY KEY,
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    quantity int NOT NULL,
    item_price numeric(8, 2) NOT NULL,
    total_price numeric(8, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

DROP TABLE IF EXISTS products_images;
CREATE TABLE products_images (
    id bigserial PRIMARY KEY,
    product_id bigint, path varchar(255),
    FOREIGN KEY (product_id) REFERENCES products(id));

INSERT INTO products_images (product_id, path) VALUES
(1, 'img_1.jpg'),
(2, 'img_1.jpg'),
(3, 'img_1.jpg'),
(4, 'img_1.jpg'),
(5, 'img_1.jpg'),
(6, 'img_1.jpg'),
(7, 'img_1.jpg'),
(8, 'img_1.jpg'),
(9, 'img_1.jpg'),
(10, 'img_1.jpg'),
(11, 'img_1.jpg'),
(12, 'img_1.jpg'),
(12, 'img_1.jpg'),
(13, 'img_1.jpg'),
(14, 'img_1.jpg'),
(15, 'img_1.jpg'),
(16, 'img_1.jpg'),
(17, 'img_1.jpg');