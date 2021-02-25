create table project_accounts
(
    project_id serial not null
        constraint project_accounts_projects_id_fk
            references projects (id),
    account_id serial not null
        constraint project_accounts_accounts_id_fk
            references accounts (id)
)