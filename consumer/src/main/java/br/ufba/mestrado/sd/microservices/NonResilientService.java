package br.ufba.mestrado.sd.microservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NonResilientService extends MicroServiceAbstract {

	@Value("${maxRequestSize}")
	private String maxRequestSize;

	@Value("${producer.url}")
	private String brokerWsUrl;

	public NonResilientService(RestTemplate rest) {
		super(rest);
	}

	public String getImagesFromBroker() {
		return super.getImagesFromBroker(brokerWsUrl + maxRequestSize);
	}

}
