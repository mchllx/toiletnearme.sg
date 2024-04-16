package vttp.batch4.csf.toiletnearme.controllers;


import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.services.ReviewService;


// GET http://localhost:8080/api/review/{id}")
// GET http://localhost:8080/api/review/user/{region}")
// POST http://localhost:8080/api/review/")
// DELETE http://localhost:8080/api/toilet/address/{id}")

// TODO: add headers for jwt
@Controller
@CrossOrigin()
@RequestMapping(path="/api/review")
public class ReviewController {

  @Autowired
  private ReviewService reviewSvc;

  // GET http://localhost:8080/api/review/
  @GetMapping
  @ResponseBody
  public ResponseEntity<List<Review>> getToilets() {
      System.out.println(">>>GET Req: Toilets");
      List<Review> reviews = new LinkedList<>();
  // JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToilets());
  // List<Toilet> toilets = toiletSvc.getToilets()
  //     .stream()
  //     .map(Utils::toToilet)
  //     .toList();
  return ResponseEntity.ok(reviews);
  }

}

