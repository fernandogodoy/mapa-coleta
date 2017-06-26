package br.com.mpc.exception;

/**
 * 
 * @author Fernando
 *
 */
public class GMapRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GMapRequestException() {
		super("Invalid Request or Respose");
	}
}
