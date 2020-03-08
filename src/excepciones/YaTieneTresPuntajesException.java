package excepciones;

public class YaTieneTresPuntajesException extends Exception{
	/**
	 * Se lanza cuando un jugador que termina una partida ya posee tres puntajes entre los diez mejores
	 * @param nick
	 */
	public YaTieneTresPuntajesException(String nick){
		super("El usuario "+nick+ " ya tiene tres puntajes dentro del top 10,"+"\n"+"no se guardará este puntaje");	
	}
}
