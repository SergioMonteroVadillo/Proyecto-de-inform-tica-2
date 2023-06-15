package vista;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controlador.ControladorVentana;

public class Ventana extends JFrame{

	public JButton boton1;

	JTextArea temperatura ,humedad, iluminacion, lluvia, fecha, hora;	
	
	ControladorVentana controlador;
	
	public Ventana(ControladorVentana c){
		
		this.controlador = c;
		
		this.setSize(380, 500);
		this.setLocation(500, 100);
		this.setResizable(false);
		this.setTitle("Estacion meteorologica");		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Se base donde ir poniendo los elementos
		JPanel tapiz = new JPanel();
		tapiz.setLayout(null);
		tapiz.setBackground((Color.CYAN).darker());
					
		//Se crean etiquetas de texto
		
		JLabel etiqueta1 = new JLabel("CONDICIONES CLIMATOLÓGICAS");  
		etiqueta1.setBounds(80, 40, 200, 50);
		
		JLabel etiqueta2 = new JLabel("TEMPERATURA:");  
		etiqueta2.setBounds(100, 82, 100, 50);
		
		JLabel etiqueta3 = new JLabel("HUMEDAD:");  
		etiqueta3.setBounds(100, 132, 100, 50);
		
		JLabel etiqueta4 = new JLabel("ILUMINACION:");  
		etiqueta4.setBounds(100, 182, 100, 50);
		
		JLabel etiqueta5 = new JLabel("LUVIA 0=NO/1=SI:");  
		etiqueta5.setBounds(100, 232, 100, 50);
		
		JLabel etiqueta6 = new JLabel("ULTIMA ACTUALIZACIÓN");  
		etiqueta6.setBounds(90, 290, 200, 50);
		
		//Se crea boton actualizar
		boton1 = new JButton("ACTUALIZAR");
		boton1.setBounds(100, 360, 120, 30);
		boton1.addActionListener(controlador);	
				

		//Se crean áreas de texto
		temperatura = new JTextArea();	
		temperatura.setBounds(210, 100, 30, 17);
		getContentPane().add(temperatura);

		humedad = new JTextArea();		
		humedad.setBounds(210, 150, 30, 17);
		getContentPane().add(humedad);
		
		iluminacion = new JTextArea();		
		iluminacion.setBounds(210, 200, 30, 17);
		getContentPane().add(iluminacion);
		
		lluvia = new JTextArea();		
		lluvia.setBounds(210, 250, 30, 17);
		getContentPane().add(lluvia);
		
		fecha = new JTextArea();		
		fecha.setBounds(90, 330, 70, 17);
		getContentPane().add(fecha);
		
		hora = new JTextArea();		
		hora.setBounds(160, 330, 60, 17);
		getContentPane().add(hora);
		
		//Se añade todos los elementos creados al tapiz
		tapiz.add(temperatura);
		tapiz.add(humedad);
		tapiz.add(iluminacion);
		tapiz.add(lluvia);				
		tapiz.add(fecha);
		tapiz.add(hora);
		
		tapiz.add(etiqueta1);
		tapiz.add(etiqueta2);
		tapiz.add(etiqueta3);
		tapiz.add(etiqueta4);
		tapiz.add(etiqueta5);
		tapiz.add(etiqueta6);
		tapiz.add(boton1);
		
		add(tapiz);		
	}
	
	public JTextArea getTexttemperatura() {
		return temperatura;		
	}
	
	public JTextArea getTexthumedad() {
		return humedad;		
	}
	
	public JTextArea getTextiluminacion() {
		return iluminacion;		
	}
	
	public JTextArea getTextlluvia() {
		return lluvia;		
	}
	
	public JTextArea getTextfecha() {
		return fecha;		
	}
	
	public JTextArea getTexthora() {
		return hora;		
	}

}
