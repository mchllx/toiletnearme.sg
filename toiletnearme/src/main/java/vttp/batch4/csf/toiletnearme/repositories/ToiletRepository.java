package vttp.batch4.csf.toiletnearme.repositories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.exceptions.InsertToiletListingException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.models.Toilet;
import vttp.batch4.csf.toiletnearme.models.User;

@Repository
public class ToiletRepository {

  private Logger logger = Logger.getLogger(ToiletRepository.class.getName());

  @Autowired
  private JdbcTemplate template;

  public Toilet getToiletByID(String id) {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_TOILET, id);

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        return new Toilet(
          rs.getString("toilet_id"),
          rs.getString("name"),
          rs.getString("address"),
          rs.getFloat("price"),
          rs.getString("gender"),
          rs.getString("type"),
          rs.getString("remarks"),
          rs.getString("website"),
          rs.getDate("last_update"),
          rs.getString("images"),
          rs.getString("region")
        );
    }
  }

  public List<Toilet> getToilets() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_TOILET);

    // toilet_id, name, address, price, gender, type, remarks, website, last_update, opening_hours_id, closing_hours_id, images, region
    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        List<Toilet> list = new LinkedList<>();
        while (rs.next() != false) {
          Toilet toilet = new Toilet(
            rs.getString("toilet_id"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getFloat("price"),
            rs.getString("gender"),
            rs.getString("type"),
            rs.getString("remarks"),
            rs.getString("website"),
            rs.getDate("last_update"),
            rs.getString("images"),
            rs.getString("region")
          );

          list.add(toilet);
        }
        return list;
    }
  }

  public boolean deleteToiletByID(String id) {
    return template.update(SQLQueries.SQL_DELETE_TOILET_BY_ID, id ) > 0;
  }

  public boolean insertToilet(Toilet toilet) throws InsertToiletListingException{
    return template.update(SQLQueries.SQL_INSERT_TOILET
      , toilet.getToiletId()
      , toilet.getName()
      , toilet.getAddress()
      , toilet.getPrice()
      , toilet.getGender()
      , toilet.getType()
      , toilet.getRemarks()
      , toilet.getWebsite()
      , toilet.getUpdatedOn()
      , Utils.createUUID8Char(Utils.PREFIX_OPENING)
      , Utils.createUUID8Char(Utils.PREFIX_CLOSING)
      , toilet.getImages()
      , toilet.getRegion()
      ) > 0;
  }

  //gsheets_female_toilet_id, last_update, region, location, remarks, address)
  public boolean insertGSheetToiletFemale(
    String region, String location, String remarks, String address)
    throws InsertToiletListingException {

    return template.update(SQLQueries.SQL_INSERT_GSHEETS_TOILET_FEMALE
      , Utils.createUUID26Char(Utils.PREFIX_TOILET)
      , new Date()
      , region
      , location
      , remarks
      , address
      ) > 0;
  }

  //gsheets_male_toilet_id, last_update, region, location, remarks, address)
  public boolean insertGSheetToiletMale(
    String region, String location, String remarks, String address)
    throws InsertToiletListingException {

    return template.update(SQLQueries.SQL_INSERT_GSHEETS_TOILET_MALE
      , Utils.createUUID26Char(Utils.PREFIX_TOILET)
      , new Date()
      , region
      , location
      , remarks
      , address
      ) > 0;
  }

  // gsheets_hotel_toilets(gsheets_hotel_toilet_id, last_update, hotel, room, review, website, address)
  public boolean insertGSheetToiletHotel(
    String hotel, String room, String review, String website, String address)
    throws InsertToiletListingException {

    return template.update(SQLQueries.SQL_INSERT_GSHEETS_TOILET_HOTEL
      , Utils.createUUID26Char(Utils.PREFIX_TOILET)
      , new Date()
      , hotel
      , room
      , review
      , website
      , address
      ) > 0;
  }

  public List<Toilet> getGSheetToiletHotel() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_GSHEETS_TOILET_HOTEL);

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        List<Toilet> list = new LinkedList<>();
        while (rs.next() != false) {
          String userWebsite = rs.getString("website");
          String[] processedWebsite = userWebsite.split(",");
          
          Toilet toilet = new Toilet();
          toilet.setToiletId(rs.getString("gsheets_hotel_toilet_id"));
          toilet.setName(rs.getString("hotel"));
          toilet.setAddress(rs.getString("address"));
          toilet.setGender("Unisex");
          toilet.setType("Private");
          toilet.setRemarks(rs.getString("room"));
          toilet.setUpdatedOn(rs.getDate("last_update"));

          if (processedWebsite.length > 1) {
            toilet.setWebsite(processedWebsite[1]); 
          } else {
            toilet.setWebsite(processedWebsite[0]);
          }

          list.add(toilet);
        }
        return list;
    }
  }

  public List<Review> getGSheetToiletHotelReviews() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_GSHEETS_TOILET_HOTEL_REVIEW);

    if(!rs.next()){
      logger.info("Hotel review does not exist in database");
        return null;
    } else {
        List<Review> list = new LinkedList<>();
        while (rs.next() != false) {
          String userReview = rs.getString("review");
          String[] processedReview = userReview.split(",");

          String toiletId = rs.getString("gsheets_hotel_toilet_id");

          // '\"@hilmzeats\'s Deluxe Room\",\"https://www.tiktok.com/@hilmzeats/video/6982870107588480258?is_from_webapp=1&sender_device=pc&web_id=7100855173279237634\"
          if (processedReview.length > 1){
            Review review = new Review();
            review.setCreatedOn(new Date());
            review.setLastUpdate(new Date());
            review.setBody("Credits to contributors from @toiletswithbidetsg");
            review.setHeader(processedReview[0].replace('"', ' ').strip());
            review.setToiletId(toiletId);
            review.setImages(processedReview[1].replace('"', ' ').strip());
            list.add(review);
          }
        }
        return list;
    }
  }

  public List<Toilet> getGSheetToiletMale() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_GSHEETS_TOILET_MALE);

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        List<Toilet> list = new LinkedList<>();
        while (rs.next() != false) {
          Toilet toilet = new Toilet(
            rs.getString("gsheets_male_toilet_id"),
            rs.getString("location"),
            "Male",
            rs.getString("address"),
            "Public",
            rs.getString("remarks"),
            rs.getDate("last_update"),
            rs.getString("region")
          );

          list.add(toilet);
        }
        return list;
    }
  }

  public List<Toilet> getGSheetToiletFemale() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_GSHEETS_TOILET_FEMALE);

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        List<Toilet> list = new LinkedList<>();
        while (rs.next() != false) {
          Toilet toilet = new Toilet(
            rs.getString("gsheets_female_toilet_id"),
            rs.getString("location"),
            "Female",
            rs.getString("address"),
            "Public",
            rs.getString("remarks"),
            rs.getDate("last_update"),
            rs.getString("region")
          );

          list.add(toilet);
        }
        return list;
    }
  }

  public List<String> getToiletAddress() {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_TOILET_ADDRESS);

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        List<String> list = new LinkedList<>();
        while (rs.next() != false) {
          list.add(rs.getString("address"));
        }
        return list;
    }
  }

  public List<String> getToiletAddressByRegion(String region) {
    SqlRowSet rs = template.queryForRowSet(SQLQueries.SQL_SELECT_TOILET_ADDRESS_BY_REGION, region);

    if(!rs.next()){
      logger.info("Toilet does not exist in database");
        return null;
    } else {
        List<String> list = new LinkedList<>();
        while (rs.next() != false) {
          list.add(rs.getString("address"));
        }
        return list;
    }
  }

}
