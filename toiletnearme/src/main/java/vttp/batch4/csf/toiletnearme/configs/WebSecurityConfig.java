package vttp.batch4.csf.toiletnearme.configs;

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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
 
        // uses a form, httpBasic loads up browser prompt
        // /login, access login page with no auth 
        .formLogin(form -> form
			.loginPage("/login")
            .successHandler(successHandler)
			.permitAll()
		)

        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry
        -> authorizationManagerRequestMatcherRegistry
            .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
            .requestMatchers("/admin/**").hasAnyRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/oauth/***","/registration/***","/login/**").permitAll()
            .anyRequest().authenticated())

        .sessionManagement(
            httpSecuritySessionManagementConfigurer
            -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
    }

    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        UserDetails user = User.builder()
                .username("user")
                .password(
                    passwordEncoder().encode("password")
                )
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(
                    passwordEncoder().encode("password")
                )
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.debug(securityDebug);
    }

}