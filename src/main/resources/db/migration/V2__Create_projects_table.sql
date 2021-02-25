create table projects
(
    id     serial primary key,
    name   varchar not null,
    status boolean not null default 'f'
);