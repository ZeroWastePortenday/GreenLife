package potenday.zerowaste.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository; // bean 주입

    public List<User> getAllUserss(){
        return userRepository.findAll();
    }
}
