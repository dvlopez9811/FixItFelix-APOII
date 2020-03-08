package hilos;


import interfaz.InterfazJuego;
import mundo.Partida;
import mundo.Ralph;

public class HiloMoverRalph extends Thread{
	
	/**
	 * Hilo principal que desarrolla el movimiento de Ralph cuando la partida esta en juego
	 */

	private InterfazJuego principal;
	private Ralph ralph;
	private Partida partida;

	public HiloMoverRalph(InterfazJuego ventana,Ralph pRalph,Partida pEdificio){
		principal = ventana;
		ralph = pRalph;
		partida=pEdificio;
	}

	public void run(){
		while(partida.getEstadoActualPartida()==Partida.CORRIENDO && !partida.terminada()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {}
			
			
			int dist = (int)(Math.random()*2+1);
			int min = (dist==1) ? 392 : 492;
			int max = (dist==1) ? 577 : 477;
			int direccion = (int)(Math.random()*2+1);
			while(ralph.estaEnMovimiento() && !partida.terminada()){
				ralph.setDemole(true);
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) { }
				if(partida.estaEnJuego())
				ralph.mover(direccion,min,max);
				principal.actualizar();
				if(partida.estaEnJuego())
				ralph.setY(partida.darVentanas()[partida.darAlturaActual()][0].darPosicionY()-118);
			}
			
			while(ralph.estaDemoliendo() && partida.estaEnJuego() && !partida.terminada()){
				partida.darFelix().setInmunidadTemporal(false);
				ralph.modificarSeMueve(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				if(partida.estaEnJuego()){
				ralph.setY(ralph.darY()+13);
				ralph.demoler();}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				principal.actualizar();
				if(partida.estaEnJuego())
				ralph.demoler();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				principal.actualizar();
				if(partida.estaEnJuego()){
				ralph.demoler();
				partida.darUltimoLadrillo(partida.getPrimerLadrillo()).setSiguiente(partida.crearLadrillo());
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				principal.actualizar();
				if(partida.estaEnJuego()){
				ralph.demoler();
				partida.darUltimoLadrillo(partida.getPrimerLadrillo()).setSiguiente(partida.crearLadrillo());
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				principal.actualizar();
				if(partida.estaEnJuego())
				ralph.demoler();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				principal.actualizar();
				if(partida.estaEnJuego()){
				ralph.demoler();
				partida.darUltimoLadrillo(partida.getPrimerLadrillo()).setSiguiente(partida.crearLadrillo());
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
				if(partida.estaEnJuego()){
				ralph.modificarEstado(0);
				ralph.setY(partida.darVentanas()[partida.darAlturaActual()][0].darPosicionY()-118);
				}
				principal.actualizar();
				
				ralph.setDemole(false);
			}
			if(!ralph.estaDemoliendo() && !ralph.estaEnMovimiento()){
				partida.setEstadoActualPartida(Partida.CAMBIO_ESTACION);
				principal.verificarEstacion();
			}
		}
}
}
