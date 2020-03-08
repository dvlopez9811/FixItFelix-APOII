package hilos;

import interfaz.InterfazJuego;
import mundo.Felix;

public class HiloMoverFelix extends Thread{
	
	
	private InterfazJuego principal;
	private Felix felix;
	private int direccion;
	
	public HiloMoverFelix(Felix nFelix, InterfazJuego ventana,int pDireccion){
		felix = nFelix;
		principal = ventana;
		direccion = pDireccion;
	}
	
	public void run() {

		while(felix.estaEnMovimiento()){
			felix.mover();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			principal.actualizar();
		}
		
		
	}

}
