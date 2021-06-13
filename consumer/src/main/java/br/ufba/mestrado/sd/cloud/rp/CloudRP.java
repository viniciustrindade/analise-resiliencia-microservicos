package br.ufba.mestrado.sd.cloud.rp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
@PropertySource("classpath:cloud.properties")
public class CloudRP {
	
	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
//		builder.setConnectTimeout(Duration.ofMillis(Long.parseLong(requestTimeOut)));
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudRP.class, args);
	}
}
