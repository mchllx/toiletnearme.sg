package vttp.batch4.csf.toiletnearme.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.exceptions.InsertToiletListingException;
import vttp.batch4.csf.toiletnearme.models.Toilet;

@Repository
public class ToiletListingRepository {

  @Autowired
  private JdbcTemplate template;

  public boolean insertToilet(Toilet toilet) throws InsertToiletListingException{
  // insert into toilet(id, date, name, address, priority, comments, cart)
  //     values (?, ?, ?, ?, ?, ?, ?)
  //     """;

    return template.update(SQLQueries.SQL_INSERT_TOILET
      , toilet.getId()
      // , review.getDate()
      // , review.getName()
      // , review.getAddress()
      // , review.getPriority()
      // , review.getComments()
      // , review.getCart().toString()
      ) > 0;
  }

  public List<String> getToiletCategories() {
    return new LinkedList<>();
  }

  public List<Toilet> getToiletsByCategory(String category, Integer limit) {
    return new LinkedList<>();
  }

}
