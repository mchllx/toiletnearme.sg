package vttp.batch4.csf.toiletnearme.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.exceptions.CreateReviewException;
import vttp.batch4.csf.exceptions.CreateUserException;
import vttp.batch4.csf.toiletnearme.models.Review;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  public boolean create(Review review) throws CreateUserException{

    // insert into purchase_order(id, date, name, address, priority, comments, cart)
    //     values (?, ?, ?, ?, ?, ?, ?)
    //     """;

    return template.update(SQLQueries.SQL_CREATE_REVIEW
        , review.getOrderId()
        , review.getDate()
        , review.getName()
        , review.getAddress()
        , review.getPriority()
        , review.getComments()
        , review.getCart().toString()
        ) > 0;
  }
}
