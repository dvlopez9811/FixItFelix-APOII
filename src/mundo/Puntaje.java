package mundo;

import java.io.Serializable;

public class Puntaje implements Serializable{
	

	/**
	 * Nombre del usuario que obtuvo el puntaje
	 */
	private String userName;
	/**
	 * Valor de los puntos conseguidos
	 */
	private int puntos;
	
	public Puntaje(String pUserName, int pPuntos) {
		userName = pUserName;
		puntos = pPuntos;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

}
