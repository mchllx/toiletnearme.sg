package vttp.batch4.csf.toiletnearme;


import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.models.Toilet;

public class Utils {

  public static final String MONGODB_1 = "singapore";

  public static final String AMENITIES_1 = "babycare";
  public static final String AMENITIES_2 = "bidet";
  public static final String AMENITIES_3 = "dryer";
  public static final String AMENITIES_4 = "reserved";

  public static Toilet toToilet(Document doc) {
    // Toilet toilet = new Toilet();
    // toilet.setId(doc.getObjectId("_id").toHexString());
    // toilet.setName(doc.getString("ProductName"));
    // toilet.setBrand(doc.getString("Brand"));
    // toilet.setPrice(doc.getDouble("Price").floatValue());
    // toilet.setDiscountPrice(doc.getDouble("DiscountPrice").floatValue());
    // toilet.setImage(doc.getString("Image_Url"));
    // toilet.setQuantity(doc.getString("Quantity"));
    // return toilet;
    return null;
  }

  public static JsonObject toJson(Toilet toilet) {
    // return Json.createObjectBuilder()
    //   .add("prodId", toilet.getId())
    //   .add("name", toilet.getName())
    //   .add("brand", toilet.getBrand())
    //   .add("price", toilet.getPrice())
    //   .add("discountPrice", toilet.getDiscountPrice())
    //   .add("image", toilet.getImage())
    //   .add("quantity", toilet.getQuantity())
    //   .build();
      return null;
  }
}
