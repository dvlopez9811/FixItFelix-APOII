package mundo;
/**
 * Vidrio de una ventana
 * @author 
 *
 */
public class Vidrio {

	public static final int REPARADO = 1;
	public static final int ROTO = 2;

	private int estado;
	private int y;
	private int x;

	public Vidrio(){
		estado=REPARADO;
	}
	

	public int darEstado(){
		return estado;
	}

	public void modificarEstado(int pEstado){
		estado = pEstado;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}
}
