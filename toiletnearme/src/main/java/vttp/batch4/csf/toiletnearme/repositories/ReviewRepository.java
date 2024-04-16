package vttp.batch4.csf.toiletnearme.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.models.User;

@Repository
public class ReviewRepository {

  @Autowired
  private JdbcTemplate template;

  private Logger logger = Logger.getLogger(ReviewRepository.class.getName());

  public boolean deleteReviewByID(String id) {
    return template.update(SQLQueries.SQL_DELETE_REVIEW_BY_ID_USER, id) > 0;
  }

  // review_id auto incr user_id, toilet_id, name, created_on, last_update, header, text, rating, images
  public boolean insertReview(Review review, User user) throws InsertReviewException {
      return template.update(SQLQueries.SQL_INSERT_REVIEW
      , user.getUserId()
      , user.getFirstName()
      , review.getToiletId()
      , review.getCreatedOn()
      , review.getLastUpdate()
      , review.getHeader()
      , review.getBody()
      , review.getRating()
      , review.getImages()
      ) > 0;
  }

  public Review getReviewByID(String id) {
    SqlRowSet rs;

    if (id.startsWith(Utils.PREFIX_USER)) {
      rs = template.queryForRowSet(SQLQueries.SQL_SELECT_REVIEW_BY_ID_USER, id);
    } else {
      rs = template.queryForRowSet(SQLQueries.SQL_SELECT_REVIEW_BY_ID_TOILET, id); 
    }

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
      return new Review(
        rs.getInt("review_id"),
        rs.getString("name"),
        rs.getString("header"),
        rs.getString("text"),
        rs.getDate("created_on"),
        rs.getDate("last_update"),
        rs.getString("toilet_id"),
        rs.getString("user_id"),
        rs.getFloat("rating"),
        rs.getString("images")
      );
    }
  }

  public List<Review> getReviews() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_REVIEW);

    // toilet_id, name, address, price, gender, type, remarks, website, last_update, opening_hours_id, closing_hours_id, images, region
    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
      // review_id, user_id, toilet_id, created_on, last_update, header, text, rating, images
        List<Review> list = new LinkedList<>();
        while (rs.next() != false) {
          Review review = new Review(
            rs.getInt("review_id"),
            rs.getString("name"),
            rs.getString("header"),
            rs.getString("text"),
            rs.getDate("created_on"),
            rs.getDate("last_update"),
            rs.getString("toilet_id"),
            rs.getString("user_id"),
            rs.getFloat("rating"),
            rs.getString("images")
          );

          list.add(review);
        }
        return list;
    }
  }
}
