package br.ufba.mestrado.sd.microservices;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@EnableScheduling
@SpringBootApplication
@PropertySource("classpath:microservice.properties")
public class MicroServices {

	@Value("${requestTimeOut}")
	private String requestTimeOut;

	@Autowired
	private CircuitBrakerService circuitBrakerService;

	@Autowired
	private RetryPatternService retryPatternService;

	@Autowired
	private NonResilientService nonResilientService;

	@Bean
	public RestTemplate rest(RestTemplateBuilder builder) {
		return builder
				.setReadTimeout(Duration.ofMillis(Long.parseLong(requestTimeOut)))
				.setConnectTimeout(Duration.ofMillis(Long.parseLong(requestTimeOut)))
				.build();
	}

	@RequestMapping("/get-images-from-broker-cb")
	public String getImagesFromBrokerCB() {
		return circuitBrakerService.getImagesFromBroker();
	}

	@RequestMapping("/get-images-from-broker-rp")
	public String getImagesFromBrokerRP() {
		return retryPatternService.getImagesFromBroker();
	}

	@RequestMapping("/get-images-from-broker")
	public String getImagesFromBrokerNormal() {
		return nonResilientService.getImagesFromBroker();
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroServices.class, args);
	}
}
