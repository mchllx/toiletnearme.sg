drop database if exists toilets;

create database toilets;

use toilets;

create table users (
    user_id int auto_increment,
    username varchar(64) not null,
    email varchar(64) not null,
    password varchar(256) not null,
    date timestamp default current_timestamp,
    name varchar(64) not null,
    profile_image varchar(256),

    primary key(user_id)
);

create table toilets (
    toilet_id varchar(64) not null,
    location varchar(64),
    address varchar(256) not null,
    price decimal,
    remarks varchar(256), 
    opening_hours Date,
    closing_hours Date,
    images varchar(256),
 
    primary key(toilet_id)
);

create table reviews (
    review_id int auto_increment,
    user_id int not null,
    toilet_id varchar(64) not null,
    created_on timestamp default current_timestamp,
    last_update timestamp default current_timestamp on update current_timestamp,
    text varchar(256),
    rating int not null,
    images varchar(256),

    primary key(review_id),
    constraint fk_id foreign key(user_id) references users(user_id),
    foreign key(toilet_id) references toilets(toilet_id)
);

grant all privileges on toilets.* to mich@'%';

flush privileges;