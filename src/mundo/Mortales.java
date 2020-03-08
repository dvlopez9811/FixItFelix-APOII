package mundo;
/**
 * Interfaz que permite a futuro implementar metodos en personajes que tengan un limite de vida en el juego y puedan morir
 * @author 
 *
 */
public interface Mortales {

	void morir();
	
	int calcularVidasRestantes();
}
