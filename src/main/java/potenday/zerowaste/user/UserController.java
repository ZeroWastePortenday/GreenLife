package potenday.zerowaste.user;

import com.google.firebase.auth.*;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potenday.zerowaste.common.CommonHandler;
import potenday.zerowaste.common.CommonResult;
import potenday.zerowaste.common.ResponseService;

import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    public static final String X_AUTH = "Authorization";
    private final Firestore firestore;
    private final FirebaseAuth firebaseAuth;
    @Autowired
    private ResponseService responseService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);
        jwt = jwt.substring("Bearer ".length());

        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(jwt);
        String uid = firebaseToken.getUid();
        String nickname = request.getParameter("nickname");

        System.out.println("gggg" + nickname);

        User user = CustomUserService.signUp(uid, nickname);

        return ResponseEntity.ok(responseService.getSingleResult(UserDto.of(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);
        jwt = jwt.substring("Bearer ".length());

        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(jwt);
        String uid = firebaseToken.getUid();
        User user = CustomUserService.getUserByUid(uid);

        if(Objects.isNull(user)){
            CommonHandler responseDto = new CommonHandler(false, 403, "no user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseDto);
        }

        return ResponseEntity.ok(responseService.getSingleResult(UserDto.of(user)));
    }

    @PostMapping("/delete")
    public void delete(HttpServletRequest request) throws FirebaseAuthException {

        String jwt = request.getHeader(X_AUTH);
        jwt = jwt.substring("Bearer ".length());

        FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(jwt);
        String uid = firebaseToken.getUid();
        CustomUserService.deleteUser(uid);
    }

    @GetMapping("/user_all")
    public String user_all() {

        try {
            // Firestore 컬렉션에서 User 문서 가져오기
            CollectionReference userCollection = firestore.collection("User");
            userCollection.get().get().forEach(queryDocumentSnapshot -> {
                String userId = queryDocumentSnapshot.getId();
                User user = queryDocumentSnapshot.toObject(User.class);
                // to-be::유저 모델에 저장
                System.out.println("firebase_hash: " + userId);
                System.out.println("Name: " + user.getName());
                System.out.println("email: " + user.getEmail());
                // 추가 필드가 있다면 이곳에서 가져오기
                // ...
            });

            // FirebaseApp 종료
            firestore.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello";
    }
}

