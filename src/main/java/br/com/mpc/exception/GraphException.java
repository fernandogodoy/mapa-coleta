package br.com.mpc.exception;

/**
 * 
 * @author Fernando
 *
 */
public class GraphException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public GraphException() {
		super("Ponto origem ou destino não localizado");
	}

}
