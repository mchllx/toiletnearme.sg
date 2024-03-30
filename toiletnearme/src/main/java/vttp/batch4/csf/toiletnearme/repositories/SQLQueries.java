package vttp.batch4.csf.toiletnearme.repositories;

public class SQLQueries {

    public static final String SQL_CREATE_REVIEW = """
        insert into users(id, date, name, address, priority, comments, cart)
        values (?, ?, ?, ?, ?, ?, ?)
        """;

}
