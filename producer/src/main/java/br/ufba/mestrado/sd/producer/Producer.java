package br.ufba.mestrado.sd.producer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufba.mestrado.sd.producer.cameras.Camera;
import br.ufba.mestrado.sd.producer.mqtt.MqttListener;
import br.ufba.mestrado.sd.producer.mqtt.MqttUtils;
import br.ufba.mestrado.sd.producer.mqtt.MyMqttMessage;

@RestController
@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.properties")
public class Producer {

	private static final Logger log = LoggerFactory.getLogger(Producer.class);
	

    @Value("${mqttServer}")
	private String mqttServer;
    
    @Value("${TOPIC}")
	private String topic;
    
    @Value("${FREQUENCE_HZ}")
	private String frequenceHz;

	@RequestMapping(value = "/get-images/{max}")
	public synchronized String readingList(@PathVariable("max") long max) {
		String messages = "";
		int count = 0;

		while (MqttUtils.peek() != null && count++ < max) {
			MyMqttMessage message = MqttUtils.remove();
			messages += message.getMessage() + ",";
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (messages.equalsIgnoreCase("")) {
			return "";
		}
		return messages.toString();
	}

	@RequestMapping(value = "/get-images-json")
	public synchronized String readingListAsJson() {
		List<Map<String, Object>> messages = new LinkedList<>();
		while (MqttUtils.peek() != null) {
			MyMqttMessage message = MqttUtils.remove();
			messages.add(message.publicMap());
		}
		if (messages.size() == 0) {
			return "{\"message\":\"empty queue.\"}";
		}
		return messages.toString();
	}

	@Bean
	public MqttListener mqttListener() {
		return new MqttListener();
	}

	@Bean
	public Thread camera1() {
		Thread camera =null;
		try {
			camera = new Thread(new Camera(mqttServer, "camera1", topic, frequenceHz));
			camera.start();
		} catch (MqttException e) {

			e.printStackTrace();
		}
		return camera;
	}

	@Bean
	public Thread camera2() {
		Thread camera =null;
		try {
			camera = new Thread(new Camera(mqttServer, "camera2", topic, frequenceHz));
			camera.start();
		} catch (MqttException e) {

			e.printStackTrace();
		}
		return camera;
	}

	
	@Scheduled(fixedRate = 5000)
	public void printQuedeSize() {
		if (MqttUtils.getQueue().size() > 10000) {
			try {
				throw new RuntimeException("Estouro de memoria");
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		log.info("QUEUE SIZE: {} ", MqttUtils.getQueue().size());
	}

	public static void main(String[] args) {
		SpringApplication.run(Producer.class, args);
//
//		try {
//			new Thread(new Camera("camera1")).start();
//			new Thread(new Camera("camera2")).start();
////			new Thread(new Camera("camera3")).start();
//
//		} catch (MqttException e) {
//
//			e.printStackTrace();
//		}
//
	}
}
