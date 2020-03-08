package interfaz;

import java.awt.BorderLayout;
import java.applet.*;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import excepciones.YaTieneTresPuntajesException;
import hilos.HiloAscender;
import hilos.HiloCaidaLadrillos;
import hilos.HiloFinal;
import hilos.HiloHerido;
import hilos.HiloMoverFelix;
import hilos.HiloMoverRalph;
import hilos.HiloRalphInicio;
import hilos.HiloReparar;
import hilos.HiloVidrios;
import hilos.HiloVueloPato;
import mundo.Partida;
import mundo.Puntaje;
import mundo.Felix;
import mundo.Juego;
import mundo.Ralph;
import mundo.Vidrio;

public class InterfazJuego extends JFrame{


	private ArrayList<Thread> hilos;
	/**
	 * Panel que pinta toda la informacion
	 */
	private PanelJuego panelJuego;
	/**
	 * jUEGO
	 */
	private static Juego juego;
	/**
	 * Partida de juego actual
	 */
	private Partida partida;

	/**
	 * Arreglo de vidrios usado para la animacion de intro
	 */
	private ArrayList<Vidrio> vidrios;


	public InterfazJuego(){

		active=true;
		hilos = new ArrayList<Thread>();
		setLayout(new BorderLayout());

		juego = new Juego();
		partida = juego.darEdificio();

		juego.cargarPuntaje();
		panelJuego = new PanelJuego(this,partida.darFelix(),partida,partida.darRalph());
		add(panelJuego);
		addKeyListener(panelJuego);
		pack();
		setSize(1100, 685);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	/////////////////////////////////////Ejecución del juego///////////////////////////

	/**
	 * Animación de entrada
	 */
	public void intro(){
		partida.darRalph().setEntrando(true);
		HiloRalphInicio hiloRalphInicio = new HiloRalphInicio(this,partida.darRalph(),partida);		
		hiloRalphInicio.start();
		hilos.add(hiloRalphInicio);
	}


	public void animacionFinal() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		partida.darFelix().setInmunidadTemporal(true);
		partida.darFelix().setPuedeMoverse(false);
		partida.darFelix().modificarEstado(15);
		partida.darRalph().modificarEstado(15);
		terminada=true;
		panelJuego.repaint();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	public void eliminarHilos() {
		for (int i = 0; i < hilos.size(); i++) {
			hilos.get(i).destroy();
		}
	}

	public void finalizarMuerto() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		perdio=true;
		panelJuego.repaint();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);		
	}

	private boolean terminada;
	private boolean perdio;

	/**
	 * Mueve el personaje
	 * @param direccion
	 */
	public void mover(int direccion){
		if(!partida.darFelix().estaReparando() && partida.estaEnJuego() && !partida.darFelix().estaMuerto() 
				&& partida.darFelix().puedeMoverse()){

			juego.darEdificio().mover(direccion);
			panelJuego.repaint();
			partida.darFelix().modificarSeMueve(true);
			HiloMoverFelix hiloMove = new HiloMoverFelix(partida.darFelix(), this,direccion);
			hiloMove.start();
			hilos.add(hiloMove);
			panelJuego.repaint();
		}
	}
	/**
	 * Crea el hilo principal que mantiene a ralph moviendose y demoliendo
	 */
	public void moverRalph(){
		if(!partida.estacionSuperada()) {
		partida.darRalph().modificarSeMueve(true);
		HiloMoverRalph hilo = new HiloMoverRalph(this, partida.darRalph(),partida);
		hilo.start();
		hilos.add(hilo);}
	}

	/**
	 * Repara la ventana actual
	 */
	public void reparar(){
		if(!partida.darFelix().estaEnMovimiento() && partida.estaEnJuego() &&!partida.darFelix().estaMuerto()){
			panelJuego.repaint();
			partida.darFelix().modificarReparando(true);
			HiloReparar hiloReparar = new HiloReparar(this, partida.darFelix());
			hiloReparar.start();
			hilos.add(hiloReparar);
			panelJuego.repaint();
		}
	}

	/**
	 * Sube la estación
	 */
	public void ascender(){

		if(partida.darAlturaActual()!=0){
			partida.setEstadoActualPartida(Partida.CAMBIO_ESTACION);
			partida.darRalph().modificarSeMueve(false);
			HiloAscender hiloAscender = new HiloAscender(this, partida.darFelix(), partida,partida.darRalph());
			hiloAscender.start();	
			hilos.add(hiloAscender);
		}else{
			//terminarPartida();
			animacionFinal();
		}
	}

	/**
	 * Ingresa el usuario y lo ingresa en el puntaje de la partida
	 */
	public void inscribirUsuario(){
		partida.getPuntaje().setUserName(JOptionPane.showInputDialog("Ingrese su nombre de usuario"));
	}

	/**
	 * Genera una nueva partida
	 */
	public void nuevaPartida(){
		panelJuego.updateUI();
		juego.nuevaPartida();
		partida = juego.darEdificio();

		panelJuego= new PanelJuego(this, partida.darFelix(), partida, partida.darRalph());
		HiloCaidaLadrillos caida= new HiloCaidaLadrillos(this, partida);

		caida.start();
		HiloVueloPato vuelo = new HiloVueloPato(this, partida.getPato());
		vuelo.start();
		add(panelJuego);
		actualizar();	
	}


	public void cambiarEstadoJuego(){
		if(juego.darEstadoJuego()==Juego.MENU){
			actualizar();
			juego.cambiarEstadoJuego(Juego.INSTRUCCIONES);
			actualizar();
		}
		else if (juego.darEstadoJuego()==Juego.INSTRUCCIONES){	
			actualizar();
			juego.cambiarEstadoJuego(Juego.JUEGO);
			actualizar();
			nuevaPartida();
			inscribirUsuario();
			intro();
		}
		else if(juego.darEstadoJuego()==Juego.JUEGO){
			nuevaPartida();
			inscribirUsuario();
			intro();
			actualizar();
		}

		else if(juego.darEstadoJuego()==Juego.PAUSA){
			juego.cambiarEstadoJuego(Juego.JUEGO);
			actualizar();
		}

	}

	/**
	 * Carga la informacion de la ultima partida guardada
	 * @return
	 */
	public boolean cargarDeArchivo(){
		boolean cargoArchivo = false;

		try{
			juego.cargar();
			actualizar();
			cargoArchivo = true;
		}catch(IOException ioexc){
			JOptionPane.showMessageDialog(this, "Problemas leyendo el archivo\nEs probable que el formato no sea válido.");
		}

		return cargoArchivo;
	}

	/**
	 * Guarda la informacion d ela partida actual
	 * @return
	 */
	public boolean guardarAArchivo(){
		boolean guardoArchivo = false;
		try{
			juego.guardar();
			guardoArchivo = true;
			JOptionPane.showMessageDialog(this, "Se guardó la partida");
		}catch(Exception ioexc){
			JOptionPane.showMessageDialog(this, "Problemas guardando el archivo\nEs probable que no tenga permisos de escritura o\nel archivo puede estar bloqueado por otro programa.");
		}
		return guardoArchivo;	
	}

	/**
	 * Muestra los diez mejores puntajes
	 */
	public void mostrarPuntajes(){
		JOptionPane.showMessageDialog(this, juego.darPuntajes());
	}

	public void buscarPuntajeDeUsuario(){
		String nick = JOptionPane.showInputDialog(this,"Ingrese el nombre del usuario para conocer su puntaje");
		if(!nick.equals(null)){
			Puntaje busc= juego.buscarPuntaje(nick);
			if(busc!=null)
				JOptionPane.showMessageDialog(this, "El puntaje de "+nick+" es "+busc.getPuntos());
			else
				JOptionPane.showMessageDialog(this, "No se encontró puntaje de "+nick);
		}
	}

	/**
	 * Pausa la partida
	 */
	public void pausar(){
		if(partida.estaEnJuego()){
			partida.setEstaEnJuego(false);
			juego.cambiarEstadoJuego(Juego.PAUSA);
		}
		else
			partida.setEstaEnJuego(true);
	}

	public void verificarEstacion(){
		if(partida.estacionSuperada()) {
			partida.darFelix().setInmunidadTemporal(true);
			ascender();
		}
	}

	/**
	 * Pone fin a la partida actual y vuelve al menú
	 */
	public void terminarPartida(){
		active=false;
		try {
			juego.guardarPuntajeActual();

		} catch (YaTieneTresPuntajesException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		juego.cambiarEstadoJuego(Juego.MENU);
	}


	/**
	 * Quita una vida del personaje y crea un hilo que muestra la animación
	 */
	public void quitarVida(){
		if(!partida.darFelix().tieneInmunidadTemporal()){
			partida.darFelix().quitarVida();
			HiloHerido danio= new HiloHerido(this, partida.darFelix());
			danio.start();
		}
		actualizar();
	}

	/**
	 * Contar las partidas en el arbol
	 */
	public void contar(){
		JOptionPane.showMessageDialog(this, juego.contarPartidas(juego.getRaizPartidas(), 0));
	}
	/**
	 * Repara una estacion completa
	 */
	public void repararToda(){
		partida.repararEstacion();
		actualizar();
		verificarEstacion();
	}


	/**
	 * Repinta el panel de juego
	 */
	public void actualizar(){
		panelJuego.repaint();
	}

	//////////////////////////////////Sonido/////////////////////////////////////////////////
	public void sonadorIntro(){
		Clip audioss;
		AudioClip audio;
		String link = "./audio/INTRO_PROYECTO.wav"; 
		try {	
			audioss= AudioSystem.getClip();
			audioss.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(link)));
			audioss.start();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	public static void musicafondo() {
		while (!(juego.darEstadoJuego()==juego.JUEGO)) {
			try{
				String link = "./audio/INTRO_PROYECTO.wav";   
				File fill = new File(link) ;

				Clip clip= AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(fill));
				clip.start();

				Thread.sleep(clip.getMicrosecondLength()/1000);
			}catch(Exception e){
				System.err.println(e);
			}
		}
	}
	static void soundFondo(){
		while (juego.darEstadoJuego()==juego.JUEGO) {
			try{
				String link = "./audio/JUGANDO_PROYECTO.wav";   
				File fill = new File(link) ;

				Clip clip= AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(fill));
				clip.start();

				Thread.sleep(clip.getMicrosecondLength()/1000);
			}
			catch(Exception e){
				System.err.println(e);
			}
		}
	}


	////////////////////////////////////////Vidrios de intro////////////////////////
	public void lluviaVidrios(){
		for (int i = 0; i < vidrios.size(); i++) {
			vidrios.get(i).setY(vidrios.get(i).getY()+1);	
		}
	}                        


	public void crearVidrios(){
		for (int i = 6; i < partida.darVentanas().length; i++) {
			for (int  k= 0; k < partida.darVentanas()[0].length; k++) {
				if(!(partida.darVentanas()[i][k].estaReparada())){
					Vidrio newv= new Vidrio();
					newv.setX(partida.darVentanas()[i][k].darPosicionX()+12);
					newv.setY(partida.darVentanas()[i][k].darPosicionY());
					vidrios.add(newv);
				}
			}
		}
	}
	public void caidaVidrios(){
		vidrios = new ArrayList<>();
		crearVidrios();
		HiloVidrios lluvia = new HiloVidrios(this);
		lluvia.start();
	}


	/////////////////////////////Getters////////////////////////////////////////
	public Juego darJuego(){
		return juego;
	}

	public ArrayList<Vidrio> darLLuviaVidrios() {
		return vidrios;
	}

	public void restartApplication() throws URISyntaxException, IOException {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(InterfazJuego.class.getProtectionDomain().getCodeSource().getLocation().toURI());

		/* is it a jar file? */
		if(!currentJar.getName().endsWith(".jar"))
			return;

		/* Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());

		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.start();
		System.exit(0);
	}
	
	private boolean active;
	
public boolean estaActivo() {
	return active;
}

	public static void main(String[] args) {
		InterfazJuego ventana = new InterfazJuego();

		ventana.setTitle("Fix It Felix - Icesi Interactiva");
			ventana.setVisible(true);
			ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			while(true) {
			if(juego.darEstadoJuego()==juego.JUEGO)
				soundFondo();
			else
				musicafondo();
			}
			
		
	}
	public boolean isTerminada() {
		return terminada;
	}

	public void setTerminada(boolean terminada) {
		this.terminada = terminada;
	}

	public boolean isPerdio() {
		return perdio;
	}

	public void setPerdio(boolean perdio) {
		this.perdio = perdio;
	}


}