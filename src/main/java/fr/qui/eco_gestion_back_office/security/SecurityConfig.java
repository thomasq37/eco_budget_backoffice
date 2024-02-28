package fr.qui.eco_gestion_back_office.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fr.qui.eco_gestion_back_office.security.jwt.AuthEntryPointJwt;
import fr.qui.eco_gestion_back_office.security.jwt.AuthTokenFilter;
import fr.qui.eco_gestion_back_office.security.jwt.JwtUtils;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    
    private final AuthEntryPointJwt unauthorizedHandler; 
    private final JwtUtils jwtUtils;
    private final CustomUtilisateurDetailsService customUtilisateurDetailsService;

    
    public SecurityConfig(AuthEntryPointJwt unauthorizedHandler, JwtUtils jwtUtils, CustomUtilisateurDetailsService customUtilisateurDetailsService) {
    	this.unauthorizedHandler = unauthorizedHandler;
    	this.jwtUtils = jwtUtils;
    	this.customUtilisateurDetailsService = customUtilisateurDetailsService;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter(jwtUtils, customUtilisateurDetailsService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(customize -> customize.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> {
                authorize.requestMatchers("/api/auth/**").permitAll();
                authorize.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").hasAuthority("ADMIN");
                authorize.anyRequest().authenticated();
            });

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
