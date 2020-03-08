package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import mundo.Partida;
import mundo.Felix;
import mundo.Juego;
import mundo.Ladrillo;
import mundo.Ralph;
import mundo.Ventana;

public class PanelJuego extends JPanel implements KeyListener,MouseListener {

	private Felix felix;
	private Ralph ralph;
	private InterfazJuego principal;
	private Partida edificio;

	public PanelJuego(InterfazJuego vent,Felix pFelix,Partida pEdificio,Ralph pRalph) {

		ralph = pRalph;
		edificio = pEdificio;
		principal = vent;
		felix = pFelix;

		setBackground(Color.BLACK);
		
		setLocation(600, 0);
		addKeyListener(this);
		addMouseListener(this);
	}

	public void pintarFinal(Graphics2D g) {
		ImageIcon win = new ImageIcon("./data/imagenes/otros/win.png");
		ImageIcon medal = new ImageIcon("./data/imagenes/otros/medalla.png");
		g.drawImage(win.getImage(),167,124,this);
		g.drawImage(medal.getImage(),730,251,this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);


		if(principal.darJuego().darEstadoJuego()==Juego.JUEGO){

			//Dibuja el edificio
			Graphics2D g2 = (Graphics2D) g;
			ImageIcon edif = new ImageIcon("./data/imagenes/otros/building.png");
			g2.drawImage(edif.getImage(), -100,edificio.getNivelY(), this);
			//////////////////////////////////////Ventanas////////////////////////////////////////////
			for (int i = 0; i < edificio.darVentanas().length; i++) {
				for (int j = 0; j < edificio.darVentanas()[0].length; j++) {
					ImageIcon ven = new ImageIcon("./data/imagenes/ventanas/V"+edificio.darVentanas()[i][j].darEstadoTotal()+".png");
					if(!((i==edificio.darVentanas().length-1 || i==edificio.darVentanas().length-2)&& j==2))
						g2.drawImage(ven.getImage(), edificio.darVentanas()[i][j].darPosicionX(), edificio.darVentanas()[i][j].darPosicionY(), this);
				}
			}
			///////////////////////////////Felix,Ralph y Pato//////////////////////////////////////////
			ImageIcon fel= new ImageIcon("./data/imagenes/felix/F"+felix.darEstado()+".png");
			g2.drawImage(fel.getImage(), felix.darX(),felix.darY(), this);
			ImageIcon ral = new ImageIcon("./data/imagenes/ralph/R"+ralph.darEstado()+".png");
			g2.drawImage(ral.getImage(), ralph.darX(), ralph.darY(), this);
			ImageIcon pat = new ImageIcon("./data/imagenes/otros/P"+edificio.getPato().getEstado()+".png");
			g2.drawImage(pat.getImage(), edificio.getPato().darX(), edificio.getPato().darY(), this);

		
			
			/////////////////////////GANANDO/////////////////////////
			
			if(principal.isTerminada())
				pintarFinal(g2);
			
			///////////////////////////////Encabezado//////////////////////////
			g2.setColor(Color.BLACK);
			g2.fillRect(300, -1, 500, 100);
			g2.setColor(Color.YELLOW);
			g2.drawLine(-1, 99, 1200, 99);
			g2.drawLine(-1, 96, 1200, 96);
			int dist =0;
			ImageIcon vida = new ImageIcon("./data/imagenes/otros/vida.png");
			for (int i = 0; i < felix.darVidas(); i++) {
				g2.drawImage(vida.getImage(), 800+dist , 50, this);
				dist+=40;
			}
			Font fuente=new Font("Haettenschweiler", Font.BOLD, 22);
			g2.setFont(fuente);
			g2.setColor(Color.WHITE);

			g2.drawString(edificio.getPuntaje().getPuntos()+"", 420, 70);
			if(edificio.getPuntaje().getUserName()!=null)
				g2.drawString(edificio.getPuntaje().getUserName(), 120, 70);
			else
				g2.drawString("ANONIMO", 120, 70);
			Font fuenlete=new Font("Haettenschweiler", Font.BOLD, 18);
			g2.setFont(fuenlete);
			g2.setColor(Color.WHITE);
			g2.drawString("MENU", 1000, 38);

			ImageIcon menuPause = new ImageIcon("./data/imagenes/otros/menu.jpeg");
			g2.drawImage(menuPause.getImage(), 1000,50, this);

			Font fuente2=new Font("Haettenschweiler", Font.BOLD,22);
			g2.setFont(fuente2);
			g2.setColor(Color.RED);
			g2.drawString("PUNTAJE", 420, 40);
			g2.drawString("USUARIO", 120, 40);                        

			////////////////////////////////////////////Ladrillos/////////////////////////////////////////////
			if(edificio.getPrimerLadrillo()!=null)
				dibujarLadrillos(edificio.getPrimerLadrillo(), g2);


			//////////////////////////////////Lluvia de vidrios//////////////////////////////////////////
			ImageIcon roto= new ImageIcon("./data/imagenes/otros/vidrioroto.png");
			
			
			
			if(principal.darLLuviaVidrios()!=null){
				for (int i = 0; i < principal.darLLuviaVidrios().size(); i++) {
					g2.drawImage(roto.getImage(), principal.darLLuviaVidrios().get(i).getX(), principal.darLLuviaVidrios().get(i).getY(), this);	
				}
			}
			
			if(principal.isPerdio()) {
				ImageIcon gameover = new ImageIcon("./data/imagenes/otros/over.png");
				g2.drawImage(gameover.getImage(),10,100,this);
			}
		}
		
		////////////////////////////////////////////Instrucciones
		else if(principal.darJuego().darEstadoJuego()==Juego.INSTRUCCIONES){

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.white);
			ImageIcon menu = new ImageIcon("./data/imagenes/menu/instruc.png");
			g2.drawImage(menu.getImage(), 100, 40, this);

			Font fuente=new Font("Tahoma", Font.BOLD, 26);
			g2.setFont(fuente);
			g2.drawString("PRESIONE LA BARRA ESPACIADORA PARA CONTINUAR", 200, 620);
		}
		////////////////////////////////////////////Menu/////////////////////////////////////
		else if(principal.darJuego().darEstadoJuego()==Juego.MENU){

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.white);
			ImageIcon menu = new ImageIcon("./data/imagenes/menu/logo.png");
			g2.drawImage(menu.getImage(), 150, 50, this);
			Font fuente=new Font("Tahoma", Font.BOLD, 26);
			g2.setFont(fuente);
			g2.drawString("PRESIONE LA BARRA ESPACIADORA PARA JUGAR", 230, 500);
		}
		/////////////////////////////////////////Pausa/////////////////////////////////////////
		else if(principal.darJuego().darEstadoJuego()==Juego.PAUSA){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.white);
			ImageIcon menu = new ImageIcon("./data/imagenes/menu/pausa.jpeg");
			g2.drawImage(menu.getImage(), 220, 50, this);
			Font fuente=new Font("Haettenschweiler", Font.BOLD, 18);
			Font fuente2=new Font("Haettenschweiler", Font.BOLD, 32);
			g2.setFont(fuente);
			g2.setColor(Color.WHITE);
			g2.drawString("PRESIONE LA BARRA ESPACIADORA PARA VOLVER", 400, 630);

			g2.setFont(fuente2);
			g2.drawString("VER PUNTAJES", 110, 300);
			g2.drawString("GUARDAR PARTIDA", 110, 513);
			g2.drawString("BUSCAR", 810, 120);
			g2.drawString("CARGAR", 810, 480);
			g2.drawString("NUEVA PARTIDA", 820, 300);

		}
		
	}

	/**
	 * Metodo que dibuja los ladrillos de la lista
	 * @param bloque
	 * @param g
	 */
	public void dibujarLadrillos(Ladrillo bloque,Graphics2D g){
		ImageIcon ladri = new ImageIcon("./data/imagenes/otros/ladrillo.png");
		g.drawImage(ladri.getImage(), bloque.darX(), bloque.darY(), this);	
		if(bloque.getSiguiente()!=null)
			dibujarLadrillos(bloque.getSiguiente(), g);
	}


	
	@Override
	public void keyPressed(KeyEvent e) {

	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int codigo = e.getKeyCode();

		////////////////////Movimiento de Felix//////////////////////////
		if(codigo==37){
			principal.mover(Felix.ATRAS);
		}
		else if(codigo==39){
			principal.mover(Felix.ADELANTE);
		}
		else if(codigo==38){
			principal.mover(Felix.ARRIBA);
		}
		else if(codigo==40){
			principal.mover(Felix.ABAJO);
		}
		else if(codigo==32){
			///////////////////////////////////Reparar///////////////////////////////////
			if(principal.darJuego().darEstadoJuego()==Juego.JUEGO)		
				principal.reparar();
			else{ 
				/////////////////////////////Avanzar entre menús//////////////////////////////
				if(principal.darJuego().darEstadoJuego()==Juego.PAUSA)
					principal.darJuego().darEdificio().setEstaEnJuego(true);
				principal.cambiarEstadoJuego();
			}
		}
		/////////////////////////////Teclas rapidas////////////////////////////////
		else if(codigo==10) {
			principal.pausar();
		}

		else if(codigo==67)
			principal.cargarDeArchivo();
		else if(codigo==71)
			principal.guardarAArchivo();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}



	@Override
	public void mouseClicked(MouseEvent e) {
	
		//System.out.println("x    "+e.getX()+"     y:     "+e.getY());
		if(principal.darJuego().darEstadoJuego()==Juego.JUEGO){
			if((e.getX() > 980 && e.getX() <1060) && (e.getY() > 40 && e.getY()<80) )
				principal.pausar();
		}
		else if(principal.darJuego().darEstadoJuego()==Juego.PAUSA){
			if((e.getX() > 77 && e.getX() <285) && (e.getY() > 245 && e.getY()<320) ){
				principal.mostrarPuntajes();
				principal.cargarDeArchivo();
			}
			else if((e.getX() > 770 && e.getX() <935) && (e.getY() > 70 && e.getY()<150) ){
				principal.buscarPuntajeDeUsuario();
				principal.cargarDeArchivo();
			}
			else if((e.getX() > 770 && e.getX() <935) && (e.getY() > 435 && e.getY()<510) ){
				principal.cambiarEstadoJuego();
				principal.cargarDeArchivo();
			}
			else if(e.getX()>111 && e.getX()<316 && e.getY()>488 && e.getY()<512){
				principal.guardarAArchivo();
			}
			else if(e.getX()>821 && e.getX()<994 && e.getY()>276 && e.getY()<298){
				principal.nuevaPartida();
				principal.cambiarEstadoJuego();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}
