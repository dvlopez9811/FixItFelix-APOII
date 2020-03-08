package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import mundo.Partida;
import mundo.Vidrio;

public class TestPartida {

	private Partida partida;

	private void setupEscenario() {
		partida=new Partida();

	}

	private void setupEscenario1() {
		partida=new Partida();
		partida.setEstacionActual(4);

	}
	private void setupEscenario2(){

		partida= new Partida();
		for (int i = partida.darAlturaActual(); i <= partida.darBaseActual(); i++) {
			for (int j = 0; j < partida.darVentanas()[0].length; j++) {
				partida.darVentanas()[i][j].darSuperior().modificarEstado(Vidrio.REPARADO);
				partida.darVentanas()[i][j].darInferior().modificarEstado(Vidrio.REPARADO);
			}
		}
	}

	@Test
	public void testEstacionActual() {
		setupEscenario();
		assertEquals(partida.getEstacionActual(), 1);
	}


	@Test
	public void testCambiaEstacionActual() {
		setupEscenario();
		partida.setEstacionActual(6);
		assertEquals(partida.getEstacionActual(), 6);
	}

	@Test
	public void testSubirEstacionActual() {
		setupEscenario();
		partida.subirEstacion();
		assertEquals(partida.getEstacionActual(), 2);
	}

	@Test
	public void testCambiaRetornaNivelY() {

		setupEscenario();
		partida.setNivelY(30);
		assertEquals(partida.getNivelY(), 30);
	}

	@Test
	public void testquitarVida() {

		setupEscenario();
		partida.quitarVida();
		assertEquals(partida.darFelix().darVidas(), 2);
	}

	@Test
	public void testestacionSuperada() {

		setupEscenario2();

		assertEquals(partida.estacionSuperada(), true);
	}


	@Test
	public void testRepararVentana() {
		setupEscenario();
		partida.repararVentana();

		int estadoReparado=0;


		assertEquals(partida.getVentanaActual().darEstadoTotal(), estadoReparado);


	}

	@Test
	public void testcreaLadrillo(){
		setupEscenario();
		assertEquals(partida.crearLadrillo(), partida.getPrimerLadrillo());

	}



	@Test

	public void testDaEstadoJuego(){
		setupEscenario();

		assertEquals(partida.estaEnJuego(), false);

	}


	@Test

	public void testCambiaEstadoJuego(){
		setupEscenario();
		partida.setEstaEnJuego(true);
		assertEquals(partida.estaEnJuego(), true);

	}

	@Test

	public void testRomperAleatorio(){
		setupEscenario();
		int estadoReparado=0;
		partida.romperAleatorio();
		assertNotEquals(partida.getVentanaActual().darEstadoTotal(), estadoReparado);

	}

	@Test
	public void mover(){
		setupEscenario();
		int jactual=partida.darJActual();
		int estadoAtras=1;

		partida.mover(estadoAtras);
		assertEquals(partida.darJActual(), jactual-1);

	}

}
