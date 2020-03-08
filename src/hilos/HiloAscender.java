package hilos;

import interfaz.InterfazJuego;
import mundo.Partida;
import mundo.Felix;
import mundo.Ralph;

public class HiloAscender extends Thread{
	/**
	 * Animación que se lleva a cabo cada que el jugador termina un nivel de 3 pisos
	 */

	private InterfazJuego principal;
	private Felix felix;
	private Ralph ralph;
	private Partida edificio;

	public HiloAscender(InterfazJuego principal, Felix feliz , Partida edificio,Ralph ralph ) {
		this.principal = principal;
		this.felix = feliz;
		this.edificio=edificio;
		this.ralph = ralph;

	}

	public void run() {

		felix.setInmunidadTemporal(true);
		while(ralph.estaDemoliendo() || ralph.estaEnMovimiento()){		

		}
		edificio.setAlturaActual(edificio.darAlturaActual()-3);
		edificio.setBaseActual(edificio.darBaseActual()-3);
		edificio.subirEstacion();
		
		while(ralph.darY()>edificio.darVentanas()[edificio.darAlturaActual()][0].darPosicionY()-118 ){
			ralph.subir();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		ralph.modificarEstado(0);
		while(edificio.darVentanas()[edificio.darBaseActual()][0].darPosicionY()<felix.darY()){
			principal.mover(Felix.ARRIBA);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		while(edificio.darVentanas()[edificio.darBaseActual()][0].darPosicionY()<550 ){
			felix.setY(felix.darY()+1);
			ralph.setY(ralph.darY()+1);
			edificio.getPato().setY(edificio.getPato().darY()+1);
			edificio.setNivelY(edificio.getNivelY()+1);
			try {
				for( int i = edificio.darVentanas().length-1; i >= 0;i--){
					for (int j = 0; j < edificio.darVentanas()[0].length; j++) {
						edificio.darVentanas()[i][j].modificarPosicionY(edificio.darVentanas()[i][j].darPosicionY()+1);
					}
				}
				principal.actualizar();
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

		}
		edificio.setEstadoActualPartida(Partida.CORRIENDO);
		felix.setInmunidadTemporal(false);
		principal.moverRalph();
	}

}
