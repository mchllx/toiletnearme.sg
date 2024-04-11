package vttp.batch4.csf.toiletnearme.repositories;

import java.util.Date;
import java.util.List;

import vttp.batch4.csf.toiletnearme.models.Bookmark;
import vttp.batch4.csf.toiletnearme.models.Review;

public class SQLQueries {

    // 5 values per row 
    public static final String SQL_INSERT_REVIEW = """
        insert into reviews(
        username, email, password, created_on, first_name
        , last_name, profile_image, role)
        values (?, ?, ?, ?, ?, ?, ?, ?)
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
        , type, remarks, opening_hours, closing_hours, images
        , region, amenities_id)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
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

    // toilet_id not autoincr, str
    public static final String SQL_INSERT_TOILET = """
        insert into toilets(
        toilet_id, name, address, price, gender
        , type, remarks, opening_hours, closing_hours, images
        , region, amenities_id)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_MALE_BY_ID = """
        select 
        gsheets_male_toilet_id, last_update, region, location, remarks
        , address
        from gsheets_male_toilets
        where gsheets_male_toilet_id like ?
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_FEMALE_BY_ID = """
        select 
        gsheets_female_toilet_id, last_update, region, location, remarks
        , address
        from gsheets_female_toilets
        where gsheets_female_toilet_id like ?
        """;

    public static final String SQL_SELECT_GSHEETS_TOILET_HOTEL_BY_ID = """
        select 
        gsheets_hotel_toilet_id, last_update, hotel, room, review
        , website, address
        from gsheets_hotel_toilets
        where gsheets_hotel_toilet_id like ?
        """;

    public static final String SQL_SELECT_TOILET_BY_ID = """
        select 
        toilet_id, name, address, price, gender
        , type, remarks, opening_hours, closing_hours, images
        , region, amenities_id
        from toilets
        where toilet_id like ?
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
}
