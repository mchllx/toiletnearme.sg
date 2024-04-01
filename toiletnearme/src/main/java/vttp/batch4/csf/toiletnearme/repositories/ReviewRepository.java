package vttp.batch4.csf.toiletnearme.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;

@Repository
public class ReviewRepository {

  @Autowired
  private JdbcTemplate template;

  public boolean create(Review review) throws InsertReviewException{

    // insert into purchase_order(id, date, name, address, priority, comments, cart)
    //     values (?, ?, ?, ?, ?, ?, ?)
    //     """;

    return template.update(SQLQueries.SQL_INSERT_REVIEW
        , review.getReviewId()
        // , review.getDate()
        // , review.getName()
        // , review.getAddress()
        // , review.getPriority()
        // , review.getComments()
        // , review.getCart().toString()
        ) > 0;
  }
}
