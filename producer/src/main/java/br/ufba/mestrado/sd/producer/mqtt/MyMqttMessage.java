package br.ufba.mestrado.sd.producer.mqtt;

import java.util.HashMap;
import java.util.Map;

public class MyMqttMessage {

    String topic;
    String message;
    Long time;
    
    public MyMqttMessage() {
        topic = null;
        message = null;
        time = 0L;
    }
    
    public MyMqttMessage(String topic, String message, Long time) {
        this.topic = topic;
        this.message = message;
        this.time = time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Map<String, Object> publicMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("topic", this.topic);
        map.put("message", this.message);
        map.put("time", this.time);
        return map;
    }
}
