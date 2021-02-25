create table projects
(
    id     serial primary key,
    name   varchar not null unique,
    status boolean not null default 'f'
);