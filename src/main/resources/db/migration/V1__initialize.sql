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
('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_CUSTOMER');

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

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
    id bigserial,
    title varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO categories (title) VALUES
('phones'),
('headphones');

DROP TABLE IF EXISTS products_images;
CREATE TABLE products_images (
    id bigserial PRIMARY KEY,
    path varchar(255)
);

INSERT INTO products_images (path) VALUES
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg'),
('img_1.jpg');

DROP TABLE IF EXISTS products;
CREATE TABLE products (
    id bigserial,
    title varchar(255) NOT NULL,
    price numeric(8,2) NOT NULL,
    image_id bigint NOT NULL,
    category_id bigint NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (image_id) REFERENCES products_images (id)
);

INSERT INTO products (title, price, image_id, category_id) VALUES
('Смартфон Samsung Galaxy A50', 15800.0, 1, 1),
('Смартфон Samsung Galaxy A10', 8600.0, 2, 1),
('Смартфон Samsung Galaxy A30', 14800.0, 3, 1),
('Смартфон Apple iPhone Xr', 55000.0, 4, 1),
('Смартфон Apple iPhone 7', 30000.0, 5, 1),
('Смартфон Apple iPhone SE', 21000.0, 6, 1),
('Смартфон Apple iPhone X', 64000.0, 7, 1),
('Смартфон HUAWEI P30 lite', 20000.0, 8, 1),
('Смартфон HUAWEI P30', 40000.0, 9, 1),
('Смартфон HUAWEI Y7', 10000.0, 10, 1),
('Смартфон HUAWEI Nova 3', 22000.0, 11, 1),
('Смартфон Sony Xperia XZ2', 35000.0, 12, 1),
('Смартфон Sony Xperia XA2 Ultra', 10000.0, 13, 1),
('Смартфон Xiaomi Mi 9T', 20000.0, 14, 1),
('Смартфон Xiaomi Redmi Note 7', 14000.0, 15, 1),
('Наушники JBL T450BT', 2000.0, 16, 2),
('Наушники Sony WH-1000XM3', 28000.0, 17, 2);

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

