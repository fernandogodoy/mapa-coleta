package br.com.mpc.prim.djkstra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fggraph.response.ResponseBuilder;
import br.com.fggraph.response.ResultDTO;
import br.com.mpc.configuration.UnidadeConfig;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.Ponto.PontoBuilder;
import br.com.mpc.util.FileUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Dijkstra.class })
public class DijkstraTest {

	@Autowired
	private Dijkstra dikstra;

	@MockBean
	private UnidadeConfig config;

	private Grafo grafo;

	@Before
	public void setUp() {
		grafo = FileUtil.readDijkstraFile();
	}

	@Test
	public void shouldSuccess() {
		given(config.getUnidade("1")).willReturn(grafo.buscar(new PontoBuilder().withId(1l).build()));
		given(config.getUnidade("2")).willReturn(grafo.buscar(new PontoBuilder().withId(9l).build()));

		dikstra.config(grafo);
		Arvore arvore = dikstra.run();
		assertNotNull(arvore);
		assertEquals(4, arvore.getEdges().size());
		ResultDTO dto = new ResponseBuilder<>(arvore).withCost().withTextResult().build();
		assertEquals(24, dto.getCost().intValue());

		arvore = dikstra.run();
		assertNotNull(arvore);
		assertEquals(4, arvore.getEdges().size());
		dto = new ResponseBuilder<>(arvore).withCost().withTextResult().build();
		assertEquals(24, dto.getCost().intValue());

	}

}
