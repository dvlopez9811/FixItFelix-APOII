package mundo;
/**
 * Objeto que contiene una posicion y coordenadas
 * @author David
 *
 */
public class Objeto {

	/**
	 * Coordenada en el eje x
	 */
	protected int x;
	/**
	 * Coordenada en el eje y
	 */
	protected int y;
	
	//Constructor
	public Objeto(){
		
	}
	
	//Getters y setters
	
	public int darX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int darY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
