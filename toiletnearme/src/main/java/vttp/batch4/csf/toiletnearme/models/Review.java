package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;
import java.util.Map;

import vttp.batch4.csf.toiletnearme.Utils;

public class Review {
    private Integer reviewId;
    private String name;
    private String header;
    private String body;
    private Date createdOn;
    private Date lastUpdate;
    private String toiletId;
    private float rating;
    private String[] images;
    private Map<String, Boolean> amenities;

    public Review() { 
    }

    public Review(Integer reviewId, String name, String header, String body, Date createdOn, Date lastUpdate, String toiletId,
            Map<String, Boolean> amenities) {
        this.reviewId = reviewId;
        this.name = name;
        this.header = header;
        this.body = body;
        this.createdOn = createdOn;
        this.lastUpdate = lastUpdate;
        this.toiletId = toiletId;
        this.amenities = amenities;
    }
    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHeader() { return header; }
    public void setHeader(String header) { this.header = header; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public Date getCreatedOn() { return createdOn; }
    public void setCreatedOn(Date createdOn) { this.createdOn = createdOn; }
    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }
    public String getToiletId() { return toiletId; }
    public void setToiletId(String toiletId) { this.toiletId = toiletId; }
    public String[] getImages() { return images; }
    public void setImages(String[] images) { this.images = images; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public Map<String, Boolean> getAmenities() { return amenities; }
    public void setAmenities(Map<String, Boolean> amenities) { this.amenities = amenities; }

    /** 1 - babycare
    *   2 - bidet
        3 - dryer
        4 - reserved
    **/

    public Map<String, Boolean> newAmenities(Map<String, Boolean> amenities) {
        if (amenities.isEmpty()) {
            System.out.println("creating amenities");
            amenities.put(Utils.AMENITIES_1, null);
            amenities.put(Utils.AMENITIES_2, null);
            amenities.put(Utils.AMENITIES_3, null);
            amenities.put(Utils.AMENITIES_4, null);
        } else {
            System.out.println("amenities exist");
        }
        return amenities; 
    }

    @Override
    public String toString() {
        return "Review{reviewId=%s, name=%s, header=%s, body=%s, createdOn=%s, lastUpdate=%s, toiletId=%s, rating: %.2f, images: %s, amenities: %s}"
        .formatted(reviewId, name, header, body
            ,createdOn.toString(), lastUpdate.toString(), toiletId, images, rating
            ,amenities.toString());
    }

}