package potenday.zerowaste.filter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import potenday.zerowaste.user.CustomUserService;
import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    public static final String X_AUTH = "x-auth";
    private UserDetailsService userDetailsService;
    private FirebaseAuth firebaseAuth;
    private CustomUserService customUserService;

    public JwtFilter(UserDetailsService userDetailsService, FirebaseAuth firebaseAuth) {
        this.userDetailsService = userDetailsService;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(X_AUTH);
        String uri = request.getRequestURI();

        if(uri.contains("/api/v1/login") || uri.contains("/questionnaires")){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            FirebaseToken verifyIdToken = firebaseAuth.verifyIdToken(jwt);
            String uid = verifyIdToken.getUid();
            Authentication authentication = customUserService.getAuthentication(uid);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (FirebaseAuthException e) {
            log.error(e.getMessage());
        }

        filterChain.doFilter(request, response);

    }
}