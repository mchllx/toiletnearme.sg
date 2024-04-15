package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.JsonObject;

public class Toilet {

    private String toiletId;
    private String name;
    private String address;
    private float price;
    private String gender;
    private String type;
	private String remarks;
    private Map<String, Date> openingHours = new HashMap<>();
    private Map<String, Date> closingHours = new HashMap<>();
    private Date updatedOn;
    private String[] images;
    private String website;
    private String region;
    private String author;
    private float rating;
    private List<Review> reviews;
    private String footTraffic;

    public Toilet(String toiletId, String name, String address, String type, String remarks, List<Review> reviews, String website, Date updatedOn) {
        this.toiletId = toiletId;
        this.name = name;
        this.address = address;
        this.type = type;
        this.remarks = remarks;
        this.reviews = reviews;
        this.website = website;
        this.updatedOn = updatedOn;
    }

    public Toilet(String toiletId, String name, String gender, String address, String type, String remarks, Date updatedOn,
            String region) {
        this.toiletId = toiletId;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.type = type;
        this.remarks = remarks;
        this.updatedOn = updatedOn;
        this.region = region;
    }

    public Toilet(String toiletId, String name, String address, float price, String gender, String type, String remarks,
            Map<String, Date> openingHours, Map<String, Date> closingHours, Date updatedOn, String[] images, String website, String region,
            String author, float rating,List<Review> reviews, String footTraffic) {
        this.toiletId = toiletId;
        this.name = name;
        this.address = address;
        this.price = price;
        this.gender = gender;
        this.type = type;
        this.remarks = remarks;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.updatedOn = updatedOn;
        this.images = images;
        this.website = website;
        this.region = region;
        this.author = author;
        this.rating = rating;
        this.reviews = reviews;
        this.footTraffic = footTraffic;
    }

    public Toilet() {
    }
    
    public String getToiletId() { return toiletId; }
    public void setToiletId(String toiletId) { this.toiletId = toiletId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getType() { return type; }
	public void setType(String type) { this.type = type; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public Map<String, Date> getOpeningHours() { return openingHours; }
	public void setOpeningHours(Map<String, Date> openingHours) { this.openingHours = openingHours; }
	public Map<String, Date> getClosingHours() { return closingHours; }
	public void setClosingHours(Map<String, Date> closingHours) { this.closingHours = closingHours; }
    public Date getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(Date updatedOn) { this.updatedOn = updatedOn; }
    public String[] getImages() { return images; }
    public void setImages(String[] images) { this.images = images; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public String getFootTraffic() { return footTraffic; }
    public void setFootTraffic(String footTraffic) { this.footTraffic = footTraffic; }
    
    // directions
    @Override
    public String toString() {
        return "Toilet{toiletId=%s, name=%s, address=%s, price=%.2f, gender=%s, type=%s, remarks=%s, openingHours=%s, closingHours=%s, updatedOn: %s, author: %s, images: %s, website: %s, region: %s, rating: %.2f, reviews: %s, footTraffic: %s }"
        .formatted(toiletId, name, address, price, gender, type, remarks, openingHours, closingHours
            , updatedOn, author, images, website
            ,region, rating, reviews, footTraffic);
    }

    public static Toilet toToilet() {
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

    public static Toilet fromJson(JsonObject jsonObj) {
            Toilet toilet = new Toilet();

        // return Json.createObjectBuilder()
        //   .add("prodId", toilet.getId())
        //   .add("name", toilet.getName())
        //   .add("brand", toilet.getBrand())
        //   .add("price", toilet.getPrice())
        //   .add("discountPrice", toilet.getDiscountPrice())
        //   .add("image", toilet.getImage())
        //   .add("quantity", toilet.getQuantity())
        //   .build();
        return toilet;
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