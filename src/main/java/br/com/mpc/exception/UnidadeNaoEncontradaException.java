package br.com.mpc.exception;

/**
 * 
 * @author Fernando
 *
 */
public class UnidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnidadeNaoEncontradaException(String idUnidade) {
		super("Unidade de Saúde "+ idUnidade + " não encontrada");
	}

}
