package mundo;

public class Partida {

	//Valores constantes que indican el estado de la partida
	public static final int INTRO = 0;
	public static final int CORRIENDO = 1;
	public static final int CAMBIO_ESTACION = 2;
	public static final int PAUSA = 3;
	public static final int FINAL = 4;


	/**
	 * Arreglo bidimensional de ventanas en el edificio
	 */
	private Ventana[][] ventanas;
	/**
	 * Ventana en la que se encuentra el jugador
	 */
	private Ventana actual;
	/**
	 * Fila de la ventana actual
	 */
	private int iActual;
	/**
	 * Columna de la ventana actual
	 */
	private int jActual;
	/**
	 * Coordenada en la que se pinta el edificio
	 */
	private int nivelY;
	/**
	 * Fila limite superior
	 */
	private int alturaActual;
	/**
	 * Fila limite inferior
	 */
	private int baseActual;
	/**
	 * Estacion en la que se encuentra el jugador
	 */
	private int estacionActual;
	/**
	 * Estado de la partida
	 */
	private int estadoActualPartida;

	/**
	 * Puntaje obtenido por el jugador en la partida
	 */
	private Puntaje puntaje;

	/**
	 * Personaje principal del juego
	 */
	private Felix felix;
	/**
	 * Villano demoledor del juego
	 */
	private Ralph ralph;
	/**
	 * Primer ladrillo de la lista enlazada lineal de ladrillos
	 */
	private Ladrillo primerLadrillo;
	/**
	 * Pato 
	 */
	private Pato pato;

	/**
	 * Partida ubicada a la izquierda en el arbol binario
	 */
	private Partida izquierda;
	/**
	 * Partida ubicada a a la derecha en el arbol binario
	 */
	private Partida derecha;
	/**
	 * Indica si la partida se encuentra en juego
	 */
	private boolean estaEnJuego;
	
	private boolean finalizando;


	//Constructor
	public Partida(){

		setNivelY(-505);
		setFelix(new Felix());
		felix.setInmunidadTemporal(false);
		setRalph(new Ralph());
		setPato(new Pato());
		ventanas = new Ventana[12][5];
		int y =574;
		for (int i = ventanas.length-1; i >= 0; i--) {
			int x = 378;
			for (int j = 0; j < ventanas[0].length; j++) {
				ventanas[i][j] = new Ventana(x,y);
				x+=55;
			}
			y = (i!=ventanas.length-2) ? y-90 : y-72;
		}
		iActual=ventanas.length-1;
		jActual=2;
		alturaActual = ventanas.length-3;
		baseActual = ventanas.length-1;
		estacionActual=1;
		puntaje = new Puntaje("", 0);
		setVentanaActual(ventanas[iActual][jActual]);
		felix.setX(actual.darPosicionX()-10);
		felix.setY(actual.darPosicionY()+20);
		felix.setInmunidadTemporal(true);
		felix.modificarEstado(0);
	}



	////////////////////////////////////////////////////////
	//Ventanas
	//////////////////////////////////////////////////////////7
	/**
	 * Repara uno de los vidrios en la ventana actual y aumenta 500 puntos en el puntaje si no estaba reparada antes
	 */
	public void repararVentana(){
		if(!actual.estaReparada()){
			switch (actual.darEstadoTotal()) {
			case 1:
				actual.darSuperior().modificarEstado(Vidrio.REPARADO);
				actual.cambiarEstadoTotal(3);
				break;
			case 2:
				actual.darSuperior().modificarEstado(Vidrio.REPARADO);
				actual.cambiarEstadoTotal(0);
				break;
			case 3:
				actual.darInferior().modificarEstado(Vidrio.REPARADO);
				actual.cambiarEstadoTotal(0);
				break;
			case 4:
				actual.darInferior().modificarEstado(Vidrio.REPARADO);
				actual.cambiarEstadoTotal(0);
				break;
			case 5:
				actual.darSuperior().modificarEstado(Vidrio.REPARADO);
				actual.cambiarEstadoTotal(4);
				break;
			case 6:
				actual.darSuperior().modificarEstado(Vidrio.REPARADO);
				actual.cambiarEstadoTotal(0);
				break;
			default:
				break;
			}
			puntaje.setPuntos(puntaje.getPuntos()+500);
		}
	}

	/**
	 * Destruye aleatoriamente las ventanas en la matriz, a excepcion de la puerta principal y un balcón
	 */
	public void romperAleatorio(){
		for (int i = 0; i < ventanas.length; i++) {
			for (int j = 0; j < ventanas[0].length; j++) {
				if(ventanas[i][j].estaReparada())
					ventanas[i][j].romperAleatorio();
			}
		}
		ventanas[ventanas.length-1][2].darSuperior().modificarEstado(Vidrio.REPARADO);
		ventanas[ventanas.length-1][2].darInferior().modificarEstado(Vidrio.REPARADO);
		ventanas[ventanas.length-2][2].darSuperior().modificarEstado(Vidrio.REPARADO);
		ventanas[ventanas.length-2][2].darInferior().modificarEstado(Vidrio.REPARADO);
	}


	/**
	 * Reemplaza la matriz de ventanas
	 * @param nuevas
	 */
	public void setVentanas(Ventana[][] nuevas){
		int y =574;
		for (int i = ventanas.length-1; i >= 0; i--) {
			int x = 378;
			for (int j = 0; j < ventanas[0].length; j++) {
				nuevas[i][j].modificarPosicionX(x);
				nuevas[i][j].modificarPosicionY(y);
				ventanas[i][j] = nuevas[i][j];
				x+=55;
			}
			y = (i!=ventanas.length-2) ? y-90 : y-72;
		}
		
	}
	
	//////////////////////////////
	//Felix
	/////////////////////////////
	/**
	 * Mueve el personaje dentro de la matriz de ventanas
	 * @param direccion
	 */
	public void mover(int direccion){
		switch (direccion) {
		case Felix.ATRAS:
			if(jActual>0){
				modificarJActual(jActual-1);
				setVentanaActual(ventanas[iActual][jActual]);
			}
			break;
		case Felix.ADELANTE:
			if(jActual < ventanas[0].length-1){
				modificarJActual(jActual+1);
				setVentanaActual(ventanas[iActual][jActual]);
			}
			break;
		case Felix.ARRIBA:
			if(iActual>alturaActual){
				modificarIActual(iActual-1);
				setVentanaActual(ventanas[iActual][jActual]);
			}
			break;
		case Felix.ABAJO:
			if(iActual<baseActual){
				modificarIActual(iActual+1);
				setVentanaActual(ventanas[iActual][jActual]);
			}

			break;
		}
		if(!(iActual==ventanas.length-1 && jActual== 2)){
			felix.setX(actual.darPosicionX()-10);
			felix.setY(actual.darPosicionY()-8);
		}else{
			felix.setX(actual.darPosicionX()-10);
			felix.setY(actual.darPosicionY()+20);
		}

	}


	//////////////////////////////////////
	//Desarrollo del juego
	//////////////////////////////////////

	/**
	 * Aumenta la estacion actual de la partida
	 */
	public void subirEstacion(){
		if(alturaActual==9 && baseActual==11)
			estacionActual=1;
		else if(alturaActual==6 && baseActual==8)
			estacionActual=2;
		else if(alturaActual==3 && baseActual==5)
			estacionActual=3;
		else if(alturaActual==0 && baseActual==2)
			estacionActual=4;
	}

	/**
	 * Quita una vida del personaje
	 */
	public void quitarVida(){
		if(!felix.tieneInmunidadTemporal())
			felix.quitarVida();

	}

	/**
	 * Verifica si la estacion actual tiene todas las ventanas reparadas
	 * @return
	 */
	public boolean estacionSuperada(){
		boolean superada=true;
		for (int i = alturaActual; i <= baseActual; i++) {
			for (int j = 0; j < ventanas[0].length; j++) {
				if(!ventanas[i][j].estaReparada())
					superada=false;
			}
		}
		return superada;
	}

	/**
	 * Verifica que toda la matriz de ventanas esta reparada
	 * @return
	 */
	public boolean terminada(){
		boolean noHayDañada = true;
		for (int i = 0; i < ventanas.length && noHayDañada; i++) {
			for (int j = 0; j < ventanas[0].length && noHayDañada; j++) {
				noHayDañada = (ventanas[i][j].estaReparada()) ? true : false;
			}
		}
		return noHayDañada;
	}

	// pato  ancho:40 alto:26
	/**
	 * Verifica si el persoje choca con un pato
	 * @return
	 */
	public boolean colisionConPato(){
		boolean choque = false;
		if(((felix.darY()<pato.darY() && felix.darY()+63>pato.darY()+26) ||(felix.darY()>pato.darY() && felix.darY()<pato.darY()+26)
				||(felix.darY()+63>pato.darY() && felix.darY()+63<pato.darY()+26)) && felix.darX()+3>pato.darX() && pato.darX()+40>felix.darX()+3)
			choque = true;
		return choque;
	}


	///////////////////////////////////
	//LADRILLOS
	//////////////////////////////////


	/**
	 * Crea un ladrillo 
	 * @return
	 */
	public Ladrillo crearLadrillo(){
		int dist = (int)(Math.random()*100);
		Ladrillo nuevo = new Ladrillo(ralph.darX()+dist,ralph.darY()+70);
		if(primerLadrillo==null)
			primerLadrillo=nuevo;
		return nuevo;
	}

	/**
	 * Retorna el ultimo ladrillo agregado en la lista, si no hay crea uno
	 * @param bloque
	 * @return
	 */
	public Ladrillo darUltimoLadrillo(Ladrillo bloque){
		if(primerLadrillo!=null){
			if(bloque.getSiguiente()!=null)
				return darUltimoLadrillo(bloque.getSiguiente());
			else
				return bloque;
		}else{
			return crearLadrillo();
		}
	}

	public void caidaLadrillos(){
		if(primerLadrillo!=null)
			primerLadrillo.caer();
	}

	//ladrillo 24 ancho 19 alto
	//felix ancho 30 alto 63

	/**
	 * Verifica que cada ladrillo en la lista no este en colision con el personaje
	 * @param lad
	 * @return
	 */
	public boolean colision(Ladrillo lad){
		if(primerLadrillo!=null){
			if(( (felix.darX()+3 < lad.darX() && lad.darX()+24 < felix.darX() + 33) || (felix.darX()+30 > lad.darX() && lad.darX()+23 > felix.darX()+30)
					|| (felix.darX()+3>lad.darX() && felix.darX()<lad.darX()+23))
					&& (felix.darY() > lad.darY() && lad.darY()+18 > felix.darY()))
				return true;
			else if(lad.getSiguiente()!=null)
				return colision(lad.getSiguiente());
			else 
				return false;
		}else return false;
	}

	/**
	 * Repara completamente una estación
	 */
	public void repararEstacion(){
		for (int i = alturaActual; i <= baseActual; i++) {
			for (int j = 0; j < ventanas[0].length; j++) {
				ventanas[i][j].darSuperior().modificarEstado(Vidrio.REPARADO);
				ventanas[i][j].darInferior().modificarEstado(Vidrio.REPARADO);
				ventanas[i][j].actualizar();
			}
		}
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Getters y Setters
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Ventana[][] darVentanas(){
		return ventanas;
	}

	public Ventana getVentanaActual() {
		return actual;
	}

	public void setVentanaActual(Ventana actual) {
		this.actual = actual;
	}

	public int darJActual() {
		return jActual;
	}

	public void modificarJActual(int jActual) {
		this.jActual = jActual;
	}

	public int darIActual() {
		return iActual;
	}

	public void modificarIActual(int iActual) {
		this.iActual = iActual;
	}

	public void actualizarActual(){
		actual = ventanas[iActual][jActual];
	}

	public int darBaseActual() {
		return baseActual;
	}


	public void setBaseActual(int baseActual) {
		this.baseActual = baseActual;
	}

	public int darAlturaActual(){
		return alturaActual;
	}

	public void setAlturaActual(int pAltura){
		alturaActual=pAltura;
	}

	public Felix darFelix() {
		return felix;
	}


	public void setFelix(Felix felix) {
		this.felix = felix;
	}

	public Ralph darRalph() {
		return ralph;
	}

	public void setRalph(Ralph ralph) {
		this.ralph = ralph;
	}

	public int getNivelY() {
		return nivelY;
	}

	public void setNivelY(int nivelY) {
		this.nivelY = nivelY;
	}

	public int getEstacionActual() {
		return estacionActual;
	}

	public void setEstacionActual(int estacionActual) {
		this.estacionActual = estacionActual;
	}

	public Ladrillo getPrimerLadrillo() {
		return primerLadrillo;
	}

	public void setPrimerLadrillo(Ladrillo primerLadrillo) {
		this.primerLadrillo = primerLadrillo;
	}

	public int getEstadoActualPartida() {
		return estadoActualPartida;
	}

	public void setEstadoActualPartida(int estadoActualPartida) {
		this.estadoActualPartida = estadoActualPartida;
	}

	public Puntaje getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Puntaje puntaje) {
		this.puntaje = puntaje;
	}

	public Partida getIzquierda() {
		return izquierda;
	}

	public void setIzquierda(Partida izquierda) {
		this.izquierda = izquierda;
	}

	public Partida getDerecha() {
		return derecha;
	}

	public void setDerecha(Partida derecha) {
		this.derecha = derecha;
	}
	public boolean estaEnJuego() {
		return estaEnJuego;
	}
	public void setEstaEnJuego(boolean estaEnJuego) {
		this.estaEnJuego = estaEnJuego;
	}

	public Pato getPato() {
		return pato;
	}

	public void setPato(Pato pato) {
		this.pato = pato;
	}



	public boolean estaFinalizando() {
		return finalizando;
	}



	public void setFinalizando(boolean finalizando) {
		this.finalizando = finalizando;
	}
}
