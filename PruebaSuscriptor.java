package pruebaProyecto;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PruebaSuscriptor {
	
	 public static void main(String[] args) throws MqttException {

	        // Configuración del cliente MQTT
	        String broker = "tcp://192.168.1.117:1883";
	        String clientId = "JavaSubscriber";
	        MemoryPersistence persistence = new MemoryPersistence();
	        MqttClient client = new MqttClient(broker, clientId, persistence);

	        // Configuración del suscriptor
	        MqttCallback callback = new MqttCallback() {
	            @Override
	            public void connectionLost(Throwable throwable) {}

	            @Override
	            public void messageArrived(String topic, MqttMessage message) throws Exception {
	                System.out.println("Mensaje recibido de topic " + topic + ": " + new String(message.getPayload()));
	            }

	            @Override
	            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
	        };
	        client.setCallback(callback);

	        // Conexión y suscripción al tema
	        client.connect();
	        String topic = "ardu";
	        client.subscribe(topic);

	        // Espera indefinida para recibir mensajes
	        while (true) {
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }

}
