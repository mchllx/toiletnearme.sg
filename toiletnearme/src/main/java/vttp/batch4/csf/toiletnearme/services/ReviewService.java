package vttp.batch4.csf.toiletnearme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp.batch4.csf.toiletnearme.exceptions.InsertReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.repositories.ImageRepository;
import vttp.batch4.csf.toiletnearme.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ImageRepository imageRepo;

  @Autowired
  private ReviewRepository reviewRepo;

  // TODO: Insert/Select images from S3
  @Transactional(rollbackFor=InsertReviewException.class)
  public boolean createNewReview(Review review) throws InsertReviewException {

    if (reviewRepo.create(review) == false) {
      System.out.println(">>>unsuccessful");
      throw new InsertReviewException("invalid request");
    }
      System.out.println(">>>successfully inserted");
      return reviewRepo.create(review);
  
  }

  // String url = imageRepo.save(imageId, imageFile.getContentType()
	// 			, imageFile.getInputStream(), imageFile.getSize());
}
