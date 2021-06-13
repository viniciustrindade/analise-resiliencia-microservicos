package br.ufba.mestrado.sd.cloud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class CloudServiceAbstract {

	private static final Logger log = LoggerFactory.getLogger(CloudServiceAbstract.class);
	private int period = 0;
	private int vazao = 0;
	private int tempoResposta = 0;
	@Value("${cloud.max.request}")
	private int cloudMaxRequest;
	protected final RestTemplate restTemplate;
	protected final String filename;
	protected PrintWriter csv = null;
	protected PrintWriter txt = null;

	public CloudServiceAbstract(RestTemplate rest, String filename) {
		this.restTemplate = rest;
		this.filename = filename;
		new File(filename + ".txt").delete();
		new File(filename + ".csv").delete();
		try {
			this.csv = new PrintWriter(filename + ".csv");
			this.txt = new PrintWriter(filename + ".txt");
		} catch (FileNotFoundException e) {
			log.error("CLOUD #### Ocorreu um erro ao salvar o arquivo {}", e.getMessage());
		}
		log.info("CLOUD #### Requisitando imagens");

	}

	public void getImagesFromMicroservice(String url) {
		
		try {
			
			if (period++ >= cloudMaxRequest) {
				throw new RuntimeException("Analise completa");
			}

			URI uri = URI.create(url);
	
			Long inicio = System.currentTimeMillis();
			String txtObj = this.restTemplate.getForObject(uri, String.class);
			tempoResposta += System.currentTimeMillis() - inicio;

			if (txtObj == null || txtObj.equalsIgnoreCase("offline")) {
//				log.info("CLOUD #### Sem imagens");
				return;
			}
			vazao += txtObj.split(",").length;
			String cvsObj = (period) + "," + vazao + "," + tempoResposta;

			
			
			log.info("CLOUD #### {} ", cvsObj);
			csv.println(cvsObj);
			csv.flush();
			txt.println(txtObj);
			txt.flush();

		} catch (HttpServerErrorException e) {
			log.error("CLOUD #### broker fora do ar");
		} catch (RuntimeException e) {
			if (e.getMessage().equals("Analise completa")) {
				System.exit(0);
				log.error("CLOUD #### saindo do programa");
				txt.close();
				csv.close();
			}
		}
	}

}