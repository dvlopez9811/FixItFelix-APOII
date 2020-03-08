package mundo;

public class Ventana {

	/**
	 * Coordenada en x
	 */
	private int posicionX;
	/**
	 * Coordenada en y
	 */
	private int posicionY;
	/**
	 * Vidrio superior
	 */
	private Vidrio superior;
	/**
	 * Vidrio inferior
	 */
	private Vidrio inferior;
	
	private int estadoTotal;


	public Ventana(int x,int y){

		superior = new Vidrio();
		inferior = new Vidrio();
		posicionX=x;
		posicionY=y;
		actualizar();
	}
	
	
	/**
	 * Asigna un estado a la ventana dependiendo del estado de sus vidrios
	 */
	public void actualizar(){
		
		int est = (int) (Math.random()*30);
		if(superior.darEstado()==Vidrio.REPARADO && inferior.darEstado()==Vidrio.REPARADO)
			cambiarEstadoTotal(0);
		else if(superior.darEstado()==Vidrio.REPARADO && inferior.darEstado()==Vidrio.ROTO){
			if(est%2==0)
				cambiarEstadoTotal(3);
			else
				cambiarEstadoTotal(4);
		}else if(superior.darEstado()==Vidrio.ROTO && inferior.darEstado()==Vidrio.ROTO){
			if(est%2==0)
				cambiarEstadoTotal(1);
			else
				cambiarEstadoTotal(5);
		}else if(superior.darEstado()==Vidrio.ROTO && inferior.darEstado()==Vidrio.REPARADO){
			if(est%2==0)
				cambiarEstadoTotal(2);
			else
				cambiarEstadoTotal(6);
		}
	}

	/**
	 * Crea un nuevo vidrio
	 * @return
	 */
	public Vidrio crearVidrio(){
		Vidrio nuevo = new Vidrio();
		int rand = (int) Math.random()*2+1;
		nuevo.modificarEstado(rand);
		return nuevo;
	}
	
	/**
	 * Rompe aleatoriamente sus vidrios
	 */
	public void romperAleatorio(){
		int rand = (int) (Math.random()*2+1);
		int rand2 = (int) (Math.random()*2+1);
		superior.modificarEstado(rand);
		inferior.modificarEstado(rand2);
		actualizar();
		
	}
	
	/**
	 * Rompe ambos vidrios
	 */
	public void romper(){
		superior.modificarEstado(Vidrio.ROTO);
		inferior.modificarEstado(Vidrio.ROTO);
		actualizar();
	}
	

	/**
	 * Verifica si ambos vidrios se encuentran reparados o no
	 * @return
	 */
	public boolean estaReparada(){
		return (superior.darEstado()==Vidrio.REPARADO && inferior.darEstado()==Vidrio.REPARADO);
	}
	
	/////////////////////////////Getters y setters////////////////////////
	
	public int darPosicionX(){
		return posicionX;
	}

	public int darPosicionY(){
		return posicionY;
	}


	public void modificarPosicionX(int pPosicion){
		posicionX = pPosicion;
	}

	public void modificarPosicionY(int pPosicion){
		posicionY = pPosicion;
	}



	public Vidrio darSuperior() {
		return superior;
	}



	public void cambiarSuperior(Vidrio superior) {
		this.superior = superior;
	}



	public Vidrio darInferior() {
		return inferior;
	}



	public void cambiarInferior(Vidrio inferior) {
		this.inferior = inferior;
	}

	public int darEstadoTotal() {
		return estadoTotal;
	}

	public void cambiarEstadoTotal(int estadoTotal) {
		this.estadoTotal = estadoTotal;
	}
}
