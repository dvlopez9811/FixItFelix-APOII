package mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import excepciones.YaTieneTresPuntajesException;
/**
 * Clase Principal
 * @author 
 *
 */
public class Juego {

	/**
	 * Ruta del archivo de la ultima partida guardad
	 */
	public static final String RUTA_PARTIDA = "./data/partidaGuardada/ultima.txt";
	/**
	 * Ruta de la ubicacion del puntaje serializado
	 */
	public static final String RUTA_PUNTAJE = "./data/puntajes/puntaje.fixitfelix";

	//Estados de juego

	public final static int JUEGO =1;
	public final static int MENU =2;
	public final static int INSTRUCCIONES =3;
	public final static int PAUSA=4;
	/**
	 * estado actual de juego
	 */

	private boolean restart;

	private int estadoJuego;
	/**
	 * Partida de juego
	 */
	private Partida partidaActual;

	/**
	 * Lista de los mayores puntajes
	 */
	private ListaPuntajes puntajes;
	/**
	 * Raiz del arbol binario que contiene las partidas jugadas en una sesión
	 */
	private Partida raizPartidas;


	////////////////////////////////////////////////////////////
	//Constructor
	////////////////////////////////////////////////////////////
	public Juego(){
		partidaActual = new Partida();
		puntajes = new ListaPuntajes();
		estadoJuego=MENU;
	}


	/////////////////////////////////////////////////////////////
	//Metodos
	/////////////////////////////////////////////////////////////

	/**
	 * Crea una nueva partida y la selecciona como la partida actual
	 */
	public void nuevaPartida(){
		Partida nueva = new Partida();
		agregarPartida(nueva, raizPartidas);
		partidaActual = nueva;
		cambiarEstadoJuego(JUEGO);
	}

	/**
	 * Agrega una partida nueva al arbol binario
	 * @param nuevo
	 * @param actual
	 */
	public void agregarPartida(Partida nuevo, Partida actual){

		if(raizPartidas==null){
			raizPartidas=nuevo;
		}else{
			if(actual.getIzquierda()==null){
				actual.setIzquierda(nuevo);
			}else if(actual.getDerecha()==null){
				actual.setDerecha(nuevo);
			}
			else
				agregarPartida(nuevo, actual.getIzquierda());
		}

	}

	/**
	 * Cuenta las partidas que existen en el arbol
	 * @param actual
	 * @param conteo
	 * @return
	 */
	public int contarPartidas(Partida actual,int conteo){
		if(actual!=null){
			conteo = conteo+1;
			if(actual.getIzquierda()!=null)
				conteo = contarPartidas(actual.getIzquierda(), conteo);
			if(actual.getDerecha()!=null)
				conteo= contarPartidas(actual.getDerecha(), conteo);
		}
		return conteo;
	}



	/**
	 * Guarda la informacion de la partida actual en un archivo de texto plano
	 * @throws IOException
	 */
	public void guardar() throws IOException{
		File nuevo= new File(RUTA_PARTIDA);
		PrintWriter pintar= new PrintWriter(nuevo);
		//	felix
		pintar.println("F" );
		pintar.println( partidaActual.darFelix().darEstado()  +"\t"+ partidaActual.darFelix().darVidas() +"\t"+partidaActual.darFelix().darX()+"\t"+partidaActual.darFelix().darY());
		//ralph);
		pintar.println("R");
		pintar.println( partidaActual.darRalph().darEstado() +"\t"+ partidaActual.darRalph().darX()+"\t"+partidaActual.darRalph().darY() );
		//estacion actual
		pintar.println("E");
		pintar.println( partidaActual.getEstacionActual());
		//estadoActualPartida
		pintar.println("S");
		pintar.println( partidaActual.getEstadoActualPartida());
		//Ventana actual (I.J)
		pintar.println("V");
		pintar.println(partidaActual.darIActual()+ "\t" +partidaActual.darJActual());
		//Nivel Edificio
		pintar.println("Y");
		pintar.println(partidaActual.getNivelY());
		//Dimensiones
		pintar.println("D");
		pintar.println(partidaActual.darBaseActual() +"\t"+partidaActual.darAlturaActual());
		pintar.println("P");
		pintar.println(partidaActual.getPuntaje().getPuntos() +"\t"+ partidaActual.getPuntaje().getUserName());

		//Matriz Ventana
		pintar.println("#Matriz");
		for (int i =0;  i < partidaActual.darVentanas().length; i++) {
			if(i>0)
				pintar.println("");
			for (int  j=0;  j< partidaActual.darVentanas()[0].length; j++) {
				pintar.print(partidaActual.darVentanas()[i][j].darEstadoTotal()+"\t");
			}	
		}
		pintar.close();
	}


	/**
	 * Carga el archivo con la ultima partda guardada
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void cargar()throws FileNotFoundException, IOException{

		File archivo = new File(RUTA_PARTIDA);
		FileReader reader = new FileReader(archivo);
		BufferedReader lector = new BufferedReader(reader);

		String linea ;
		while (((linea=lector.readLine())!=null ||  linea.trim().length()!=0) && !linea.startsWith("#")) {
			if(linea.startsWith("F")){
				linea=lector.readLine();
				String[] datos = linea.split("\t");
				int estado=0;
				int vidas=0;
				int x=0;
				int y=0;

				estado = Integer.parseInt(datos[0]);
				vidas=Integer.parseInt(datos[1]);
				x=Integer.parseInt(datos[2]);
				y=Integer.parseInt(datos[3]);
				partidaActual.darFelix().modificarEstado(estado);
				partidaActual.darFelix().setVidas(vidas);
				partidaActual.darFelix().setX(x);
				partidaActual.darFelix().setY(y);
			}
			else if(linea.startsWith("R")){
				linea=lector.readLine();
				String[] datos = linea.split("\t");
				int estado=0;
				int x=0;
				int y=0;
				estado=Integer.parseInt(datos[0]);
				x=Integer.parseInt(datos[1]);
				y=Integer.parseInt(datos[2]);

				partidaActual.darRalph().modificarEstado(estado);
				partidaActual.darRalph().setX(x);
				partidaActual.darRalph().setY(y);	
			}
			else if(linea.startsWith("E")){
				linea= lector.readLine();
				int dato= Integer.parseInt(linea);
				partidaActual.setEstacionActual(dato);	
			}
			else if(linea.startsWith("S")){
				linea= lector.readLine();
				int dato= Integer.parseInt(linea);
				partidaActual.setEstadoActualPartida(dato);
			}
			else if(linea.startsWith("S")){

				linea= lector.readLine();
				int dato= Integer.parseInt(linea);
				partidaActual.setEstadoActualPartida(dato);	
			}
			else if(linea.startsWith("V")){
				linea=lector.readLine();
				String[] datos = linea.split("\t");
				int j=0;
				int k=0;
				k=Integer.parseInt(datos[0]);
				j=Integer.parseInt(datos[1]);
				partidaActual.modificarIActual(k);
				partidaActual.modificarJActual(j);	
			}
			else if(linea.startsWith("Y")){	
				linea= lector.readLine();
				int dato= Integer.parseInt(linea);
				partidaActual.setNivelY(dato);
			}
			else if(linea.startsWith("D")){
				linea=lector.readLine();
				String[] datos = linea.split("\t");
				int base=0;
				int Altura=0;
				base=Integer.parseInt(datos[0]);
				Altura=Integer.parseInt(datos[1]);
				partidaActual.setBaseActual(base);
				partidaActual.setAlturaActual(Altura);	
			}
			else if(linea.startsWith("P")){
				linea=lector.readLine();
				String[] datos = linea.split("\t");
				int puntos=0;
				String user="";
				puntos=Integer.parseInt(datos[0]);
				user=(datos[1]);
				partidaActual.getPuntaje().setPuntos(puntos);
				partidaActual.getPuntaje().setUserName(user);

			}
		}
		//Lee la matriz en el archivo
		Ventana[][] nuevas = new Ventana[partidaActual.darVentanas().length][partidaActual.darVentanas()[0].length];
		linea=lector.readLine();	
		String[] valores=linea.split("\t");
		for (int i = 0; i <= partidaActual.darVentanas().length-1; i++) {
			if(i>0){
				linea = lector.readLine();
				valores = linea.split("\t");
			}
			for (int j = 0; j < partidaActual.darVentanas()[0].length; j++) {

				int dato = Integer.parseInt(valores[j]);
				nuevas[i][j] = new Ventana(partidaActual.darVentanas()[i][j].darPosicionX(), partidaActual.darVentanas()[i][j].darPosicionY());
				if(dato==0){
					nuevas[i][j].darSuperior().modificarEstado(Vidrio.REPARADO);
					nuevas[i][j].darInferior().modificarEstado(Vidrio.REPARADO);	
				}else if(dato==1 || dato==5){
					nuevas[i][j].darSuperior().modificarEstado(Vidrio.ROTO);
					nuevas[i][j].darInferior().modificarEstado(Vidrio.ROTO);	
				}			
				else if(dato==2 || dato==6){
					nuevas[i][j].darSuperior().modificarEstado(Vidrio.ROTO);
					nuevas[i][j].darInferior().modificarEstado(Vidrio.REPARADO);
				}
				else if(dato==3 || dato==4){
					nuevas[i][j].darSuperior().modificarEstado(Vidrio.REPARADO);
					nuevas[i][j].darInferior().modificarEstado(Vidrio.ROTO);
					//							}	
				}	
				nuevas[i][j].actualizar();
			}
		}
		partidaActual.setVentanas(nuevas);
		lector.close();
		reader.close();
		cargarPuntaje();
	}



	////////////////////////////////////////////////
	//Puntaje
	////////////////////////////////////////////////

	public void cargarPuntaje(){

		File puntaje;
		FileInputStream fis;
		ObjectInputStream in;

		try {
			puntaje = new File(RUTA_PUNTAJE);
			fis = new FileInputStream(puntaje);
			in = new ObjectInputStream(fis);
			puntajes = (ListaPuntajes) in.readObject();
			in.close();
			fis.close();

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void serializar(){
		ObjectOutputStream out;
		FileOutputStream fos;
		try {
			File puntaje = new File(RUTA_PUNTAJE);
			fos = new FileOutputStream(puntaje);
			out = new ObjectOutputStream(fos);
			out.writeObject(puntajes);
			out.close();
			fos.close();
		} catch (IOException e){
		}
	}

	/**
	 * Guarda el puntaje de la partida actual y lo serializa
	 * @throws YaTieneTresPuntajesException
	 */
	public void guardarPuntajeActual() throws YaTieneTresPuntajesException{
		puntajes.agregarPuntaje(partidaActual.getPuntaje());
		serializar();
	}

	/**
	 * Busca el puntaje de un usuario
	 * @param user
	 * @return
	 */
	public Puntaje buscarPuntaje( String user ){
		return puntajes.buscarPuntaje(user);
	}


	////////////////////////////////////////////
	//Getters y Setters
	///////////////////////////////////////////
	public String darPuntajes(){
		return puntajes.darPuntajes();
	}

	public int darEstadoJuego() {
		return estadoJuego;
	}

	public Partida darEdificio(){
		return partidaActual;
	}

	public void setEdificio(Partida pEdificio){
		this.partidaActual=pEdificio;
	}


	public void cambiarEstadoJuego(int estadoJuego) {
		this.estadoJuego = estadoJuego;
	}

	public Partida getRaizPartidas() {
		return raizPartidas;
	}

	public void setRaizPartidas(Partida raizPuntaje) {
		this.raizPartidas = raizPuntaje;
	}


	public boolean isRestart() {
		return restart;
	}


	public void setRestart(boolean restart) {
		this.restart = restart;
	}

}
