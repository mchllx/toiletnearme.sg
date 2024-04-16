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
import vttp.batch4.csf.toiletnearme.models.Role;
import vttp.batch4.csf.toiletnearme.models.Toilet;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.services.JWTService;
import vttp.batch4.csf.toiletnearme.services.ToiletService;
import vttp.batch4.csf.toiletnearme.services.UserService;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private UserService userSvc;
 
    // GET http://localhost:8080/api/toilet/
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<JsonObject>> getToilets() {
        System.out.println(">>>GET Req: Toilets");

        if (toiletSvc.getToilets() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new LinkedList<>());
        }

        List<JsonObject> toilets = toiletSvc.getToilets()
            .stream()
            .map(Utils::toiletToJson)
            .toList();
            
    return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(toilets);
    }

    // TODO: UPDATE/PUT http://localhost:8080/api/toilet/{toilet}")
    // POST http://localhost:8080/api/toilet/{toilet}")
    @PostMapping
    @ResponseBody
    public ResponseEntity<String> postToilet(@RequestBody String payload) {
        System.out.println("payload" + payload);

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jr.readObject();
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
    public ResponseEntity<String> deleteToilet(@PathVariable String id, @RequestParam String email, Toilet toilet) {
        System.out.println(">>>DELETE Req: Toilet");

        if (toiletSvc.getToiletByID(id) == null) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("message:", "toilet does not exist").build().toString());
        }

        User user = userSvc.selectUserByEmail(email);
        Set<Role> authorities = user.getAuthorities();

        if (authorities.contains(Role.ROLE_USER)) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("message:", "invalid access").build().toString());
        }

        toiletSvc.deleteToiletByID(id);

    return ResponseEntity
      .status(HttpStatus.OK)
      .contentType(MediaType.APPLICATION_JSON)
      .body(Json.createObjectBuilder().add("deleted:", id).build().toString());
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
