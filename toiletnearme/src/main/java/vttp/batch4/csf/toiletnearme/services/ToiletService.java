package vttp.batch4.csf.toiletnearme.services;

import java.util.LinkedList;
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

  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void updateToiletfromGSheet() throws InsertToiletListingException{
    List<Toilet> toilets = new LinkedList<>();

      toiletRepo.getGSheetToiletFemale()
        .stream()
        .forEach(toilet -> toilets.add(toilet));

        toiletRepo.getGSheetToiletMale()
        .stream()
        .forEach(toilet -> toilets.add(toilet));

        toiletRepo.getGSheetToiletHotel()
        .stream()
        .forEach(toilet -> toilets.add(toilet));
      
        toilets
          .forEach(toilet -> {
            try {
              insertToilet(toilet);
            } catch (InsertToiletListingException e) {
              System.out.printf(">>>Unsuccessful: %s was not inserted", toilet.getToiletId());
            }
          }); 
          System.out.printf(">>>Successful: %s was inserted", toilets.toString());
  }

  public List<String> getToiletAddress() {
    return toiletRepo.getToiletAddress();
  }

  public List<String> getToiletAddressByRegion(String region) {
    return toiletRepo.getToiletAddressByRegion(region);
  }

  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void insertToilet(Toilet toilet) throws InsertToiletListingException {

    if (toiletRepo.insertToilet(toilet) == false) {
      System.out.printf(">>>Unsuccessful: %s was not inserted", toilet.getToiletId());
      throw new InsertToiletListingException("Invalid request");
    }

    // returning user inserts records into mySQL twice
      System.out.printf(">>>Successful: %s was inserted", toilet.getToiletId()); 
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
      System.out.printf(">>>Successful: %s was inserted", hotel); 
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
      System.out.printf(">>>Successful: %s was inserted", location); 
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
      System.out.printf(">>>Successful: %s was inserted", location); 
  }



}
