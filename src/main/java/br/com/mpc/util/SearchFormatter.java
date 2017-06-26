package br.com.mpc.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilitário para formatar o nome das unidades em um formato de consulta do
 * google
 * 
 * @author Fernando
 *
 */
public class SearchFormatter {

	private static final String PLUS = "+";
	private static final String PREFIX = "Maringá+PR+";

	/**
	 * Substitui os espaços da palavra pelo caracter +
	 * 
	 * @param nome
	 * @return
	 */
	public static final String format(String nome) {
		return StringUtils.join(PREFIX, StringUtils.replaceAll(nome, StringUtils.SPACE, PLUS));
	}

}
