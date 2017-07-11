package br.com.mpc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.mpc.configuration.UnidadeConfig;
import br.com.mpc.exception.GMapMatrixValidationException;
import br.com.mpc.exception.GMapRequestException;
import br.com.mpc.exception.UnidadeNaoEncontradaException;
import br.com.mpc.grafo.Aresta;
import br.com.mpc.rpc.GmapHttp;
import br.com.mpc.validator.GMapMatrixValidator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GService.class, UnidadeConfig.class, GMapRequestService.class, GMapMatrixValidator.class })
public class GServiceTest {

	@Autowired
	private GService service;

	@MockBean
	private GmapHttp gMapHttp;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void sholdResturnSuccess() throws IOException {

		montaRequestSuccess("responses/exemplo-retorno.json");
		Aresta aresta = service.addAresta("1", "2");
		assertNotNull(aresta);
		assertEquals(Long.valueOf(1), aresta.getOrigin().getId());
		assertEquals(Long.valueOf(2), aresta.getTarget().getId());
		assertEquals(BigDecimal.valueOf(2770), aresta.getCost());
		assertEquals(BigDecimal.valueOf(390), aresta.getTempo());
	}

	@Test
	public void sholdReturnUnidadeNaoEncontradaException() throws IOException {

		expectedException.expect(UnidadeNaoEncontradaException.class);

		montaRequestSuccess("responses/exemplo-retorno.json");
		service.addAresta("1", "100");
	}

	@Test
	public void sholdReturn() throws IOException {

		expectedException.expect(GMapMatrixValidationException.class);
		montaRequestSuccess("responses/exemplo-retorno-invalid.json");
		service.addAresta("1", "2");
	}

	@Test
	public void sholdReturnRequestException() throws IOException {

		expectedException.expect(GMapRequestException.class);
		montaRequestError();
		service.addAresta("1", "2");
	}

	private void montaRequestSuccess(String jsonName) throws IOException {
		URL resource = ClassLoader.getSystemResource(jsonName);
		given(gMapHttp.get(Mockito.anyString())).willReturn(resource.openStream());
	}

	private void montaRequestError() throws IOException {
		given(gMapHttp.get(Mockito.anyString())).willReturn(null);
	}

}
