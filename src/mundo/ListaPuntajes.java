package mundo;

import java.io.Serializable;

import excepciones.YaTieneTresPuntajesException;
/**
 * Contiene la lista de puntajes en un arreglo
 * @author David
 *
 */
public class ListaPuntajes implements Serializable{

	/**
	 * Arreglo con los mejores puntajes
	 */
	private Puntaje[] puntajes;


	//Constructor

	public ListaPuntajes(){
		puntajes = new Puntaje[10];
		for (int i = 0; i < puntajes.length; i++) {
			puntajes[i]=new Puntaje("", 0);
		}	
	}

	/**
	 * Verifica si existen menos de 3 puntajes que tengan el mismo nombre de usuario dentro del arreglo de los 10 mejores puntajes
	 * @param usuario
	 * @return
	 */
	public boolean yaEstaTresVeces(String usuario){
		int contador =0;
		for (int i = 0; i < puntajes.length; i++) {
			if(puntajes[i].getUserName().equals(usuario))
				contador++;
		}
		boolean repite = (contador<3)?false:true;

		return repite;
	}

	/**
	 * Agrega un nuevo puntaje en el arreglo si este es mayor que el puntaje mas bajo
	 * @param p
	 * @throws YaTieneTresPuntajesException
	 */
	public void agregarPuntaje(Puntaje p) throws YaTieneTresPuntajesException{
		int menor =  puntajes[puntajes.length-1].getPuntos();
		if(!yaEstaTresVeces(p.getUserName())){
			if(p.getPuntos()>menor)
				puntajes[puntajes.length-1]= p;
			ordenarPuntajes();
		}else{
			throw new YaTieneTresPuntajesException(p.getUserName());
		}

	}

	/**
	 * Ordena los puntajes en el arreglo mediante inserción segun sus puntos
	 */
	public void ordenarPuntajes(){
		for( int i = 1; i < puntajes.length; i++ ){
			for( int j = i; j > 0 && (puntajes[ j - 1 ].getPuntos() < puntajes[ j ].getPuntos()); j-- ){
				Puntaje temp = puntajes[ j ];
				puntajes[ j ] = puntajes[ j - 1 ];
				puntajes[ j - 1 ] = temp;
			}
		}
	}

	/**
	 * Ordena los puntajes en el arreglo mediante inserción segun el nombre de usuario
	 */
	public void ordenar(){
		for( int i = 1; i < puntajes.length; i++ ){
			for( int j = i; j > 0 && (puntajes[j-1].getUserName().compareTo(puntajes[j].getUserName()) < 0 ); j-- ){
				Puntaje temp = puntajes[ j ];
				puntajes[ j ] = puntajes[ j - 1 ];
				puntajes[ j - 1 ] = temp;
			}
		}
	}

	/**
	 * Busca un puntaje en el arreglo que tenga el nombre de usuario buscado
	 * @param user
	 * @return
	 */
	public Puntaje buscarPuntaje( String user ){
		boolean encontre = false;
		Puntaje buscado = null;
		int inicio = 0;
		int fin = puntajes.length - 1;
		ordenar();
		while( inicio <= fin && !encontre ){
			int medio = ( inicio + fin ) / 2;
			if( puntajes[ medio ].getUserName().compareTo(user) == 0){
				encontre = true;
				buscado = puntajes[medio];
			}
			else if( puntajes[ medio ].getUserName().compareTo(user) < 0 ){
				fin = medio - 1;
			}
			else{
				inicio = medio + 1;
			}
		}
		ordenarPuntajes();
		return buscado;
	}


	/**
	 * Retorna una cadena con la información de los puntajes
	 * @return
	 */
	public String darPuntajes(){
		String lista = "Posición      Puntaje     Usuario"+"\n";
		int contador = 1;
		for (int i = 0; i < puntajes.length; i++) {
			lista += "    "+contador+".                  "+puntajes[i].getPuntos()+"        "+puntajes[i].getUserName()+"\n";
			contador++;
		}
		return lista;
	}

}
