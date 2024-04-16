package vttp.batch4.csf.toiletnearme.repositories;

import java.util.Date;
import java.util.List;

import vttp.batch4.csf.toiletnearme.models.Bookmark;
import vttp.batch4.csf.toiletnearme.models.Review;

public class SQLQueries {

    // 5 values per row
    // opening_hours_id, sunday, monday, tuesday, wednesday, thursday, friday, saturday
    public static final String SQL_INSERT_OPENING_HOURS = """
        insert into opening_hours(
        opening_hours_id, sunday, monday, tuesday, wednesday
        ,thursday ,friday, saturday)
        values (?, ?, ?, ?, ?, ?, ?)
        """;
    
    public static final String SQL_INSERT_CLOSING_HOURS = """
        insert into closing_hours(
        closing_hours_id, sunday, monday, tuesday, wednesday
        ,thursday , friday, saturday)
        values (?, ?, ?, ?, ?, ?, ?)
        """;

    // review_id auto incr user_id, toilet_id, created_on, last_update, header, text, rating, images
    public static final String SQL_INSERT_REVIEW = """
        insert into reviews(
        user_id, toilet_id, name, created_on, last_update
        , header , text, rating, images)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_DELETE_TOILET_BY_ID = """
        delete 
        toilet_id, name, address, price, gender
        , type, remarks, opening_hours, closing_hours, images
        , region, amenities_id 
        from toilets
        where toilet_id like ?
        """;

    public static final String SQL_UPDATE_TOILET = """
        replace into toilets(
        toilet_id, name, address, price, gender
        ,type , remarks, website, last_update, opening_hours_id
        ,closing_hours_id , images, region)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_SELECT_TOILET_BY_ID = """
        select 
        toilet_id, name, address, price, gender
        ,type , remarks, website, last_update, opening_hours_id
        ,closing_hours_id , images, region
        from toilets
        where toilet_id like ?
        """;
    
    public static final String SQL_SELECT_TOILET_ADDRESS_BY_REGION = """
        select 
        address, region
        from toilets
        where region like ?
        """;

    public static final String SQL_SELECT_TOILET_ADDRESS = """
        select 
        address
        from toilets
        """;
    
    public static final String SQL_SELECT_TOILET = """
        select 
        toilet_id, name, address, price, gender
        , type, remarks, website, last_update, opening_hours_id
        , closing_hours_id, images, region
        from toilets
        """;
       
    public static final String SQL_SELECT_REVIEW = """
        select 
        user_id, toilet_id, name, created_on, last_update
        , header , text, rating, images
        from reviews
        """;

    public static final String SQL_SELECT_REVIEW_BY_ID_TOILET = """
        select 
        user_id, toilet_id, name, created_on, last_update
        , header , text, rating, images
        from reviews
        where toilet_id like ?
        """;

    public static final String SQL_SELECT_REVIEW_BY_ID_USER = """
        select 
        user_id, toilet_id, name, created_on, last_update
        , header , text, rating, images
        from reviews
        where user_id like ?
        """;

    public static final String SQL_DELETE_REVIEW_BY_ID_USER = """
        delete 
        user_id, toilet_id, name, created_on, last_update
        , header , text, rating, images
        from reviews
        where user_id like ?
        """;

     // toilet_id not autoincr, str
     // toilet_id, name, address, price, gender
    // , type, remarks, website, last_update, opening_hours_id
    // , closing_hours_id, images, region
    public static final String SQL_INSERT_TOILET = """
        insert into toilets(
        toilet_id, name, address, price, gender
        , type, remarks, website, last_update, opening_hours_id
        , closing_hours_id, images, region)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_GSHEETS_TOILET_MALE = """
        insert into gsheets_male_toilets(
        gsheets_male_toilet_id, last_update, region, location, remarks
        , address)
        values (?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_GSHEETS_TOILET_FEMALE = """
        insert into gsheets_female_toilets(
        gsheets_female_toilet_id, last_update, region, location, remarks
        , address)
        values (?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_GSHEETS_TOILET_HOTEL = """
        insert into gsheets_hotel_toilets(
        gsheets_hotel_toilet_id, last_update, hotel, room, review
        , website, address)
        values (?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_MALE_BY_ID = """
        select 
        gsheets_male_toilet_id, last_update, region, location, remarks
        , address
        from gsheets_male_toilets
        where gsheets_male_toilet_id like ?
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_MALE = """
        select 
        gsheets_male_toilet_id, last_update, region, location, remarks
        , address
        from gsheets_male_toilets
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_FEMALE_BY_ID = """
        select 
        gsheets_female_toilet_id, last_update, region, location, remarks
        , address
        from gsheets_female_toilets
        where gsheets_female_toilet_id like ?
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_FEMALE = """
        select 
        gsheets_female_toilet_id, last_update, region, location, remarks
        , address
        from gsheets_female_toilets
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_HOTEL_REVIEW = """
        select 
        gsheets_hotel_toilet_id, review
        from gsheets_hotel_toilets
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_HOTEL_BY_ID = """
        select 
        gsheets_hotel_toilet_id, last_update, hotel, room, review
        , website, address
        from gsheets_hotel_toilets
        where gsheets_hotel_toilet_id like ?
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_HOTEL = """
        select 
        gsheets_hotel_toilet_id, last_update, hotel, room, review
        , website, address
        from gsheets_hotel_toilets
        """;


    public static final String SQL_UPDATE_USER_ROLE_BY_EMAIL = """
        UPDATE users
        SET role = ?
        WHERE email = ?
        """;

    public static final String SQL_UPDATE_USER = """
        replace into users(
        user_id, username, email, password, created_on
        , last_update, first_name, last_name, profile_image, role
        , expired, enabled, locked, credentials)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    // user_id auto incrs
    public static final String SQL_INSERT_USER = """
        insert into users(
        user_id, username, email, password, created_on
        , last_update, first_name, last_name, profile_image, role
        , expired, enabled, locked, credentials)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_SELECT_USER_BY_EMAIL = """
        select 
        user_id, username, email, password, created_on
        , last_update, first_name, last_name, profile_image, role
        , expired, enabled, locked, credentials
        from users
        where email like ?
        """;

    public static final String SQL_SELECT_USER_BY_ID = """
        select 
        user_id, username, email, password, created_on
        , last_update, first_name, last_name, profile_image, role
        , expired, enabled, locked, credentials
        from users
        where user_id like ?
        """;

    public static final String SQL_SELECT_USER = """
        select 
        user_id, username, email, password, created_on
        , last_update, first_name, last_name, profile_image, role
        , expired, enabled, locked, credentials
        from users
        """;
}
