package potenday.zerowaste.user;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;

@RestController
public class UserController {

    private final Firestore firestore;

    public UserController(Firestore firestore) {
        this.firestore = firestore;
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

