package hilos;

import interfaz.InterfazJuego;
import mundo.Juego;
import mundo.Pato;

public class HiloVueloPato extends Thread{

	
	private Pato pato;
	private InterfazJuego principal;
	
	public HiloVueloPato(InterfazJuego ventana, Pato duck){
		pato = duck;
		principal=ventana;
	}
	
	@Override
	public void run() {
		while(true){
			int intervalo = (int)(Math.random()*3+2);
			int y = (int)(Math.random()*231+409);
			pato.setY(y);
			try {
				Thread.sleep(intervalo*1000);
			} catch (InterruptedException e1) {}
			pato.setX(-30);
			while(principal.darJuego().darEstadoJuego()==Juego.JUEGO && principal.darJuego().darEdificio().estaEnJuego() && pato.darX()<1101){
				pato.mover();
				pato.setX(pato.darX()+10);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				principal.actualizar();
				if(principal.darJuego().darEdificio().colisionConPato())
					principal.quitarVida();
			}
		}
		
	}
	
}
