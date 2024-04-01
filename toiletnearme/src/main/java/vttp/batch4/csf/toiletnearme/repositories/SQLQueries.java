package vttp.batch4.csf.toiletnearme.repositories;

public class SQLQueries {


    public static final String SQL_INSERT_REVIEW = """
        insert into reviews(username, email, password, created_on, first_name, last_name, profile_image, role)
        values (?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String SQL_INSERT_TOILET = """
        insert into toilets(user_id, username, email, password, created_on, first_name, last_name, profile_image, role)
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
