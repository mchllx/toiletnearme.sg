package vttp.batch4.csf.toiletnearme.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.repositories.ImageRepository;
import vttp.batch4.csf.toiletnearme.repositories.ReviewRepository;
import vttp.batch4.csf.toiletnearme.repositories.ToiletListingRepository;
import vttp.batch4.csf.toiletnearme.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ImageRepository imageRepo;

  @Autowired
	private ToiletListingRepository toiletRepo;

  @Autowired
  private ReviewRepository reviewRepo;

  @Autowired
  private UserRepository userRepo;

  // TODO: Insert/Select images from S3
  @Transactional(rollbackFor=InsertReviewException.class)
  public boolean insertReview(Review review, User user) throws InsertReviewException {

    if (reviewRepo.insertReview(review, user) == false) {
      System.out.println(">>>unsuccessful");
      throw new InsertReviewException("invalid request");
    }
      System.out.println(">>>successfully inserted");
      return reviewRepo.insertReview(review, user);
  
  }

  public List<Review> getGSheetHotelReviews() {
    return toiletRepo.getGSheetToiletHotelReviews();
  }

  public void updateReviewsFromGSheet() throws InsertReviewException {
    List<Review> reviews = getGSheetHotelReviews();
      reviews.forEach(review -> {
        try {
          insertReview(review, new User("USR01HVGWH8XGHEH6G93SAQ9GDJ0S"));
        } catch (InsertReviewException e1) {
          System.out.printf(">>>Unsuccessful: %s was not inserted", review.getReviewId());
        }
        System.out.printf(">>>Successful: %s was inserted", review);
      });

  }
}

  // String url = imageRepo.save(imageId, imageFile.getContentType()
	// 			, imageFile.getInputStream(), imageFile.getSize());