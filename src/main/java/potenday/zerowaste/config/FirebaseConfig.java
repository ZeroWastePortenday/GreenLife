package potenday.zerowaste.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {

        ClassPathResource serviceAccountResource = new ClassPathResource("serviceAccountKey.json");
        try (InputStream inputStream = serviceAccountResource.getInputStream()) {
            String serviceAccountJson = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ByteArrayInputStream(serviceAccountJson.getBytes())))
                    .build();
            FirebaseApp.initializeApp(options);

            return FirebaseAuth.getInstance(FirebaseApp.getInstance());
        } catch (IOException e) {
            // 예외 처리 로직 추가
            throw new RuntimeException("Failed to read serviceAccountKey.json from Classpath", e);
        }
    }
}