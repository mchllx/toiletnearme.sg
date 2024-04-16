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
    @PostMapping(path="/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestBody String payload) {
        // payload{"user":{"email":"tester@gmail.com","password":"tester"}}
        // System.out.println("Payload" + payload);
        System.out.println(">>>POST Req: Login");

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jr.readObject().getJsonObject("user");

        String email = jsonObj.getString("email");

        if (userSvc.selectUserByEmail(email) == null) {
            
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("message:", "invalid user").build().toString());
        }

        String password = jsonObj.getString("password");
        User user = userSvc.selectUserByEmail(email);
        boolean matches = googleHandler.passwordEncoder().matches(password, user.getPassword());

        if (!matches) {
            return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Json.createObjectBuilder().add("message:", "invalid user").build().toString());
        }

        String token = jwtSvc.generateToken(email);

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Utils.userToJson(user, token).toString());
    }

    // user_id, username, email, password, created_on, last_update, first_name, last_name, profile_image, role, expired, enabled, locked, credentials
    @PostMapping(path="/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody String payload) throws InsertUserException {
        // System.out.println("payload" + payload);
        System.out.println(">>>POST Req: Registration");

        JsonReader jr = Json.createReader(new StringReader(payload));
        JsonObject jsonObj = jr.readObject().getJsonObject("user");

        String username = jsonObj.getString("username");
        String email = jsonObj.getString("email");
        String password = jsonObj.getString("password");
        String firstName = jsonObj.getString("firstName");
        String lastName = jsonObj.getString("lastName");
        String profileImage = jsonObj.getString("profileImage");

        if (userSvc.selectUserByEmail(email) != null) {
            
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
        user.setPassword(googleHandler.passwordEncoder().encode(password));
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

    // @GetMapping(path="/login2")
    // public String login2() {
    //     return "login";
    // }

    // @GetMapping(path="/form")
    // public String form() {
    //     return "form";
    // }

    // if (jwtSvc.extractExpiration(token).after(new Date())) { 
    //     return ResponseEntity
    //         .status(HttpStatus.BAD_REQUEST)
    //         .contentType(MediaType.APPLICATION_JSON)
    //         .body(Json.createObjectBuilder().add("message:", "token is not expired").build().toString());
    //     }
        
    // email = jwtSvc.extractEmail(token);

}