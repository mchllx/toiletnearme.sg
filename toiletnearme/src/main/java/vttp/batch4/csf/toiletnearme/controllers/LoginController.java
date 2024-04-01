package vttp.batch4.csf.toiletnearme.controllers;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/")
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @GetMapping(path="/login")
    public String login() {
        logger.info("redirecting to login");
        return "index";
    }

    @GetMapping(path="/logout")
    public String logout() {
        logger.info("redirecting to logout");
        return "index";
    }
}