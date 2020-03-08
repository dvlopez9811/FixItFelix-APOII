package hilos;

import interfaz.InterfazJuego;
import mundo.Felix;

public class HiloHerido extends Thread{

	InterfazJuego principal;
	Felix felix;

	public HiloHerido(InterfazJuego ventana,Felix fel){
		principal=ventana;
		felix = fel;
	}

	@Override
	public void run() {
		
		while(felix.tieneInmunidadTemporal() && !felix.estaMuerto()){
//			System.out.println("Pasando por aca");
			if(felix.darEstado()!=20) // 20 no existe para que titile
				felix.modificarEstado(20);
			else
				felix.modificarEstado(0);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			principal.actualizar();
		}
		felix.modificarEstado(0);
		while(felix.estaMuerto()){
			felix.morir();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
			principal.actualizar();
			if(felix.darEstado()==14){
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {}
				
//				principal.terminarPartida();
				//felix.setVidas(3);
				principal.finalizarMuerto();
			}
		}

	}

}
