package photonet.server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import photonet.server.domain.service.UserService;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .logout().and()
                .authorizeRequests()
                .antMatchers(Endpoints.HOME,Endpoints.LOGIN).permitAll()
                .antMatchers(HttpMethod.GET,"/h2-console/**").hasRole(Roles.ADMIN)
                .anyRequest().authenticated();
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.cors();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::getDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String ALLOWED_METHODS = "*";
    private static final String ALLOWED_HEADERS = "*";
    private static final String EXPOSED_HEADERS = "Content-Disposition";
    private static final String PATH = "/**";

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Collections.singletonList(ALLOWED_METHODS));
        configuration.setAllowedHeaders(Collections.singletonList(ALLOWED_HEADERS));
        configuration.setExposedHeaders(Collections.singletonList(EXPOSED_HEADERS));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(PATH, configuration);
        return source;
    }
}
