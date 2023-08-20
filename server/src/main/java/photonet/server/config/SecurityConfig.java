package photonet.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import photonet.server.domain.service.UserService;

import java.util.Collections;
import java.util.List;

import static photonet.server.config.Endpoints.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final List<String> ALLOWED_ORIGINS = List.of("http://localhost:4200", "http://127.0.0.1:4200");
    private static final String ALLOWED_METHODS = "*";
    private static final String ALLOWED_HEADERS = "*";
    private static final String EXPOSED_HEADERS = "Content-Disposition";
    private static final String PATH = "/**";

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic().and()
                .logout().and()
                .authorizeRequests()
                .antMatchers(HOME, LOGIN, REGISTER).permitAll()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ALLOWED_ORIGINS);
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Collections.singletonList(ALLOWED_METHODS));
        configuration.setAllowedHeaders(Collections.singletonList(ALLOWED_HEADERS));
        configuration.setExposedHeaders(Collections.singletonList(EXPOSED_HEADERS));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PATH, configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getSecurityDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
