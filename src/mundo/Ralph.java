package mundo;

public class Ralph extends Personaje {

	/**
	 * Indica si esta entrando
	 */
	private boolean entra;
	/**
	 * Indica si esta demoliendo
	 */
	private boolean demole;

	//Constantes de direccion
	
	public final static int DERECHA=1;
	public static final int IZQUIERDA =2;

	public Ralph(){
		y=555;
		x=1000;
		estado =16;
	}
	
	public void subir(){
		estado = (estado!=13)?13:14;
		y-=10;
	}

	public void entrar(){
		if(x>=550){
			setX(x-15);
			estado = (estado==17) ? 16 : 17;
		}else{
			if(y>=475)
				setY(y-20);
			else{
				if(x>=400 && y>=380)
					setX(x-20);
				else{
					if(y>=380)
						setY(y-20);
					else if(x<450)
						setX(x+20);
					else{
						if(y>=295)
							setY(y-20);
					}
				}
			}
			estado = (estado!=13) ? 13 : 14;
		}
	}

	
	public void mover(int direccion,int min,int max){
		switch (direccion){
		case IZQUIERDA:
			estado = (estado !=16)?16:17;
			if(x>min)
				x-=6;
			else{
				estado=0;
				seMueve=false;
			}
			break;
		case DERECHA:
			estado = (estado!=1) ? 1 : 2;
			if(x<max)
				x+=6;
			else{
				estado=0;
				seMueve=false;
			}
			break;
		}

	}

	public void demoler(){
		estado = (estado!=11) ? 11 : 12;
	}
	public boolean estaEntrando() {
		return entra;
	}

	public void setEntrando(boolean entra) {
		this.entra = entra;
	}

	public boolean estaDemoliendo() {
		return demole;
	}

	public void setDemole(boolean demole) {
		this.demole = demole;
	}

}
