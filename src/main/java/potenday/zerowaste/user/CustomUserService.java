package potenday.zerowaste.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.*;

import java.util.Objects;

@Service
public class CustomUserService implements UserDetailsService {
    private static final String DEFAULT_NICKNAME_PREFIX = "Guest";

    private static UserRepository userRepository;
    private FirebaseAuth firebaseAuth;

    public CustomUserService(UserRepository userRepository, FirebaseAuth firebaseAuth) {
        this.userRepository = userRepository;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }

    public static User getUserByUid(String uid){
        return (User) userRepository.findByName(uid);
    }

    public static User signUp(String uid) throws FirebaseAuthException {
        User user = User.builder()
                .name(uid)
                .build();

        return userRepository.save(user);
    }

    public static void deleteUser(String uid) {
        User user = userRepository.findByName(uid);

        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new IllegalArgumentException("해당 uid를 가진 사용자가 존재하지 않습니다.");
        }
    }

    public Authentication getAuthentication(String uid){

        User user = getUserByUid(uid);
        if(Objects.isNull(user)){
            throw new EntityNotFoundException("User를 찾지 못했습니다.");
        }
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    private static String generateDefaultNickname() {
        String randomNumber = RandomStringUtils.randomNumeric(4);
        return DEFAULT_NICKNAME_PREFIX + randomNumber;
    }
}