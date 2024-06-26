package vttp.batch4.csf.toiletnearme.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.mortbay.jetty.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.services.GoogleSheetsService;
import vttp.batch4.csf.toiletnearme.services.GoogleSheetsServiceImpl;
import vttp.batch4.csf.toiletnearme.services.ToiletService;

@Controller
@CrossOrigin()
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
  
public class GoogleAPIController {

  @Value("${maps.api.key}")
  private String mapsAPIKey;

  // NOT USED: gmaps Init() in index.hmtl has to intialise before any services
  // GET localhost:8080/api/gmap/key
  @GetMapping(path="/gmap/key")
  @ResponseBody
  public ResponseEntity<String> getGoogleMapAPIKey() {
    System.out.println("Google Maps API Key requested");
    
    if (mapsAPIKey.isEmpty()) {
      System.out.println("API Key does not exist");

      return ResponseEntity
      .status(HttpStatus.ORDINAL_500_Internal_Server_Error)
      .body("");
    }

    return ResponseEntity
      .status(HttpStatus.ORDINAL_200_OK)
      .body(Json.createObjectBuilder().add("apiKey", mapsAPIKey).build().toString());
  } 

}
