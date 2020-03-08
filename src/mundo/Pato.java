package mundo;

public class Pato extends Objeto implements Moviles{


	/**
	 * estado actual
	 */
	private int estado;
	
	//Constructor
	public Pato(){

	}

	/**
	 * Animaci�n
	 */
	@Override
	public void mover() {
		estado = (estado!=1) ? 1 : 2;
		setY((estado==2)? y+10 : y-10);
	}

	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
}
