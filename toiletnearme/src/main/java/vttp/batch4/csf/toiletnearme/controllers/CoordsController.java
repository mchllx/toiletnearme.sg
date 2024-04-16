package vttp.batch4.csf.toiletnearme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch4.csf.toiletnearme.models.Coordinates;
import vttp.batch4.csf.toiletnearme.repositories.CoordsRepository;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@CrossOrigin()
@RequestMapping(path="/api/marker", produces = MediaType.APPLICATION_JSON_VALUE)
public class CoordsController {

    @Autowired
    private CoordsRepository coordsRepo;

  // POST http://localhost:8080/api/marker/{marker}")
    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity<String> postMarker(@RequestBody String payload) {
        System.out.println("payload" + payload);

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jr.readObject().getJsonObject("marker");
        System.out.println("json" + jsonObj.toString());
    
        // String name = jsonObj.getString("name");
        // String address = jsonObj.getString("address");
        // boolean priority = jsonObj.getBoolean("priority");
        // String comments = jsonObj.getString("comments");

        // JsonObject cart = jsonObj.getJsonObject("cart");
        // System.out.println("cart" + cart.toString());

      if (coordsRepo.updateCoords(jsonObj) < 0) {
        // BAD REQUEST = 400
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Json.createObjectBuilder().add("message:", "coords already exists").build().toString());
      }
    
      // OK = 200
      return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Json.createObjectBuilder().add("inserted:", jsonObj).build().toString());
    }

  @DeleteMapping(path="/delete/{id}")
  @ResponseBody
  public ResponseEntity<String> deleteToilet(@PathVariable String id) {
      System.out.println(">>>DELETE Req: Coords");

      if (coordsRepo.deleteCoords(id) < 0) {
        return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(Json.createObjectBuilder().add("message:", "invalid request").build().toString());
      }

      coordsRepo.deleteCoords(id);

  return ResponseEntity
    .status(HttpStatus.OK)
    .contentType(MediaType.APPLICATION_JSON)
    .body(Json.createObjectBuilder().add("deleted", id).build().toString());
  }
}