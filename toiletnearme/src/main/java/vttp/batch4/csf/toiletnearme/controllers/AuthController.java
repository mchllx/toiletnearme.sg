package vttp.batch4.csf.toiletnearme.controllers;

import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.services.JWTService;
import vttp.batch4.csf.toiletnearme.services.UserService;

@Controller
@RequestMapping(path="/api/jwt")
public class AuthController {

    @Autowired
    JWTService jwtSvc;
    
    @Autowired
    UserService userSvc; 

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    // JWT Filter blocks requests without headers before reach controller
    @GetMapping(path="/login")
    @ResponseBody
    public ResponseEntity<String> login(String payload) {
        System.out.println("payload" + payload);
        System.out.println(">>>GET Req: Login");

        User user = new User();

        // if (userSvc.selectUserById(email) == false) {
            
        // return ResponseEntity
        //     .status(HttpStatus.BAD_REQUEST)
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .body(Json.createObjectBuilder().add("message:", "invalid user").build().toString());
        // }

        // if (jwtSvc.extractExpiration(jwtToken).after(new Date())) {
            
        //     return ResponseEntity
        //         .status(HttpStatus.BAD_REQUEST)
        //         .contentType(MediaType.APPLICATION_JSON)
        //         .body(Json.createObjectBuilder().add("message:", "token is not expired").build().toString());
        //     }
    
        // String email = jwtSvc.extractEmail(jwtToken);
        // String token = jwtSvc.generateToken("");

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("logged in:", user.getEmail()).build().toString());
    }

    @GetMapping(path="/register")
    @ResponseBody
    public ResponseEntity<String> register(String payload) throws InsertUserException {
        System.out.println("payload" + payload);
        System.out.println(">>>GET Req: Registration");

        // if (userSvc.selectUserById(email) != false) {
            
        // return ResponseEntity
        //     .status(HttpStatus.BAD_REQUEST)
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .body(Json.createObjectBuilder().add("message:", "user exists").build().toString());
        // }

        User user = new User();
        userSvc.insertUser(new User());

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("registered", user.getEmail()).build().toString());
    }

    // @GetMapping(path="/login")
    // public String login() {
    //     return "login";
    // }

    // @GetMapping(path="/form")
    // public String form() {
    //     return "form";
    // }

}