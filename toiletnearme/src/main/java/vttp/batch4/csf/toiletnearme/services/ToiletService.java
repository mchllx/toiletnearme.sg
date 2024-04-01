package vttp.batch4.csf.toiletnearme.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  

}
