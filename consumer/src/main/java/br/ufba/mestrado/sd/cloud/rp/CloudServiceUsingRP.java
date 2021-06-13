package br.ufba.mestrado.sd.cloud.rp;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.ufba.mestrado.sd.cloud.CloudServiceAbstract;

@EnableAsync
@Service
public class CloudServiceUsingRP extends CloudServiceAbstract {

	public CloudServiceUsingRP(RestTemplate rest) {
		super(rest, "retrypattern");
	}

	@Async
	@Scheduled(fixedRate = 5000)
	public void getImagesFromMicroserviceCB() {
		super.getImagesFromMicroservice("http://localhost:8080/get-images-from-broker-rp");
	}

}
