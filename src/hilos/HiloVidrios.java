package hilos;

import interfaz.InterfazJuego;
import mundo.Partida;
import mundo.Ralph;

public class HiloVidrios extends Thread{

	private InterfazJuego principal;

	public HiloVidrios(InterfazJuego ventana){
		principal=ventana;
	}

	@Override

	public void run() {

		while (principal.darJuego().darEdificio().getEstadoActualPartida()!=Partida.CORRIENDO){
			principal.lluviaVidrios();
			try {
				Thread.sleep(3);
			} catch (Exception e) {
			}
			principal.actualizar();

		}
	}
}