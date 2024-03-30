drop database if exists toilets;

create database toilets;

use toilets;

create table users (
    id int auto_increment,
    username varchar(64) not null,
    email varchar(64) not null,
    password varchar(256) not null,
    date timestamp default current_timestamp,
    name varchar(64) not null,
    image varchar(256),
    r_id varchar(64) not null,

    primary key(u_id)
);

-- create table reviews (
--     id varchar(64) not null,
--     t_id varchar(64) not null,
--     created_on timestamp default current_timestamp,
--     last_update timestamp default current_timestamp on update current_timestamp,
--     text varchar(256),
--     rating int not null

--     primary key(id),
--     constraint fk_id foreign key(id) references users(r_id)
-- );

create table favourites (
    id int auto_increment,
    t_id varchar(64) not null,
    u_id int,
    name varchar(64) not null,
    image varchar (256),
    ratings int,

    primary key(id),
    constraint fk_u_id foreign key (id) references users(id) 
)

grant all privileges on toilets.* to mich@'%';

flush privileges;