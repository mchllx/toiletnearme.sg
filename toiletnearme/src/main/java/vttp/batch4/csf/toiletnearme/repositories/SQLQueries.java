package vttp.batch4.csf.toiletnearme.repositories;

public class SQLQueries {


    public static final String SQL_INSERT_REVIEW = """
        insert into reviews(username, email, password, created_on, first_name, last_name, profile_image, role)
        values (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_GSHEETS_TOILET_MALE = """
        insert into gsheets_male_toilets(gsheets_male_toilet_id, last_update, region, location, remarks, address)
        values (?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_GSHEETS_TOILET_FEMALE = """
        insert into gsheets_female_toilets(gsheets_female_toilet_id, last_update, region, location, remarks, address)
        values (?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_GSHEETS_TOILET_HOTEL = """
        insert into gsheets_hotel_toilets(gsheets_hotel_toilet_id, last_update, hotel, room, review, website, address)
        values (?, ?, ?, ?, ?, ?, ?)
        """;

    // toilet_id not autoincr, str
    public static final String SQL_INSERT_TOILET = """
        insert into toilets(toilet_id, name, address, price, gender, type, remarks, opening_hours, closing_hours
            , images, region, amenities_id)
        values (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    // user_id auto incrs
    public static final String SQL_INSERT_USER = """
        insert into users(username, email, password, created_on, first_name, last_name, profile_image, role)
        values (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_SELECT_USER_BY_EMAIL = """
        select user_id, username, email, password, created_on, first_name, last_name, profile_image, role
        from users
        where email like ?
        """;

}
