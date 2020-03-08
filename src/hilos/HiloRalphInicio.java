package hilos;

import interfaz.InterfazJuego;
import mundo.Partida;
import mundo.Juego;
import mundo.Ralph;

public class HiloRalphInicio extends Thread{
	
	//Hilo con la entrada de Ralph

	private InterfazJuego principal;
	private Ralph ralph;
	private Partida edificio;

	public HiloRalphInicio (InterfazJuego ventana,Ralph demoledor,Partida pEdificio){
		ralph = demoledor;
		principal=ventana;
		edificio=pEdificio;
	}

	public void run() {
		
		while(ralph.estaEntrando() && !ralph.estaDemoliendo()){
			principal.darJuego().darEdificio().darFelix().setPuedeMoverse(false);
			if(ralph.darY()>295){
				ralph.entrar();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				for (int i = edificio.darVentanas().length-4; i < edificio.darVentanas().length; i++) {
					for (int j = 0; j < edificio.darVentanas()[0].length; j++) {
						if(edificio.darVentanas()[i][j].darPosicionY() < ralph.darY()+40 && edificio.darVentanas()[i][j].darPosicionY()+35>ralph.darY()+40
								&& 	edificio.darVentanas()[i][j].darPosicionX() < ralph.darX()+40 && (edificio.darVentanas()[i][j].darPosicionX()+50) > ralph.darX()+40 && j<4)
							edificio.darVentanas()[i][j].romper();
					}
				}
			}else{
				ralph.modificarEstado(5);
				ralph.setY(295);
				principal.actualizar();
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
				}
				ralph.setY(275);
				ralph.setX(ralph.darX()+3);
				ralph.modificarEstado(6);
				principal.actualizar();
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {}
				ralph.setDemole(true);
				ralph.setY(310);
				ralph.setEntrando(false);
			}
			principal.actualizar();
		}
		principal.caidaVidrios();
		while(ralph.estaDemoliendo()){
			ralph.demoler();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			principal.actualizar();
			ralph.demoler();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			principal.actualizar();
			ralph.demoler();
			edificio.romperAleatorio();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			principal.actualizar();
			ralph.demoler();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {}
			ralph.modificarEstado(5);
			ralph.setY(295);
			principal.actualizar();
			ralph.setDemole(false);
			principal.caidaVidrios();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
			edificio.setEstadoActualPartida(Partida.CORRIENDO);
			principal.moverRalph();	
			principal.darJuego().darEdificio().darFelix().setInmunidadTemporal(false);
//			principal.pausar();
			principal.darJuego().darEdificio().darFelix().setPuedeMoverse(true);

		}
	}
}
