package br.com.mpc.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SearchFormatterTest {

	@Test
	public void test() {
		String texto = "Unidade de Saude Alvorada I";
		String experado = "Maringa+PR+Unidade+de+Saude+Alvorada+I";
		assertEquals(experado, SearchFormatter.format(texto));
	}

}
