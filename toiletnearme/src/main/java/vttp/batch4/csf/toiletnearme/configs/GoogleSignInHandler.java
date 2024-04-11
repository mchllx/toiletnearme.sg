package vttp.batch4.csf.toiletnearme.configs;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.api.client.util.Value;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp.batch4.csf.toiletnearme.Utils;
import vttp.batch4.csf.toiletnearme.exceptions.InsertUserException;
import vttp.batch4.csf.toiletnearme.models.Role;
import vttp.batch4.csf.toiletnearme.models.User;
import vttp.batch4.csf.toiletnearme.services.JWTService;
import vttp.batch4.csf.toiletnearme.services.UserService;

@Configuration
public class GoogleSignInHandler implements AuthenticationSuccessHandler {

    @Value("${oauth2.admin}")
    private String adminEmail;

    @Autowired
    UserService userSvc;

    @Autowired
    JWTService jwtSvc;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        String redirectUrl = null;
        
        // at_hash=, sub=, email_verified=, iss=, given_name=
        // , nonce=, picture=https://lh3.googleusercontent.com/a/, aud=, azp=, name=
        // , exp/expiration=date, family_name=, iat/issued at=, email=
        // if auth username is instance of oa2user
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User userDetails = (DefaultOAuth2User)authentication.getPrincipal();
            
            String firstName = userDetails.getAttribute("given_name"); 
            String lastName = userDetails.getAttribute("family_name"); 
            String email = userDetails.getAttribute("email")
            !=null?userDetails.getAttribute("email"):userDetails.getAttribute("login")+"gmail.com"; 
            String username = email.replace("@gmail.com", Utils.createUUID8Char()); 
            // Placeholder
            String profileImage = userDetails.getAttribute("picture")
            !=null?userDetails.getAttribute("picture"):"https://t4.ftcdn.net/jpg/05/42/36/11/360_F_542361185_VFRJWpR2FH5OiAEVveWO7oZnfSccZfD3.jpg";
            
            //    System.out.println(">>>email:" +email);
            //    System.out.println(">>>details:" +userDetails.getAttributes());
            
            if (userSvc.selectUserByEmail(email) == null) {
                Set<Role> authorities = new HashSet<>();
                authorities.add(Role.ROLE_USER);
                
                if (email == adminEmail) {
                    authorities.remove(Role.ROLE_USER);
                    authorities.add(Role.ROLE_ADMIN);
                } 
                
                User user = new User();
                
                user.setUserId(Utils.createUUID26Char());
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(passwordEncoder().encode(email));
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
                    e1.printStackTrace();
                }
                
                user = userSvc.selectUserByEmail(email);
                
                System.out.println(">>>Generating JWT\n" + user.getEmail());
                String token = jwtSvc.generateToken(email);
                System.out.println("token:" +token);
                // Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
                
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                System.out.println("header:" + headers.toString());
                response.setHeader(headers.toString(), token);

            }
        }
        System.out.println(">>>Successful OAuth2.0 authentication");
        redirectUrl = "/";
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
