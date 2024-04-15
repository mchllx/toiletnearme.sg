package vttp.batch4.csf.toiletnearme.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.models.User;

@Repository
public class ReviewRepository {

  @Autowired
  private JdbcTemplate template;

  // review_id auto incr user_id, toilet_id, created_on, last_update, header, text, rating, images
  public boolean insertReview(Review review, User user) throws InsertReviewException {
      return template.update(SQLQueries.SQL_INSERT_REVIEW
      , user.getUserId()
      , review.getToiletId()
      , review.getCreatedOn()
      , review.getLastUpdate()
      , review.getHeader()
      , review.getBody()
      , review.getRating()
      , review.getImages()
      ) > 0;
  }
}
