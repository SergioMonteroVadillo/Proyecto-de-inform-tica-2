package modelo;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Comunicacion {	
	
    public Comunicacion() throws MqttException  {

        // Configuración del servidor MQTT
        String broker = "tcp://192.168.1.117:1883";		//Para probar el funcionamiento, se debe cambiar dirección IP a la que tenga asignada el equipo pdonde se pruebe
        String clientId = "Proyecto2";        
        MemoryPersistence persistence = new MemoryPersistence();
        final MqttClient client = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] { broker });
        options.setCleanSession(true);
        System.out.println("Entra en comunicacion");

        // Configuración del suscriptor
        MqttCallback callback = new MqttCallback() {
            //@Override
            public void connectionLost(Throwable throwable) {}

            //@Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
               
            	System.out.println("Mensaje recibido en " + topic + ": " + new String(message.getPayload()));
            	
            	deserializador (topic, new String(message.getPayload()));
            	
            	// Publicar mensaje recibido en otro tema
                /*MqttTopic pubTopic = client.getTopic("Topic");
                MqttMessage pubMessage = new MqttMessage(message.getPayload());
                pubTopic.publish(pubMessage);*/
            }

            //@Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {} 
        };
        client.setCallback(callback);

        // Conexión y suscripción al tema
        client.connect(options);
        String topic = "EM001";
        client.subscribe(topic);

        // Espera indefinida para recibir mensajes
       
    }
  
    //Deserialización del mensaje recivido para su tratamiento
	public void deserializador(String topic,  String eventos) {
    			
		String id = topic;
		int temperatura = 0, humedad = 0, iluminacion = 0, lluvia = 0;
		
		String [] coma = eventos.split(",");
		
		for (String i : coma) {			
			
			String [] dospuntos = i.split(":");
			
			for (int j = 0; j<dospuntos.length; j++) {
			
				if (dospuntos[j].equals("Temperatura")) {					
					temperatura = Integer.valueOf(dospuntos[j+1]);					
				}

				if (dospuntos[j].equals("Humedad")) {
					humedad = Integer.valueOf(dospuntos[j+1]);
				}
				if (dospuntos[j].equals("Luz")) {
					iluminacion = Integer.valueOf(dospuntos[j+1]);
				}
				if (dospuntos[j].equals("Lluvias")) {					
					lluvia = Integer.valueOf(dospuntos[j+1]);
				}			
			}
		}
		new BaseDatos(id, temperatura, humedad, iluminacion, lluvia);		
	}    
}