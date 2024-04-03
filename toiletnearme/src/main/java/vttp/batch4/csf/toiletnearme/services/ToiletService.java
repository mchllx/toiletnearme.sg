package vttp.batch4.csf.toiletnearme.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.exceptions.InsertToiletListingException;
import vttp.batch4.csf.toiletnearme.models.Toilet;
import vttp.batch4.csf.toiletnearme.repositories.ToiletListingRepository;

@Service
public class ToiletService {

  @Autowired
  private ToiletListingRepository toiletRepo;

  public List<String> getToiletCategories() {
    return toiletRepo.getToiletCategories();
  }

  public List<Toilet> getToiletsByCategory(String category) {
    return toiletRepo.getToiletsByCategory(category, 20);
  }

  @Transactional(rollbackFor=InsertToiletListingException.class)
  public void insertToilets(Toilet toilet) throws Exception {

    if (toiletRepo.insertToilet(toilet) == false) {
      (">>>Invalid request");
      toiletRepo.insertToilet(toilet); 
    }
    System.out.println(">>>successfully inserted");

    if (poRepo.create(order) == false) {
      System.out.println(">>>unsuccessful");
      throw new PurchaseOrderException("invalid request");
    }
      System.out.println(">>>successfully inserted");
      return poRepo.create(order);
  

  }



}
