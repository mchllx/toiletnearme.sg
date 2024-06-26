package vttp.batch4.csf.toiletnearme.repositories;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.exceptions.NoAccessException;
import vttp.batch4.csf.toiletnearme.models.Role;
import vttp.batch4.csf.toiletnearme.models.User;

@Repository
public class UserRepository {

  @Autowired
  private JdbcTemplate template;

  private Logger logger = Logger.getLogger(UserRepository.class.getName());

    // role, email
    public boolean updateUserRoleByEmail(User user) throws InsertUserException, NoAccessException {
    return template.update(SQLQueries.SQL_UPDATE_USER_ROLE_BY_EMAIL
        , user.getEmail()
        , user.getAuthorities()
        ) > 0;
    }

    public User selectUserByEmail(String email) {
        SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_USER_BY_EMAIL
        , email);

        //if record does not exist
        if(!rs.next()){
        logger.info(">>>User does not exist in database");
        // returning new User() != null
            return null;
        } else {
            logger.info(">>>User found in database");
            // rs.beforeFirst();

            User user = new User();
            return user.fromSQL(rs);
            
        }
    }

    public User selectUserById(String userId) {
        SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_USER_BY_ID
        , userId);

        //if record does not exist
        if(!rs.next()){
        logger.info("User does not exist in database");
        // returning new User() != null
            return null;
        } else {
            logger.info("User found in database");
            User user = new User();
            // rs.beforeFirst();
          
            return user.fromSQL(rs); 
        }
    }

    // user_id, username, email, password, created_on
    // , last_update, first_name, last_name, profile_image, role
    public boolean insertUser(User user) throws InsertUserException {
        return template.update(SQLQueries.SQL_INSERT_USER
            , user.getUserId()
            , user.getUsername()
            , user.getEmail()
            , user.getPassword()
            , user.getCreatedOn()
            , user.getUpdatedOn()
            , user.getFirstName()
            , user.getLastName()
            , user.getProfileImage()
            , user.getAuthorities().toString()
            , user.isAccountNonExpired()
            , user.isEnabled()
            , user.isAccountNonLocked()
            , user.isCredentialsNonExpired()
            ) > 0;
        }
    
}