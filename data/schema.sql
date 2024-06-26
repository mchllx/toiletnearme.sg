drop database if exists toiletnearme;

create database toiletnearme;

use toiletnearme;

create table toilets (
    toilet_id varchar(64) not null,
    name varchar(256),
    address varchar(256) not null,
    price decimal,
    gender varchar(64),
    type varchar(64),
    remarks varchar(1024),
    website varchar(1024),
    last_update timestamp default current_timestamp on update current_timestamp, 
    opening_hours_id varchar(64) not null,
    closing_hours_id varchar(64) not null,
    images varchar(256),
    region varchar(64),
 
    primary key(toilet_id)
);

create table opening_hours (
    opening_hours_id varchar(64) not null,
    sunday Date,
    monday Date,
    tuesday Date,
    wednesday Date,
    thursday Date,
    friday Date,
    saturday Date,

    primary key(opening_hours_id)
);

create table closing_hours (
    closing_hours_id varchar(64) not null,
    sunday Date,
    monday Date,
    tuesday Date,
    wednesday Date,
    thursday Date,
    friday Date,
    saturday Date,

    primary key(closing_hours_id)
);

create table amenities (
    toilet_id varchar(64) not null,
    amenities_id varchar(64) not null,
    babycare boolean not null,
    bidet boolean not null,
    dryer boolean not null,
    reserved boolean not null,
    locked boolean not null,
    unisex boolean not null,

    primary key(amenities_id),
    constraint fk_toilet_id foreign key(toilet_id) references toilets(toilet_id)
);

create table users (
    user_id varchar(64) not null,
    username varchar(64) not null,
    email varchar(64) not null,
    password varchar(256) not null,
    created_on timestamp default current_timestamp,
    last_update timestamp default current_timestamp on update current_timestamp,
    first_name varchar(64) not null,
    last_name varchar(64) not null,
    profile_image varchar(256),
    role varchar(64) not null,
    expired boolean,
    enabled boolean,
    locked boolean,
    credentials boolean,

    primary key(user_id)
);

create table bookmarks (
    bookmark_id int auto_increment,
    user_id varchar(64) not null,
    toilet_id varchar(64) not null,
    last_added timestamp default current_timestamp on update current_timestamp,

    primary key(bookmark_id),
    constraint fk_bk_user_id foreign key(user_id) references users(user_id), 
    constraint fk_bk_toilet_id foreign key(toilet_id) references toilets(toilet_id) 
);

create table reviews (
    review_id int auto_increment,
    user_id varchar(64) not null,
    toilet_id varchar(64) not null,
    created_on timestamp default current_timestamp,
    last_update timestamp default current_timestamp on update current_timestamp,
    header varchar(256),
    text varchar(1024),
    rating int,
    images varchar(256),

    primary key(review_id),
    constraint fk_rv_user_id foreign key(user_id) references users(user_id),
    constraint fk_rv_toilet_id foreign key(toilet_id) references toilets(toilet_id)
);

create table gsheets_hotel_toilets (
    gsheets_hotel_toilet_id varchar(64),
    last_update timestamp default current_timestamp on update current_timestamp,
    hotel varchar(128) not null,
    room varchar(256),
    review varchar(1024),
    website varchar(1024),
    address varchar(256) not null,

    primary key(gsheets_hotel_toilet_id)
);

create table gsheets_male_toilets (
    gsheets_male_toilet_id varchar(64),
    last_update timestamp default current_timestamp on update current_timestamp,
    region varchar(128),
    location varchar(128),
    remarks varchar(256),
    address varchar(256),

    primary key(gsheets_male_toilet_id)
); 

create table gsheets_female_toilets (
    gsheets_female_toilet_id varchar(64),
    last_update timestamp default current_timestamp on update current_timestamp,
    region varchar(128),
    location varchar(128),
    remarks varchar(256),
    address varchar(256),

    primary key(gsheets_female_toilet_id)
);

grant all privileges on toiletnearme.* to mich@'%';

flush privileges;