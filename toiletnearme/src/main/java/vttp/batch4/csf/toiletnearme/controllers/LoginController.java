package vttp.batch4.csf.toiletnearme.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.batch4.csf.toiletnearme.services.JWTService;

@Controller
@RequestMapping(path="/")
public class LoginController {

    @Autowired
    JWTService jwtSvc; 

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @GetMapping(path="/login")
    public String login() {
        logger.info("logging in");
        return "login";
    }

    @GetMapping(path="/logout")
    public String logout() {
        logger.info("logged out");
        return "index";
    }

    @GetMapping(path="/form")
    public String form() {
        logger.info("form");
        return "form";
    }

    // JWT Filter blocks requests without headers before reach controller
    @PostMapping(path="/authorization/jwt")
    public ResponseEntity<String> jwt(@RequestParam("username") String username) {
        
        return ResponseEntity
            .status(HttpStatus.OK)
            // .headers()
            .body("authenticated");
    }

    @GetMapping(path="/user")
    public String user() {
        logger.info("user");
        return "user";
    }
}