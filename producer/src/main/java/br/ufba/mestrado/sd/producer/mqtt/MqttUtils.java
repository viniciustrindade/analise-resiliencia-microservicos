package br.ufba.mestrado.sd.producer.mqtt;

import java.util.LinkedList;
import java.util.Queue;

public class MqttUtils {
    
    private static Queue<MyMqttMessage> queue;

    public synchronized static Queue<MyMqttMessage> getQueue() {
        if (queue == null) {
            queue = new LinkedList<>();
        }
        return queue;
    }

    public synchronized static void add(String topic, String message) {
        add(topic, message, System.currentTimeMillis());
    }

    public synchronized static void add(String topic, String message, Long time) {
        getQueue().add(new MyMqttMessage(topic, message, time));
    }

    public synchronized static MyMqttMessage remove() {
        return getQueue().poll();
    }
    
    public synchronized static MyMqttMessage peek() {
        return getQueue().peek();
    }
    
}
