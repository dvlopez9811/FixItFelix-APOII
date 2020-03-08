package mundo;

public class Ladrillo extends Objeto {

	/**
	 * Ladrillo siguiente en la lista enlazada lineal
	 */
	private Ladrillo siguiente;
	
	//Constructor
	
	public Ladrillo(int nX,int nY){
		setX(nX);
		setY(nY);
	}
	
	/**
	 * Aumenta la posicion de y en 1
	 */
	public void caer(){
		y+=1;
		if(siguiente!=null)
			siguiente.caer();
	}

	
	/**
	 * Retorna el ladrillo siguiente en la lista
	 * @return
	 */
	public Ladrillo getSiguiente() {
		return siguiente;
	}


	/**
	 * Establece un ladrillo como siguiente
	 * @param siguiente
	 */
	public void setSiguiente(Ladrillo siguiente) {
		this.siguiente = siguiente;
	}
}
