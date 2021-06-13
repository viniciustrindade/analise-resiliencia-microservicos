package br.ufba.mestrado.sd.microservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableCircuitBreaker
@Service
public class CircuitBrakerService extends MicroServiceAbstract {

	@Value("${minRequestSize}")
	private String minRequestSize;

	@Value("${maxRequestSize}")
	private String maxRequestSize;

	@Value("${producer.url}")
	private String brokerWsUrl;

	public CircuitBrakerService(RestTemplate rest) {
		super(rest);
	}

	@HystrixCommand(fallbackMethod = "brokerOff")
	public String getImagesFromBroker() {
		return super.getImagesFromBroker(brokerWsUrl + maxRequestSize);
	}

	public String brokerOff() {
		super.brokerOff();

		return super.getImagesFromBroker(brokerWsUrl + minRequestSize);
	}

}
