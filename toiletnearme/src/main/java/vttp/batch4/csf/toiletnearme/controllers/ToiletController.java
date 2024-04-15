package vttp.batch4.csf.toiletnearme.controllers;

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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller
@CrossOrigin()
@RequestMapping(path="/api/toilet", produces = MediaType.APPLICATION_JSON_VALUE)
public class ToiletController {

    @Autowired
    private ToiletService toiletSvc;

    @GetMapping(path="/address")
    @ResponseBody
    public ResponseEntity<String> getAddress() {
    JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletAddress());

    System.out.println(">>>Received GET: Address");

    return ResponseEntity.ok(arrBuilder.build().toString());
    }

    @GetMapping(path="/address/{region}")
    @ResponseBody
    public ResponseEntity<String> getAddressByRegion(@PathVariable String region) {

    JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletAddressByRegion(region));
    return ResponseEntity.ok(arrBuilder.build().toString());
    }

    // api/toilet/address/type=<type>
    @GetMapping(path="/address/type={type}")
    @ResponseBody
    public ResponseEntity<String> getAddressByType(@PathVariable String type) {

    JsonArrayBuilder arrBuilder = Json.createArrayBuilder(toiletSvc.getToiletAddressByRegion(type));
    return ResponseEntity.ok(arrBuilder.build().toString());
    }

    // @GetMapping(path="/address/{region}")
    // @ResponseBody
    // public ResponseEntity<String> getToiletsByRegion(@PathVariable String region) {

    // List<JsonObject> toilets = toiletSvc.getToiletAddressByRegion(region)
    //     .stream()
    //     .map(Utils::toJson)
    //     .toList();

    // return ResponseEntity.ok(Json.createArrayBuilder(toilets).build().toString());
    // }
    
}
