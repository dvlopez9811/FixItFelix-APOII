package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import mundo.Juego;

public class TestJuego {

	private Juego juego;



	private void setupEscenario() {
		juego= new Juego();

	}
	@Test
	public void tesCambiarElEstado() {

		setupEscenario();
		juego.cambiarEstadoJuego(juego.PAUSA);
		assertEquals(juego.darEstadoJuego(), 4);

	}
	@Test
	public void testCantidad() {

		setupEscenario();
		int cantidad=1;

		assertEquals(juego.contarPartidas(juego.darEdificio(), 0), cantidad);		

	}
}
