package hilos;

import interfaz.InterfazJuego;
import mundo.Partida;

public class HiloCaidaLadrillos extends Thread{
	
	private Partida partida;
	private InterfazJuego principal;

	public HiloCaidaLadrillos(InterfazJuego ventana, Partida pEdificio){
		principal=ventana;
		partida=pEdificio;
	}
	
	
	public void run() {
		while(true){
			if(partida.estaEnJuego()){
			partida.caidaLadrillos();
			if(partida.colision(partida.getPrimerLadrillo()))
				principal.quitarVida();
			}
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {}
			principal.actualizar();
		}
		
	}
}
