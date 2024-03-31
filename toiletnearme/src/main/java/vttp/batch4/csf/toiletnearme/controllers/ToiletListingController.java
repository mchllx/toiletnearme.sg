package vttp.batch4.csf.toiletnearme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.services.ToiletService;

@Controller
@CrossOrigin(origins={
    "http://localhost:4200"
  , "https://vttpb4-michelle-lim.up.railway.app"
  , "http://www.toiletnearme.sg"
  , "http://localhost:8080"})

@RequestMapping(path="/api"
  , produces = MediaType.APPLICATION_JSON_VALUE)
  
public class ToiletListingController {

  @Autowired
  private ToiletService toiletSvc;

  @GetMapping(path="/categories")
  @ResponseBody
  public ResponseEntity<String> getCategories() {

    JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletCategories());

    return ResponseEntity.ok(arrBuilder.build().toString());
  }

  @GetMapping(path="/category/{category}")
  @ResponseBody
  public ResponseEntity<String> getCategory(@PathVariable String category) {

    List<JsonObject> products = toiletSvc.getToiletsByCategory(category)
        .stream()
        .map(Utils::toJson)
        .toList();

    return ResponseEntity.ok(Json.createArrayBuilder(products).build().toString());
  }
}
