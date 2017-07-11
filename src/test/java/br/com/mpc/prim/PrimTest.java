package br.com.mpc.prim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fggraph.response.ResponseBuilder;
import br.com.fggraph.response.ResultDTO;
import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.GraphDTO;
import br.com.mpc.model.app.Ponto;
import br.com.mpc.model.app.Ponto.PontoBuilder;
import br.com.mpc.util.FileUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Prim.class })
public class PrimTest {

	@Autowired
	private Prim prim;

	private Grafo grafo;

	@Before
	public void setUp() {
		grafo = FileUtil.readPrimFile();
	}
	
	@Test
	public void shouldMinCostVertex() {
		prim.config(grafo);
		Ponto ponto = grafo.buscar(new PontoBuilder().withId(3L).build());
		Aresta aresta = prim.findByMenorCusto(grafo.getAdjacentes(ponto));
		assertEquals(Long.valueOf(6), aresta.getTarget().getId());
		assertEquals(Long.valueOf(3), aresta.getOrigin().getId());
		assertEquals(BigDecimal.ONE, aresta.getCost());
	}

	@Test
	public void shouldMinimalTree() {
		Arvore arvore = prim.config(grafo).run();
		ResultDTO dto = new ResponseBuilder<>(arvore).withCost().withTextResult().build();
		assertEquals(12, dto.getCost().intValue());
	}
	
	@Test
	public void shouldFomated() {
		Arvore arvore = prim.config(grafo).run();
		ResultDTO dto = new ResponseBuilder<>(arvore).withCost().withTextResult().build();
		GraphDTO graphDTO = new GraphDTO(dto, BigDecimal.ZERO);
		assertNotNull(graphDTO);
	}

}
