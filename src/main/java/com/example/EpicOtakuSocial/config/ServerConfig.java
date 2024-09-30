package com.example.EpicOtakuSocial.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServerConfig {

    @Bean
    public Cloudinary imageUpload(@Value("${cloudinary.name}") String name,
                                  @Value("${cloudinary.key}") String key,
                                  @Value("${cloudinary.secret}") String secret) {
        Map<String, String> configuration = new HashMap<>();
        configuration.put("cloud_name", name);
        configuration.put("api_key", key);
        configuration.put("api_secret", secret);

        return new Cloudinary(configuration);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    } //È uno strumento semplice e potente per inviare richieste HTTP,
    // come GET, POST, PUT, DELETE, e per ricevere risposte da servizi RESTful.
    // Può essere utilizzato per interagire con API di terze part

}
