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
values ('john', 200, 'address', '12345');

insert into order_items (product_id, order_id, quantity, price_per_product, price)
values (1, 1, 2, 100, 200);