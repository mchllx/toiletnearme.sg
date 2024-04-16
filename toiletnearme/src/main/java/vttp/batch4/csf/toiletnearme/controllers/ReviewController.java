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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.services.ReviewService;
import vttp.batch4.csf.toiletnearme.services.UserService;


// GET http://localhost:8080/api/review/{id}")
// GET http://localhost:8080/api/review/user/{region}")
// POST http://localhost:8080/api/review/")
// DELETE http://localhost:8080/api/toilet/address/{id}")

@Controller
@CrossOrigin()
@RequestMapping(path="/api/review")
public class ReviewController {

  @Autowired
  private ReviewService reviewSvc;

  @Autowired
  private UserService userSvc;

  // TODO: add headers for token, complete after toilet ones work
  // GET http://localhost:8080/api/review/
  @GetMapping
    @ResponseBody
    public ResponseEntity<List<JsonObject>> getReviews() {
        System.out.println(">>>GET Req: Toilets");

        if (reviewSvc.getReviews() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LinkedList<>());
        }

        List<JsonObject> reviews = reviewSvc.getReviews()
            .stream()
            .map(Utils::reviewToJson)
            .toList();
            
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(reviews);
  }

  // TODO: PUT http://localhost:8080/api/review/")
  // POST http://localhost:8080/api/review/")
  @PostMapping(path="/add")
  @ResponseBody
  public ResponseEntity<String> postReview(@RequestBody String payload) {
      System.out.println("payload" + payload);

      JsonReader jr = Json.createReader(new StringReader(payload));
      JsonObject jsonObj = jr.readObject();
      System.out.println("json" + jsonObj.toString());

      Review review = new Review();
  
      // String name = jsonObj.getString("name");
      // String address = jsonObj.getString("address");
      // boolean priority = jsonObj.getBoolean("priority");
      // String comments = jsonObj.getString("comments");

      // JsonObject cart = jsonObj.getJsonObject("cart");
      // System.out.println("cart" + cart.toString());

  //   if (toiletSvc.insertToilet(toilet) == false) {

  //     // BAD REQUEST = 400
  //     return ResponseEntity
  //     .status(HttpStatus.BAD_REQUEST)
  //     .contentType(MediaType.APPLICATION_JSON)
  //     .body(Json.createObjectBuilder().add("message:", "toilet already exists").build().toString());
  //   }
  
    // OK = 200
    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(Json.createObjectBuilder().add("id:", review.getToiletId()).build().toString());
  }

  // DELETE http://localhost:8080/api/review/delete/{id}")
  @DeleteMapping(path="/delete/{id}")
  @ResponseBody
  public ResponseEntity<String> deleteToilet(@PathVariable String id, @RequestParam String email) {
      System.out.println(">>>DELETE Req: Review");

      if (reviewSvc.getReviewByID(id) == null) {
          return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Json.createObjectBuilder().add("message:", "review does not exist").build().toString());
      }

      Review review = reviewSvc.getReviewByID(id);
      User user = userSvc.selectUserById(email);

      if (user.getUserId() != review.getUserId()) {
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Json.createObjectBuilder().add("message:", "invalid access").build().toString());
      }
      reviewSvc.deleteReviewByID(id);

  return ResponseEntity
    .status(HttpStatus.OK)
    .contentType(MediaType.APPLICATION_JSON)
    .body(Json.createObjectBuilder().add("deleted", id).build().toString());
  }


}

