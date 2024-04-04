package vttp.batch4.csf.toiletnearme;


import java.util.UUID;

import org.bson.Document;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.models.Toilet;

public class Utils {

  // public static final String MONGODB_1 = "singapore";

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
  public static String createUUID() {
    return UlidCreator.getMonotonicUlid().toString();
  }
}
