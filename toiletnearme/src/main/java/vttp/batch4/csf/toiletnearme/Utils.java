package vttp.batch4.csf.toiletnearme;


import java.util.UUID;


import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.models.Toilet;

public class Utils {

  public static final String PREFIX_TOILET = "TLT";
  public static final String PREFIX_OPENING = "OPN";
  public static final String PREFIX_CLOSING = "CLS";
  public static final String PREFIX_REVIEW = "REV";
  public static final String PREFIX_USER = "USR";

  public static final String ROLE_ADMIN = "ADMIN";
  public static final String ROLE_USER = "USER";

  public static final String AMENITIES_1 = "babycare";
  public static final String AMENITIES_2 = "bidet";
  public static final String AMENITIES_3 = "dryer";
  public static final String AMENITIES_4 = "reserved";
  public static final String AMENITIES_5 = "locked";
  public static final String AMENITIES_6 = "unisex";

  public static final Integer SHEET_MALE = 0;
  public static final Integer SHEET_FEMALE = 1;
  public static final Integer SHEET_HOTEL = 2;

  // 26 characters
  // Eg - 01HNS6YMJNZX24G4YN38AGBZEE
  // public static String createUUID26Char() {
  //   return UlidCreator.getMonotonicUlid().toString();
  // }

  public static String createUUID26Char(String prefix) {
    return prefix.concat(UlidCreator.getMonotonicUlid().toString());
  }

  // 8 characters
  // Eg - 01HNS6YM
  public static String createUUID8Char(String prefix) {
    return prefix.concat(UlidCreator.getMonotonicUlid().toString().substring(0,8));
  }

  // 8 characters
  // Eg - 01HNS6YM
  public static String createUUID8Char() {
    return UlidCreator.getMonotonicUlid().toString().substring(0,8);
  }

  public static JsonObject toiletToJson(Toilet toilet) {
    return Json.createObjectBuilder()
      .add("id", toilet.getToiletId())
      .add("name", toilet.getName())
      .add("address", toilet.getAddress())
      .add("price", toilet.getPrice())
      .add("gender", toilet.getGender())
      .add("type", toilet.getType())
      .add("remarks", toilet.getRemarks())
      .add("website", toilet.getWebsite())
      .add("last_update", toilet.getUpdatedOn().toString())
      .add("images", toilet.getImages())
      .add("region", toilet.getRegion())
      .build();
  }

  public static JsonObject reviewToJson(Review review) {
    return Json.createObjectBuilder()
      .add("review_id", review.getReviewId())
      .add("name", review.getName())
      .add("header", review.getHeader())
      .add("text", review.getBody())
      .add("created_on", review.getCreatedOn().toString())
      .add("last_update", review.getLastUpdate().toString())
      .add("user_id", review.getUserId())
      .add("rating", review.getRating())
      .add("images", review.getImages())
      .build();
  }

}
