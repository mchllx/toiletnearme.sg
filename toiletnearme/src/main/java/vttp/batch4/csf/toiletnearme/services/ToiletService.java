package vttp.batch4.csf.toiletnearme.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.exceptions.InsertToiletListingException;
import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.models.Toilet;
import vttp.batch4.csf.toiletnearme.repositories.ToiletListingRepository;

@Service
public class ToiletService {

  @Autowired
  private ToiletListingRepository toiletRepo;

  private static final Logger logger = Logger.getLogger(ToiletService.class.getName());

  public List<String> getToiletCategories() {
    return toiletRepo.getToiletCategories();
  }

  public List<Toilet> getToiletsByCategory(String category) {
    return toiletRepo.getToiletsByCategory(category, 20);
  }

  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void insertToilets(Toilet toilet) throws Exception {

    if (toiletRepo.insertToilet(toilet) == false) {
      System.out.printf(">>>Unsuccessful: %s was not inserted", toilet.getId());
      throw new InsertUserException("Invalid request");
    }

    // returning user inserts records into mySQL twice
      System.out.printf(">>>Successfully: %s was inserted", toilet.getId()); 
  }

  // TODO: check ID if already exists
  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void insertGSheetToiletHotel(
    String hotel, String room, String review, String website, String address)
    throws InsertToiletListingException {

    if (toiletRepo.insertGSheetToiletHotel(hotel, room, review, website, address) == false) {
      System.out.printf(">>>Unsuccessful: %s was not inserted", hotel);
      throw new InsertToiletListingException("Invalid request");
    }

    // returning user inserts records into mySQL twice
      System.out.printf(">>>Successfully: %s was inserted", hotel); 
  }

  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void insertGSheetToiletFemale(
    String region, String location, String remarks, String address)
    throws InsertToiletListingException {

    if (toiletRepo.insertGSheetToiletFemale(region, location, remarks, address) == false) {
      System.out.printf(">>>Unsuccessful: %s was not inserted", location);
      throw new InsertToiletListingException("Invalid request");
    }

    // returning user inserts records into mySQL twice
      System.out.printf(">>>Successfully: %s was inserted", location); 
  }

  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void insertGSheetToiletMale(
    String region, String location, String remarks, String address)
    throws InsertToiletListingException {

    if (toiletRepo.insertGSheetToiletMale(region, location, remarks, address) == false) {
      System.out.printf(">>>Unsuccessful: %s was not inserted", location);
      throw new InsertToiletListingException("Invalid request");
    }

    // returning user inserts records into mySQL twice
      System.out.printf(">>>Successfully: %s was inserted", location); 
  }



}
