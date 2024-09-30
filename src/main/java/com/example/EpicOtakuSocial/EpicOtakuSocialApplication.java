package com.example.EpicOtakuSocial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class EpicOtakuSocialApplication {


	public static void main(String[] args) {

		SpringApplication.run(EpicOtakuSocialApplication.class, args);
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.jikan.moe/v4/anime/21";
		String response = restTemplate.getForObject(url, String.class);
		//mi ritorna l'oggetto anime ma come stringa
		//per averla come obj devo creare una classe anime con tutti gli argomenti e usarla come tipo di response

	}

}
