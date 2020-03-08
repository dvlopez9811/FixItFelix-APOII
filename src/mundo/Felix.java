package mundo;

public class Felix extends Personaje implements Moviles,Mortales{


	/**
	 * Cantidad de vidas actuales
	 */
	private int vidas;
	/**
	 * Indica si el jugador esta reparando una ventana
	 */
	private boolean repara;
	/**
	 * Indica si el jugador tiene una inmunidad temporal debida a que acaba de recibir un golpe
	 */
	private boolean inmunidadTemporal;

	private boolean puedeMoverse;

	//Constantes de dirección de movimiento
	public static final int ATRAS = 1;
	public static final int ADELANTE = 2;
	public static final int ARRIBA = 3;
	public static final int ABAJO = 4;

	public Felix(){
		seMueve = false;
		vidas=3;
		modificarReparando(false);
	}


	@Override
	/**
	 * Cambia el estado del jugador para realizar la animación de movimiento
	 */
	public void mover() {
		if(estado<3)
			estado++;
		else{
			estado =0;
			modificarSeMueve(false);
		}
		
	}
	
	@Override
	/**
	 * Cambia el estado del jugador para llevar a cabo la animación de muerte
	 */
	public void morir() {
		if(estado<10)
			estado=10;
		else if(estado<14)
			estado++;
	}

	/**
	 * Cambia el estado de felix para animar la reparación de una ventana
	 */
	public void reparar(){
		if(estado<9)
			estado++;
		else{
			estado = 5;
			modificarReparando(false);
		}
	}
	@Override
	/**
	 * 
	 */
	public int calcularVidasRestantes() {
		return vidas;
	}
	


	
	//////////////////////////////////////////////////////////////////////////////
	//Getters y Setters
	//////////////////////////////////////////////////////////////////////////////
	
	public boolean estaReparando() {
		return repara;
	}


	public void modificarReparando(boolean repara) {
		this.repara = repara;
	}

	public boolean estaMuerto(){
		return (vidas<1) ? true : false;
	}

	public int darVidas() {
		return vidas;
	}
	
	public void setVidas(int pVidas){
		vidas =pVidas;
	}


	public void quitarVida() {
		vidas = darVidas()-1;
		inmunidadTemporal=true;
	}


	public boolean tieneInmunidadTemporal() {
		return inmunidadTemporal;
	}


	public void setInmunidadTemporal(boolean inmunidadTemporal) {
		this.inmunidadTemporal = inmunidadTemporal;
	}


	public boolean puedeMoverse() {
		return puedeMoverse;
	}


	public void setPuedeMoverse(boolean puedeMoverse) {
		this.puedeMoverse = puedeMoverse;
	}


}
