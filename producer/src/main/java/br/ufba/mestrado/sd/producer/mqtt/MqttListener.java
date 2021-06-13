package br.ufba.mestrado.sd.producer.mqtt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;


public class MqttListener implements ServletContextListener {

    MqttClient client;


    @Value("${TOPIC}")
	private String TOPIC;

    @Value("${mqttServer}")
	private String mqttServer;
    
    public MqttListener() {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (client != null) {
                client.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void contextInitialized(ServletContextEvent sce) {
    	  String clientId = "listener";
          try {
              client = new MqttClient(mqttServer, clientId);
              client.connect();
              client.subscribe(TOPIC);
              client.setCallback(new MyMqttCallback());
          } catch (MqttException e) {
              e.printStackTrace();
          }
    }
    
  
}
