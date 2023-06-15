package modelo;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

import controlador.ControladorVentana;

import java.sql.ResultSet;

public class BaseDatos {
		
	private int temperatura, humedad, iluminacion, lluvia;
	private String id, fecha, hora;

	public BaseDatos() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		
		
		try {
			Class.forName("org.sqlite.JDBC");			
			conn = DriverManager.getConnection("jdbc:sqlite:PI2.db");
			stmt = conn.createStatement();			
			rs = stmt.executeQuery("SELECT * FROM evento WHERE fecha=(SELECT fecha FROM evento Order BY fecha DESC LIMIT 1) ORDER BY hora DESC LIMIT 1");
			
			while (rs.next()) {
				id = rs.getString("id");
				temperatura = rs.getInt("temperatura");			
				humedad = rs.getInt("humedad");				
				iluminacion = rs.getInt("iluminacion");
				lluvia = rs.getInt("lluvia");
				fecha = rs.getString("fecha");
				hora = rs.getString("hora");				
			}		
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	//Constructor para crear usuario
	public BaseDatos(String id, String nombre, int telefono, String correo_e) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		
		
		try {
			Class.forName("org.sqlite.JDBC");			
			conn = DriverManager.getConnection("jdbc:sqlite:PI2.db");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("insert into cliente (Id, nombre, telefono, C_Electronico)values (" + id + ", " + nombre + ", " + telefono + ", " + correo_e + ")");
						
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	//Constructor para guardar eventos
	public BaseDatos( String id, int temperatura, int humedad, int iluminacion, int lluvia) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;	
				
		try {
			Class.forName("org.sqlite.JDBC");			
			conn = DriverManager.getConnection("jdbc:sqlite:PI2.db");
			stmt = conn.createStatement();			
			rs = stmt.executeQuery("insert into evento (temperatura, humedad, iluminacion, lluvia, fecha, hora) values("+  temperatura + ", " + humedad + ", " + iluminacion + ", " + lluvia +", DATE(), TIME())");
					
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}

	public String temperatura() {
		String temp = Integer.toString(temperatura);
		return temp;
	}
	
	public String humedad() {
		String hum = Integer.toString(humedad);
		return hum;
	}
	
	public String iluminacion() {
		String luz = Integer.toString(iluminacion);
		return luz;
	}
	
	public String lluvia() {
		String lluv = Integer.toString(lluvia);
		return lluv;
	}
	
	public String fecha() {	
		return fecha;
	}
	
	public String hora() {	
		return hora;
	}

}

