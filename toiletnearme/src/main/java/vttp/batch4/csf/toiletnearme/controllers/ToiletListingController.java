package vttp.batch4.csf.toiletnearme.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.mortbay.jetty.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.services.GoogleSheetsService;
import vttp.batch4.csf.toiletnearme.services.GoogleSheetsServiceImpl;
import vttp.batch4.csf.toiletnearme.services.ToiletService;

@Controller
@CrossOrigin()
@RequestMapping(path="/api"
  , produces = MediaType.APPLICATION_JSON_VALUE)
  
public class ToiletListingController {

  @Autowired
  private GoogleSheetsServiceImpl googleSheetSvc;

  @PostMapping(path="/api/gsheets")
  @ResponseBody
  public ResponseEntity<String> postToilet() {

    try {
      googleSheetSvc.getSpreadSheetValues(Utils.SHEET_FEMALE);
      googleSheetSvc.getSpreadSheetValues(Utils.SHEET_MALE);
      googleSheetSvc.getSpreadSheetValues(Utils.SHEET_HOTEL);  
    } catch (IOException e1) {
      e1.printStackTrace();
      return ResponseEntity
        .status(HttpStatus.ORDINAL_500_Internal_Server_Error)
        .body("Server error");
    } catch (GeneralSecurityException e2) {
      e2.printStackTrace();
      return ResponseEntity
      .status(HttpStatus.ORDINAL_500_Internal_Server_Error)
      .body("Server error");
    }
    return ResponseEntity
        .status(HttpStatus.ORDINAL_200_OK)
        .body("Successful");
  }
  
  // @GetMapping(path="/categories")
  // @ResponseBody
  // public ResponseEntity<String> getCategories() {

  //   JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletCategories());

  //   return ResponseEntity.ok(arrBuilder.build().toString());
  // }

  // @GetMapping(path="/category/{category}")
  // @ResponseBody
  // public ResponseEntity<String> getCategory(@PathVariable String category) {

  //   List<JsonObject> products = toiletSvc.getToiletsByCategory(category)
  //       .stream()
  //       .map(Utils::toJson)
  //       .toList();

  //   return ResponseEntity.ok(Json.createArrayBuilder(products).build().toString());
  // }
}
