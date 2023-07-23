package potenday.zerowaste.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import potenday.zerowaste.user.CustomUserService;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    public static final String X_AUTH = "Authorization";
    private UserDetailsService userDetailsService;
    @Autowired
    private FirebaseAuth firebaseAuth;
    private CustomUserService customUserService;

    public JwtFilter(CustomUserService userDetailsService, FirebaseAuth firebaseAuth) {
        this.userDetailsService = userDetailsService;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(X_AUTH);
        jwt = jwt.substring("Bearer ".length());

        FirebaseToken verifyIdToken = null;
        try {
            verifyIdToken = firebaseAuth.verifyIdToken(jwt);
        } catch (FirebaseAuthException e) {{
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Token Not Found", e);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Token Not Found", e);
        }

        String uri = request.getRequestURI();

        if(uri.contains("/api") || uri.contains("/questionnaires")){
            filterChain.doFilter(request, response);
            return;
        }

        String uid = verifyIdToken.getUid();
        Authentication authentication = customUserService.getAuthentication(uid);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}