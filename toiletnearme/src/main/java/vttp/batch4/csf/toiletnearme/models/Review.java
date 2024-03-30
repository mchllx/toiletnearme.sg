package vttp.batch4.csf.toiletnearme.models;

import java.util.Date;
import java.util.Map;

import vttp.batch4.csf.toiletnearme.Utils;

public class Review {
    private String id;
    private String name;
    private String header;
    private String body;
    private Date createdOn;
    private Date lastUpdate;
    private String toiletId;

    private Map<String, Boolean> amenities;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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

}
