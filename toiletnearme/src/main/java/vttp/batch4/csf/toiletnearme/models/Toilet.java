package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;
import java.util.List;

import com.github.f4b6a3.ulid.UlidCreator;

public class Toilet {

    private String id;
    private String name;
    private String address;
    private float price;
    private String description;
    private String openingHours;
    private Date createdOn;
    private Date updatedOn;
    private String[] thumbnails;
    private float rating;
    private List<Review> reviews;
    private String footTraffic;

    public Toilet() {
    // 26 characters
    // Eg - 01HNS6YMJNZX24G4YN38AGBZEE
        id = UlidCreator.getMonotonicUlid().toString();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }
    public Date getCreatedOn() { return createdOn; }
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }
    public Date getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(Date updatedOn) { this.updatedOn = updatedOn; }
    public String[] getThumbnails() { return thumbnails; }
    public void setThumbnails(String[] thumbnails) { this.thumbnails = thumbnails; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
    public String getFootTraffic() { return footTraffic; }
    public void setFootTraffic(String footTraffic) { this.footTraffic = footTraffic; }
    
    // directions

    @Override
    public String toString() {
        return "Toilet{id=%s, name=%s, address=%s, price=%.2f, description=%s, openingHours=%s, createdOn: %s, updatedOn: %s, thumbnails: %s, rating: %.2f, reviews: %s, footTraffic: %s }"
        .formatted(id, name, address, price, description, openingHours
            ,createdOn.toString(), updatedOn.toString(), thumbnails
            ,rating, reviews, footTraffic);
    }
}
