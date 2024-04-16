package vttp.batch4.csf.toiletnearme.controllers;

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
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.models.Toilet;
import vttp.batch4.csf.toiletnearme.services.ToiletService;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

// TODO: add headers for jwt
@Controller
@CrossOrigin()
@RequestMapping(path="/api/toilet", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToiletController {

    @Autowired
    private ToiletService toiletSvc;
 
    // GET http://localhost:8080/api/toilet/
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Toilet>> getToilets() {
        System.out.println(">>>GET Req: Toilets");
        List<Toilet> toilets = new LinkedList<>();
    // JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToilets());
    // List<Toilet> toilets = toiletSvc.getToilets()
    //     .stream()
    //     .map(Utils::toToilet)
    //     .toList();
    return ResponseEntity.ok(toilets);
    }

    // POST http://localhost:8080/api/toilet/{toilet}")
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> postToilets(@RequestBody String payload) {
        System.out.println("payload" + payload);

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jr.readObject().getJsonObject("order");
        System.out.println("json" + jsonObj.toString());

        Toilet toilet = new Toilet();
    
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
        .body(Json.createObjectBuilder().add("id:", toilet.getToiletId()).build().toString());
    }

    // DELETE http://localhost:8080/api/toilet/{id}")
    @DeleteMapping(path="{id}")
    @ResponseBody
    public ResponseEntity<String> deleteToilet(@PathVariable String id, Toilet toilet) {
        System.out.println(">>>DELETE Req: Toilet");

        // if (toiletSvc.getToiletByID(id) == false) {
        //     return ResponseEntity
        //     .status(HttpStatus.BAD_REQUEST)
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .body(Json.createObjectBuilder().add("message:", "toilet does not exists").build().toString());
        // }

    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(Json.createObjectBuilder().add("id:", toilet.getToiletId()).build().toString());
    }
    

    // GET http://localhost:8080/api/toilet/address
    @GetMapping(path="/address")
    @ResponseBody
    public ResponseEntity<String> getAddress() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletAddress());
        System.out.println(">>>GET Req: Address");

    return ResponseEntity.ok(arrBuilder.build().toString());
    }

    // GET http://localhost:8080/api/toilet/address?region={region}")
    // OR any single param str
    @GetMapping(path="/address/{param}")
    @ResponseBody
    public ResponseEntity<String> getAddressByRegion(@RequestParam String param) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletAddressByRegion(param));
        System.out.println(">>>GET Req: Address By "+param);

    return ResponseEntity.ok(arrBuilder.build().toString());
    }
    
}
