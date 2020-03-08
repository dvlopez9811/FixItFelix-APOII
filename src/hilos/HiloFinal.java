package hilos;

import interfaz.InterfazJuego;
import mundo.Partida;
import mundo.Ralph;

public class HiloFinal extends Thread{

	private InterfazJuego principal;
	private Ralph ralph;
	private Partida edificio;
	
	public HiloFinal(InterfazJuego ventana,Ralph demoledor,Partida pEdificio) {
		ralph = demoledor;
		principal=ventana;
		edificio=pEdificio;
	}
	
	@Override
	public void run() {
		
		
	}
}
