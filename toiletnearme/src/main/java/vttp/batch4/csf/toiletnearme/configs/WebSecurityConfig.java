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
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    @Autowired
    CustomSuccessHandler successHandler;

    // if true, enables debug
    private boolean securityDebug = false;

    // any request must be made by an authenticated user
    // AccessDeniedException thrown when not authorised
    // Continues upon success
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
            .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))

        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(HttpMethod.DELETE).hasAnyAuthority("ADMIN")
            .requestMatchers("/add").authenticated()
            .requestMatchers("/", "/login", "/login/oauth2/code/google/***", "/register/***").permitAll()
        );

        // uses a form, httpBasic loads up browser prompt
        http.formLogin(form -> form
            .loginPage("/login")
            .successHandler(successHandler));

        http.oauth2Login(login -> login
            .loginPage("/login")
            .successHandler(successHandler)) 
            .logout(logout -> logout
            .logoutUrl("/logout") // Specify the logout URL
            .logoutSuccessUrl("/") // Redirect to homepage after logout
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

    return http.build();
    }

    // @Bean
    // public UserDetailsService users() {
    //     // The builder will ensure the passwords are encoded before saving in memory
    //     UserDetails user = User.builder()
    //             .username("user")
    //             .password(
    //                 passwordEncoder().encode("password")
    //             )
    //             .roles("USER")
    //             .build();
    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(
    //                 passwordEncoder().encode("password")
    //             )
    //             .roles("USER", "ADMIN")
    //             .build();
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

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