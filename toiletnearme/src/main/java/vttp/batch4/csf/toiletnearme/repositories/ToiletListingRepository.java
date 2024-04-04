package vttp.batch4.csf.toiletnearme.repositories;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.exceptions.InsertToiletListingException;
import vttp.batch4.csf.toiletnearme.models.Amenities;
import vttp.batch4.csf.toiletnearme.models.Toilet;

@Repository
public class ToiletListingRepository {

  @Autowired
  private JdbcTemplate template;
  
  // toilet_id, name, address, price, gender,
  // type, remarks, opening_hours, closing_hours, images
  // region, amenities
  public boolean insertToilet(Toilet toilet) throws InsertToiletListingException{

    return template.update(SQLQueries.SQL_INSERT_TOILET
      , toilet.getToiletId()
      , toilet.getName()
      , toilet.getAddress()
      , toilet.getPrice()
      , toilet.getGender()
      , toilet.getType()
      , toilet.getRemarks()
      , toilet.getOpeningHours()
      , toilet.getClosingHours()
      , toilet.getImages()
      , toilet.getRegion()
      , Utils.createUUID26Char() 
      ) > 0;
  }

  //gsheets_female_toilet_id, last_update, region, location, remarks, address)
  public boolean insertGSheetToiletFemale(
    String region, String location, String remarks, String address)
    throws InsertToiletListingException {

    return template.update(SQLQueries.SQL_INSERT_GSHEETS_TOILET_FEMALE
      , Utils.createUUID26Char()
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
      , Utils.createUUID26Char()
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
      , Utils.createUUID26Char()
      , new Date()
      , hotel
      , room
      , review
      , website
      , address
      ) > 0;
  }

  public List<String> getToiletCategories() {
    return new LinkedList<>();
  }

  public List<Toilet> getToiletsByCategory(String category, Integer limit) {
    return new LinkedList<>();
  }

}
