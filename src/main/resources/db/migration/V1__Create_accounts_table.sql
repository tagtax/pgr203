create table accounts
(
    id    serial primary key,
    name  varchar(128) not null,
    email varchar(254) not null
);