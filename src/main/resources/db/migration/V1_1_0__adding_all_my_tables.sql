create table public.clients
(
    id            bigserial
        primary key,
    email_address varchar(255)
        unique,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    phone_number  varchar(255) not null
        unique
);

alter table public.clients
    owner to postgres;

create table public.orders
(
    id              serial
        primary key
        unique,
    count_of_person bigint               not null,
    event           varchar(255)         not null,
    general_cost    bigint,
    reserved_date   timestamp(6)         not null,
    client_id       integer
        constraint fkk1ri6s69qqq84rdxoc8556310
            references public.clients
            on update cascade on delete cascade,
    flag_is_paid    boolean default true not null
);

alter table public.orders
    owner to postgres;

create table public.ordered_menu
(
    id        integer not null
        primary key,
    orders_id integer
        constraint fka91r598y53r1qxed6tce87rl7
            references public.orders
);

alter table public.ordered_menu
    owner to postgres;

create table public.security_credentials_clients
(
    id              integer not null
        primary key,
    client_id       integer,
    client_login    varchar(255),
    client_password varchar(255),
    client_role     varchar(255)
);

alter table public.security_credentials_clients
    owner to postgres;

create table public.dishes
(
    id             bigserial
        constraint dish_pk
            primary key,
    name           varchar not null,
    weight         bigint  not null,
    cost           bigint  not null,
    type_of_dishes varchar not null
);

alter table public.dishes
    owner to postgres;

create table public.ordered_menu_dishes
(
    ordered_menu_id integer not null,
    dishes_id       integer not null
);

alter table public.ordered_menu_dishes
    owner to postgres;

