package br.ufba.mestrado.sd.producer.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqttCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable arg0) {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }

    @Override
    public synchronized void messageArrived(String topic, MqttMessage message) throws Exception {
        MqttUtils.add(topic, new String(message.getPayload()));
    }

}
