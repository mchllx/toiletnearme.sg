drop database if exists toiletnearme;

create database toiletnearme;

use toiletnearme;

create table users (
    user_id int auto_increment,
    username varchar(64) not null,
    email varchar(64) not null,
    password varchar(256) not null,
    created_on timestamp default current_timestamp,
    first_name varchar(64) not null,
    last_name varchar(64) not null,
    profile_image varchar(256),
    role varchar(64) not null,

    primary key(user_id)
);

create table amenities (
    amenities_id varchar(64) not null,
    babycare boolean not null,
    bidet boolean not null,
    dryer boolean not null,
    reserved boolean not null,

    primary key(amenities_id)
);

create table female_toilets (
    toilet_id varchar(64) not null,
    name varchar(64),
    address varchar(256) not null,
    price decimal,
    remarks varchar(256), 
    opening_hours Date,
    closing_hours Date,
    images varchar(256),
    amenities_id varchar(64) not null,
 
    primary key(toilet_id),
    foreign key(amenities_id) references amenities(amenities_id)
    
);

create table male_toilets (
    toilet_id varchar(64) not null,
    name varchar(64),
    address varchar(256) not null,
    price decimal,
    remarks varchar(256), 
    opening_hours Date,
    closing_hours Date,
    images varchar(256),
    amenities_id varchar(64) not null,
 
    primary key(toilet_id),
    foreign key(amenities_id) references amenities(amenities_id)
    
);

create table reviews (
    review_id int auto_increment,
    user_id int not null,
    toilet_id varchar(64) not null,
    created_on timestamp default current_timestamp,
    last_update timestamp default current_timestamp on update current_timestamp,
    header varchar(256),
    text varchar(256),
    rating int not null,
    images varchar(256),
    amenities_id varchar(64) not null,

    primary key(review_id),
    constraint fk_id foreign key(user_id) references users(user_id),
    foreign key(toilet_id) references toilets(toilet_id),
    foreign key(amenities_id) references amenities(amenities_id)

);

grant all privileges on toilets.* to mich@'%';

flush privileges;