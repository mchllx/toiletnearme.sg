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
import vttp.batch4.csf.toiletnearme.repositories.ToiletRepository;
import vttp.batch4.csf.toiletnearme.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ImageRepository imageRepo;

  @Autowired
	private ToiletRepository toiletRepo;

  @Autowired
  private ReviewRepository reviewRepo;

  @Autowired
  private UserService userSvc;

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

  public List<Review> getReviews() {
    return reviewRepo.getReviews();
  }

  public Review getReviewByID(String id) {
    return reviewRepo.getReviewByID(id);
  }

  public boolean deleteReviewByID(String id) {
    return reviewRepo.deleteReviewByID(id);
  }

  public List<Review> getGSheetHotelReviews() {
    return toiletRepo.getGSheetToiletHotelReviews();
  }

  public void updateReviewsFromGSheet() throws InsertReviewException {
    List<Review> reviews = getGSheetHotelReviews();
      reviews.forEach(review -> {
        try {
          insertReview(review, userSvc.selectUserById("USR01HVKSP4F1A1K6RSCXNZ15G3T7"));
        } catch (InsertReviewException e1) {
          System.out.printf(">>>Unsuccessful: %s was not inserted\n", review.getReviewId());
        }
        System.out.printf(">>>Successful: %s was inserted\n", review);
      });
  }
}

  // String url = imageRepo.save(imageId, imageFile.getContentType()
	// 			, imageFile.getInputStream(), imageFile.getSize());