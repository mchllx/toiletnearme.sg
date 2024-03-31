package vttp.batch4.csf.toiletnearme.controllers;


import java.io.StringReader;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import vttp.batch4.csf.exceptions.CreateReviewException;
import vttp.batch4.csf.toiletnearme.models.Review;
import vttp.batch4.csf.toiletnearme.services.ReviewService;

@Controller
@CrossOrigin(origins={
  "http://localhost:4200"
  , "https://vttpb4-michelle-lim.up.railway.app"
  , "http://www.toiletnearme.sg"
  , "http://localhost:8080"})

@RequestMapping(path="/api")
public class ReviewController {

  @Autowired
  private ReviewService reviewSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // @PostMapping(path="/listings")
  // @ResponseBody
  // public ResponseEntity<String> postReview(@RequestBody String payload) throws CreateReviewException {

  //     System.out.println("payload" + payload);

  //       JsonReader jr = Json.createReader(new StringReader(payload));
  //       JsonObject jsonObj = jr.readObject().getJsonObject("review");

  //       System.out.println("json" + jsonObj.toString());

  //       // String id = UUID.randomUUID().toString().substring(0,8);

  //       // orderid, date, name, address, priority, comments, cart 
    
  //       String name = jsonObj.getString("name");
  //       String address = jsonObj.getString("address");
  //       boolean priority = jsonObj.getBoolean("priority");
  //       String comments = jsonObj.getString("comments");

  //        // cart: lineItems: [{}]
  //       JsonObject cart = jsonObj.getJsonObject("cart");
  //       System.out.println("cart" + cart.toString());


  //       Review review = new Review();

  //       System.out.println(">>> check review" + review.getId());

  //       order.setDate(new Date());
  //       order.setName(name);
  //       order.setAddress(address);
  //       order.setPriority(priority);
  //       order.setComments(comments);
  //       order.setCart(new Cart());

  //     if (poSvc.createNewPurchaseOrder(order) == false) {

  //       JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder()
  //         .add("message:", "order already exists");

  //       JsonObject error = jsonObjBuilder.build();

  //       // BAD REQUEST = 400
  //       return ResponseEntity
  //       .status(HttpStatus.BAD_REQUEST)
  //       .contentType(MediaType.APPLICATION_JSON)
  //       .body(error.toString());
  //     }

  //     JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder()
  //         .add("id:", order.getOrderId());
  //         // .add("name:", order.getName())
  //         // .add("address:", order.getAddress())
  //         // .add("priority:", order.getPriority())
  //         // .add("comments:", order.getComments())
  //         // .add("cart:", order.getCart().toString());

  //       JsonObject success = jsonObjBuilder.build();
    
  //     // OK = 200
  //     return ResponseEntity
  //     .status(HttpStatus.OK)
  //     .contentType(MediaType.APPLICATION_JSON)
  //     .body(success.toString());
  // }
}
