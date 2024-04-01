package vttp.batch4.csf.toiletnearme.configs;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.services.UserService;

@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService userSvc;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        String redirectUrl = null;
        System.out.println("Successfully authenticated");

        // if auth username is instance of oa2user
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
           DefaultOAuth2User userDetails = (DefaultOAuth2User)authentication.getPrincipal();

           String email = userDetails.getAttribute("email")
           !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"gmail.com";

           if (userSvc.selectByEmail(email) == null) {
                User user = new User();

                user.setUsername(null);
                user.setEmail(email);
                user.setPassword(Utils.newUUID());
                user.setRole("USER");

            try {
                userSvc.insertNewUser(user);
            } catch (InsertUserException e1) {
                e1.printStackTrace();
            }

           }
        }
        redirectUrl = "/";
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);

    }
    
}
