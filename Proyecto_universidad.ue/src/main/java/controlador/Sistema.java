package controlador;

import org.eclipse.paho.client.mqttv3.MqttException;

import modelo.Comunicacion;
import vista.Ventana;

public class Sistema {
	
	static Sistema s = new Sistema();

	public static void main(String[] args) {
			
		try {
			Comunicacion comunica = new Comunicacion();
		} catch (MqttException e) {
			e.printStackTrace();
		}
		ControladorVentana controlador = new ControladorVentana(s);
		Ventana ventana = new Ventana(controlador);
		controlador.setVentanaAControlar(ventana);
		ventana.setVisible(true);
	}
}