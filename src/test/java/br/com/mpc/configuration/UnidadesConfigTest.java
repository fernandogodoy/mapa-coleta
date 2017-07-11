package br.com.mpc.configuration;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.mpc.exception.UnidadeNaoEncontradaException;
import br.com.mpc.model.app.Ponto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { UnidadeConfig.class })
public class UnidadesConfigTest {

	@Autowired
	private UnidadeConfig appConfig;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shouldSuccessPropertFile() {
		Ponto ponto = appConfig.getUnidade("2");
		Assert.assertEquals("CASA DO PSF PAULINO (UBS Pinheiros)", ponto.getName());
	}

	@Test
	public void shouldFailReadPropertFile() {
		expectedException.expect(UnidadeNaoEncontradaException.class);
		appConfig.getUnidade("50");
	}

}
