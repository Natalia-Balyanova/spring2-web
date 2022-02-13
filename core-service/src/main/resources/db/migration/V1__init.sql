create table categories (
    id              bigserial primary key,
    title           varchar(255),
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);

insert into categories (title)
values
('Vegetables'),
('Drink'),
('Milk'),
('Fruit'),
('Sweet'),
('Grocery'),
('Fish');

create table products (
  id            bigserial primary key,
  title         varchar(255),
  price         int,
  category_id   bigint references categories (id),
  created_at    timestamp default current_timestamp,
  updated_at    timestamp default current_timestamp
);

insert into products (title, price, category_id)
values
('Bread', 100, 6),
('Apple', 100, 4),
('Milk', 70, 3),
('Eggs', 100, 6),
('Fish', 600, 7),
('Coffee', 500, 2),
('Tea', 300, 2),
('Butter', 200, 3),
('Butter Maxi', 450, 3),
('Banana', 70, 4),
('Orange juice', 100, 2),
('Cheese', 1200, 3),
('Tomato', 200, 1),
('Cucumber', 150, 1),
('Potato', 50, 1),
('Pasta', 100, 6),
('Dumplings', 700, 6),
('Corn', 70, 6),
('Cookie', 100, 5),
('Salmon', 1200, 7);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price int    not null,
    address     varchar(255),
    phone       varchar(255),
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

create table order_items
(
    id                bigserial primary key,
    product_id        bigint not null references products (id),
    order_id          bigint not null references orders (id),
    quantity          int    not null,
    price_per_product int    not null,
    price             int    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values  ('john', 200, 'address', '12345'),
        ('john', 300, 'address2', '12345'),
        ('john', 200, 'address3', '12345'),
        ('john', 300, 'address4', '12345'),
        ('john', 210, 'address5', '12345'),
        ('bob', 140, 'address_bob', '6789'),
        ('bob', 200, 'address_bob', '56789'),
        ('bob', 600, 'address_bob', '56789'),
        ('bob', 500, 'address_bob', '56789'),
        ('bob', 900, 'address_bob', '56789');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values  (1, 1, 2, 100, 200),
        (1, 2, 3, 100, 300),
        (2, 3, 2, 100, 200),
        (2, 4, 3, 100, 300),
        (3, 5, 3, 70, 210),
        (3, 6, 2, 70, 140),
        (4, 7, 2, 100, 200),
        (5, 8, 1, 600, 600),
        (6, 9, 1, 500, 500),
        (7, 10, 3, 300, 900);