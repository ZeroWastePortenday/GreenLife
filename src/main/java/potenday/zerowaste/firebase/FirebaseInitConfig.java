package potenday.zerowaste.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseInitConfig {

    @Bean
    public Firestore firestore() throws IOException {
        ClassPathResource serviceAccountResource = new ClassPathResource("serviceAccountKey.json");
        try (InputStream inputStream = serviceAccountResource.getInputStream()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
            FirestoreOptions options = FirestoreOptions.newBuilder()
                    .setCredentials(credentials)
                    .build();

            Firestore firestore = options.getService();
            return firestore;

        } catch (IOException e) {
            // 예외 처리 로직 추가
            throw new RuntimeException("Failed to read serviceAccountKey.json from Classpath", e);
        }
    }
}
