package vttp.batch4.csf.toiletnearme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.repositories.ReviewRepository;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepo;

  @Transactional(rollbackFor=InsertReviewException.class)
  public boolean createNewReview(Review review) throws InsertReviewException {

    if (reviewRepo.create(review) == false) {
      System.out.println(">>>unsuccessful");
      throw new InsertReviewException("invalid request");
    }
      System.out.println(">>>successfully inserted");
      return reviewRepo.create(review);
  
  }
}
