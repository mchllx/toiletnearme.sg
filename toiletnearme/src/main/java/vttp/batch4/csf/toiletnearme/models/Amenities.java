package vttp.batch4.csf.toiletnearme.models;

import java.util.Map;

import vttp.batch4.csf.toiletnearme.Utils;

public class Amenities {

    private Map<String, Boolean> amenities;
    private String amenitiesId;

	public Amenities() {
    }

    public Amenities(Map<String, Boolean> amenities, String amenitiesId) {
		this.amenities = amenities;
        this.amenitiesId = amenitiesId;
	}

	public Map<String, Boolean> getAmenities() { return amenities; }
	public void setAmenities(Map<String, Boolean> amenities) { this.amenities = amenities; }
    public String getAmenitiesId() { return amenitiesId; }
	public void setAmenitiesId(String amenitiesId) { this.amenitiesId = amenitiesId; }

	/** 1 - babycare
    *   2 - bidet
        3 - dryer
        4 - reserved
        5 - locked (keys required)
        6 - unisex
    **/

    public Map<String, Boolean> createAmenities(Map<String, Boolean> amenities) {
        if (amenities.isEmpty()) {
            System.out.println("creating amenities");
            amenities.put(Utils.AMENITIES_1, null);
            amenities.put(Utils.AMENITIES_2, null);
            amenities.put(Utils.AMENITIES_3, null);
            amenities.put(Utils.AMENITIES_4, null);
            amenities.put(Utils.AMENITIES_5, null);
            amenities.put(Utils.AMENITIES_6, null);
        } else {
            System.out.println("amenities exist");
        }
        return amenities; 
    }

    @Override
    public String toString() {
        return "Amenities{babycare=%b, bidet=%b, dryer=%b, reserved=%b, locked=%b, unisex=%b, amenitiesId=%s}"
        .formatted(amenities.get("babycare"), amenities.get("bidet"), amenities.get("dryer")
            , amenities.get("reserved"), amenities.get("locked"), amenities.get("unisex")
            , amenitiesId);
    }
 
}
