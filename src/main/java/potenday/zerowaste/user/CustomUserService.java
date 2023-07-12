package potenday.zerowaste.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.*;

import java.util.Objects;
import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {
    private static final String DEFAULT_NICKNAME_PREFIX = "Guest";
    @Autowired
    static UserRepository userRepository;
    @Autowired
    static FirebaseAuth firebaseAuth;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }

    public static User getUserByUid(String uid){
        return (User) userRepository.findByName(uid);
    }

    public static User signUp(String uid) throws FirebaseAuthException {
        firebaseAuth.getUserByEmail(uid);
        UserRecord userRecord = firebaseAuth.getUser(uid);

        User user = User.builder()
                .username(userRecord.getUid())
                .email(userRecord.getDisplayName())
                .nickname(Optional.ofNullable(userRecord.getDisplayName()).orElseGet(() -> generateDefaultNickname()))
                .build();

        return userRepository.save(user);
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