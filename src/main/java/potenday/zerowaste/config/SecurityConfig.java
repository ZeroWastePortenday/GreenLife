package potenday.zerowaste.config;

import com.google.firebase.auth.FirebaseAuth;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import potenday.zerowaste.filter.JwtFilter;
import potenday.zerowaste.user.CustomUserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private CustomUserService userDetailsService;

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/resources/**");
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests() // 인증, 인가 설정
                .requestMatchers("/api/v1/login", "/api/v1/signup", "/api/v1/delete", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .build();

    }

}