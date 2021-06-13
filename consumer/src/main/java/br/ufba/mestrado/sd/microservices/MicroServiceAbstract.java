package br.ufba.mestrado.sd.microservices;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public abstract class MicroServiceAbstract {

	private static final Logger log = LoggerFactory.getLogger(MicroServiceAbstract.class);
	private final RestTemplate restTemplate;

	public MicroServiceAbstract(RestTemplate rest) {
		this.restTemplate = rest;
	}

	public String getImagesFromBroker(String url) {
		URI uri = URI.create(url);

		return this.restTemplate.getForObject(uri, String.class);
	}

	public String brokerOff() {
		log.info("MICROSERVICE #### Possivel timeout, reduzindo o tamanho do request");
		return "offline";
	}

}
