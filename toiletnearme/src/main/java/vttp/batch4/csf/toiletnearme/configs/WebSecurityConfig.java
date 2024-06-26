package vttp.batch4.csf.toiletnearme.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import vttp.batch4.csf.toiletnearme.models.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Autowired
    private GoogleSignInHandler successHandler;

    // if true, enables debug
    private boolean securityDebug = false;

    // any request must be made by an authenticated user
    // AccessDeniedException thrown when not authorised
    // Continues upon success
    @Bean
    public SecurityFilterChain oAuth2FilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))

        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(HttpMethod.DELETE).hasAnyAuthority(Role.ROLE_ADMIN.toString())
            .requestMatchers("/api/review/add", "/api/toilet/add", "/api/review/delete", "/api/toilet/delete", "/api/marker/add").authenticated()
            .requestMatchers("/api/jwt/***", "/api/gmap/***", "/api/toilet/***", "/api/***", "/", "/api/jwt/login", "/api/jwt/login2", "/login/oauth2/code/google/***", "/register/***").permitAll()
        );

        // http.formLogin(login -> login
        //     .loginPage("/api/jwt/login")
        //     .permitAll()
        //     );

        http.oauth2Login(login -> login
            .loginPage("/login")
            .successHandler(successHandler)) 
            .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll() // Allow access to the logout endpoint without authentication
            );

        http.httpBasic(Customizer.withDefaults());
            
        http.sessionManagement(
            httpSecuritySessionManagementConfigurer
            -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //Make the below setting as * to allow connection from any hos
        corsConfiguration.setAllowedOrigins(List.of
            ("http://localhost:4200"
            ,"http://localhost:8080"
            ,"http://www.toiletnearme.sg"
            ,"https://vttpb4-michelle-lim.up.railway.app"));

        corsConfiguration.setAllowedMethods(List.of
            ("GET", "POST", "PATCH", "DELETE"));
            
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(securityDebug);
    }
}


// uses a form, httpBasic loads up browser prompt
// http.formLogin(form -> form
//     .loginPage("/login")
//     .successHandler(successHandler))
//     .logout(logout -> logout
//     .logoutUrl("/logout")
//     .logoutSuccessUrl("/"));