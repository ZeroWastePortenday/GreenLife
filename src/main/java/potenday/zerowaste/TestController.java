package potenday.zerowaste;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello, world!!";
    }

    /*
        firebase secret key generator
    */
    @GetMapping("/firebase_init")
    public String firebase_init() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        return "firebase Admin SDK done";
    }

    /*
        resources/serveiceAccountKey.json 파일로 엑세스 테스트
        앞서 설정한 FirebaseOptions를 사용하여 FirebaseApp을 초기화합니다. 이 코드는 Firebase 애플리케이션을 설정하고 Firebase 서비스에 연결합니다.
        이를 통해 Firebase 서비스를 사용할 수 있게 되며, Firebase Cloud Messaging, Firebase Realtime Database, Firebase Authentication 등과 같은 서비스에 액세스할 수 있습니다.
    */
    @GetMapping("/firebase_endpoint_test")
    public String firebase_endpoint_test() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        return "firebase endpoint test done";
    }

}
