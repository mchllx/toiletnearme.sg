package vttp.batch4.csf.toiletnearme.repositories;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.models.User;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate template;

  private Logger logger = Logger.getLogger(UserRepository.class.getName());

    // insert into purchase_order(id, date, name, address, priority, comments, cart)
    //     values (?, ?, ?, ?, ?, ?, ?)
    //     """;
    public boolean insertNewUser(User user) throws InsertUserException {
    return template.update(SQLQueries.SQL_INSERT_USER
        , user.getUserId()
        // , review.getDate()
        // , review.getName()
        // , review.getAddress()
        // , review.getPriority()
        // , review.getComments()
        // , review.getCart().toString()
        ) > 0;
    }

    // user_id int, username str, email str, password str
    // created_on timestamp, first_name str, last_name str, profile_image str, role str
    public User selectByEmail(String email) {
        SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_USER_BY_EMAIL, email);

        //if record does not exist
        if(!rs.next()){
        logger.info("User does not exist in database");
            return new User();
        } else {
            logger.info("User found in database");
            rs.beforeFirst();

            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setCreatedOn(rs.getDate("created_on"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setProfileImage(rs.getString("profile_image"));
            user.setRole(rs.getString("role"));
          
            return user; 
        }
    }
}