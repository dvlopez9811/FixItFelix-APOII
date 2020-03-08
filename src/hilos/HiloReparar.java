package hilos;

import interfaz.InterfazJuego;
import mundo.Felix;

public class HiloReparar extends Thread{
	
	private Felix felix;
	private InterfazJuego principal;
	
	public HiloReparar(InterfazJuego ventana,Felix pFelix){
		felix = pFelix;
		principal = ventana;
	}
	
	public void run() {
		felix.modificarEstado(5);
		while(felix.estaReparando()){
			felix.reparar();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			principal.actualizar();
		}
		principal.darJuego().darEdificio().repararVentana();
		felix.modificarEstado(0);
		principal.verificarEstacion();
		
	}

}
