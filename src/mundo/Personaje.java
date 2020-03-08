package mundo;
/**
 * Representa un objeto que tiene movimiento y un estado de animación
 * @author David
 *
 */
public class Personaje extends Objeto{
	
	
	/**
	 * Estado actual
	 */
	protected int estado;
	/**
	 * Indica si se mueve 
	 */
	protected boolean seMueve;
	
	
	public Personaje(){
	
	}
	
	
	public int darEstado() {
		return estado;
	}

	public void modificarEstado(int pEstado) {
		this.estado = pEstado;
	}
	public boolean estaEnMovimiento() {
		return seMueve;
	}

	public void modificarSeMueve(boolean seMueve) {
		this.seMueve = seMueve;
	}

}
