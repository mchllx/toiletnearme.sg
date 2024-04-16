package vttp.batch4.csf.toiletnearme.controllers;

import java.io.StringReader;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.configs.GoogleSignInHandler;
import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.models.Role;
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
    
    @Autowired
    GoogleSignInHandler googleHandler; 

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    // JWT Filter blocks requests without headers before reach controller
    @GetMapping(path="/login")
    @ResponseBody
    public ResponseEntity<String> login(String payload) {
        System.out.println("payload" + payload);
        System.out.println(">>>GET Req: Login");

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jr.readObject().getJsonObject("order");

        String email = "";
        String token = "";

        User user = new User();

        if (userSvc.selectUserById(email) == null) {
            
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("message:", "invalid user").build().toString());
        }

        if (jwtSvc.extractExpiration(token).after(new Date())) {
            
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Json.createObjectBuilder().add("message:", "token is not expired").build().toString());
            }
            
        email = jwtSvc.extractEmail(token);
        token = jwtSvc.generateToken("");

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("logged in:", user.getEmail()).build().toString());
    }

    @PostMapping(path="/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestParam String username, String password, String firstName, String lastName, String profileImage) throws InsertUserException {
        // System.out.println("payload" + payload);
        System.out.println(">>>GET Req: Registration");

        String email = username;

        if (userSvc.selectUserById(email) != null) {
            
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("message:", "user exists").build().toString());
        }
    
        Set<Role> authorities = new HashSet<>();
        authorities.add(Role.ROLE_USER);
        
        User user = new User();
        user.setUserId(Utils.createUUID26Char(Utils.PREFIX_USER));
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(googleHandler.passwordEncoder().encode(email));
        user.setCreatedOn(new Date());
        user.setUpdatedOn(new Date());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setProfileImage(profileImage);
        
        user.setAuthorities(authorities);
        // System.out.println(">>>sending user data to mySQL");
        
        try {
            userSvc.insertUser(user);
        } catch (InsertUserException e1) {
            System.out.println(">>>Email exists in database");
        }
        
        System.out.println(">>>Generating JWT" + email);
        String token = jwtSvc.generateToken(email);
        // Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c 

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("registered", token).build().toString());
    }

    @GetMapping(path="/login2")
    public String login2() {
        return "login";
    }

    @GetMapping(path="/form")
    public String form() {
        return "form";
    }

}