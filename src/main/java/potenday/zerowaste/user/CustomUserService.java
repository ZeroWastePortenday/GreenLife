package potenday.zerowaste.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }

    @Transactional
    public User register(String uid, String email, String nickname) {
        User customUser = User.builder()
                .username(uid)
                .email(email)
                .nickname(nickname)
                .build();
        userRepository.save(customUser);
        return customUser;
    }
}