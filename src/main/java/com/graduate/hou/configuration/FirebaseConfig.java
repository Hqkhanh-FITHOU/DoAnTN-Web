package com.graduate.hou.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {
    private final FirebaseProperties firebaseProperties;

    @PostConstruct
    public void initialize() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/firebase-adminsdk.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    
    // @Bean
    // FirebaseMessaging firebaseMessaging() throws IOException {
    //     GoogleCredentials googleCredentials=GoogleCredentials
    //             .fromStream(new FileInputStream(firebaseProperties.getGoogleCredentials()));
    //     FirebaseOptions firebaseOptions = FirebaseOptions
    //             .builder()
    //             .setCredentials(googleCredentials)
    //             .build();
    //     FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
    //     return FirebaseMessaging.getInstance(app);
    // }



    

}
