package br.ufba.mestrado.sd.producer.cameras;

import static java.lang.Math.pow;
import static java.lang.Math.random;
import static java.lang.Math.round;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public class Camera extends MqttClient implements Runnable {

	private String id;

	private String topic;
    
	private String frequenceHz;
    
    

	public Camera(String mqttServer, String id, String topic, String frequenceHz) throws MqttException  {
		super(mqttServer, id);
		this.id = id;
		this.topic = topic;
		this.frequenceHz = frequenceHz;
		this.connect();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void publishRandomImage() {
		String msg = "";
//		int max = 1000;
//		int min = 10;
//		int num = (int) (Math.random() * (max-min)) + min;
//		for (int i=0;i<min;i++) 
			msg+=Long.toString(round(random() * pow(36, 10)), 36) + Long.toString(round(random() * pow(36, 10)), 36)+ Long.toString(round(random() * pow(36, 10)), 36)+Long.toString(round(random() * pow(36, 10)), 36) +Long.toString(round(random() * pow(36, 10)), 36) + Long.toString(round(random() * pow(36, 10)), 36) +Long.toString(round(random() * pow(36, 10)), 36);
			msg+=Long.toString(round(random() * pow(36, 10)), 36) + Long.toString(round(random() * pow(36, 10)), 36)+ Long.toString(round(random() * pow(36, 10)), 36)+Long.toString(round(random() * pow(36, 10)), 36) +Long.toString(round(random() * pow(36, 10)), 36) + Long.toString(round(random() * pow(36, 10)), 36) +Long.toString(round(random() * pow(36, 10)), 36);
			msg+=Long.toString(round(random() * pow(36, 10)), 36) + Long.toString(round(random() * pow(36, 10)), 36)+ Long.toString(round(random() * pow(36, 10)), 36)+Long.toString(round(random() * pow(36, 10)), 36) +Long.toString(round(random() * pow(36, 10)), 36) + Long.toString(round(random() * pow(36, 10)), 36) +Long.toString(round(random() * pow(36, 10)), 36);

		try {
			// System.out.println(id + " ### publicando nova imagem");
			super.publish(topic, new MqttMessage(msg.getBytes()));
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			
			this.publishRandomImage();
			try {
				Thread.sleep(1000/Integer.parseInt(frequenceHz));
//			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
