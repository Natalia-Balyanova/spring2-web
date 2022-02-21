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
  price         numeric(8, 2) not null,
  category_id   bigint references categories (id),
  created_at    timestamp default current_timestamp,
  updated_at    timestamp default current_timestamp
);

insert into products (title, price, category_id)
values
('Bread', 99.99, 6),
('Apple', 99.99, 4),
('Milk', 79.99, 3),
('Eggs', 99.99, 6),
('Fish', 599.99, 7),
('Coffee', 499.99, 2),
('Tea', 299.99, 2),
('Butter', 199.99, 3),
('Butter Maxi', 449.99, 3),
('Banana', 69.99, 4),
('Orange juice', 99.99, 2),
('Cheese', 1199.99, 3),
('Tomato', 199.99, 1),
('Cucumber', 149.99, 1),
('Potato', 49.90, 1),
('Pasta', 99.90, 6),
('Dumplings', 699.90, 6),
('Corn', 69.90, 6),
('Cookie', 99.99, 5),
('Salmon', 1199.90, 7);

create table orders
(
    id          bigserial primary key,
    username    varchar(255) not null,
    total_price numeric(8, 2)  not null,
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
    price_per_product numeric(8, 2)    not null,
    price             numeric(8, 2)    not null,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp default current_timestamp
);

insert into orders (username, total_price, address, phone)
values ('john', 199.98, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 99.99, 199.98);