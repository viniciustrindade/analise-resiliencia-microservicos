package br.ufba.mestrado.sd.microservices;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@EnableRetry
@Service
public class RetryPatternService extends MicroServiceAbstract {

	@Value("${minRequestSize}")
	private String minRequestSize;

	@Value("${maxRequestSize}")
	private String maxRequestSize;

	@Value("${producer.url}")
	private String brokerWsUrl;

	public RetryPatternService(RestTemplate rest) {
		super(rest);
	}

	@Retryable(value = SocketTimeoutException.class, maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
	public String getImagesFromBroker() {
		try {

			return super.getImagesFromBroker(brokerWsUrl + maxRequestSize);
		} catch (Exception e) {
//			e.printStackTrace();
			throw e;
		}
	}

	@Recover
	public String brokerOff() {
		super.brokerOff();
		return super.getImagesFromBroker(brokerWsUrl + minRequestSize);
	}

}
