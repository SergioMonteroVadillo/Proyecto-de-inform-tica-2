package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;

import modelo.BaseDatos;
import modelo.Comunicacion;
//import modelo.Año;
//import modelo.Control;
import vista.Ventana;

public class ControladorVentana implements ActionListener{

	Ventana ventana = new Ventana(null);

	Sistema sistema;

	String mensaje;
		
	public ControladorVentana(Sistema s) {

		this.sistema = s;
	}	

	public void actionPerformed(ActionEvent e) {

		BaseDatos basedatos = new BaseDatos();
		
		//this.ventana.getTextListado1().setText("");
		this.ventana.getTexttemperatura().setText("  "+basedatos.temperatura());
		this.ventana.getTexthumedad().setText("  "+basedatos.humedad());
		this.ventana.getTextiluminacion().setText("  "+basedatos.iluminacion());
		this.ventana.getTextlluvia().setText("  "+basedatos.lluvia());
		this.ventana.getTextfecha().setText("  "+basedatos.fecha());
		this.ventana.getTexthora().setText("  "+basedatos.hora());		
		System.out.println("Temperatura: " + basedatos.temperatura() + " - Humedad: " + basedatos.humedad() + " - Iluminacion: " + basedatos.iluminacion() + " - Lluvia: " + basedatos.lluvia() + " - Fecha: " + basedatos.fecha()  + " - Hora: " + basedatos.hora());	

	}

	public void setVentanaAControlar (Ventana v) {
		this.ventana = v;
	}

}
