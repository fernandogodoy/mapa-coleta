package br.com.mpc.grafo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.mpc.model.app.Ponto;
import br.com.mpc.model.app.Ponto.PontoBuilder;
import br.com.mpc.util.FileUtil;

public class GrafoTest {

	@Test
	public void sholdVertexLoaded() {
		Grafo grafo = FileUtil.readPrimFile();
		assertNotNull(grafo);
		assertEquals(6, grafo.getVertex().size());
		
		grafo = FileUtil.readDijkstraFile();
		assertNotNull(grafo);
		assertEquals(9, grafo.getVertex().size());
	}

	@Test
	public void sholdReturnVertex() {
		Grafo grafo = FileUtil.readPrimFile();
		Ponto experado = new PontoBuilder().withId(5l).build();
		Ponto resultado = grafo.buscar(experado);
		assertNotNull(resultado);
		assertEquals(experado.getId(), resultado.getId());
		
		grafo = FileUtil.readDijkstraFile();
		experado = new PontoBuilder().withId(5l).build();
		resultado = grafo.buscar(experado);
		assertNotNull(resultado);
		assertEquals(experado.getId(), resultado.getId());
	}

	@Test
	public void sholdVertexAdjacent() {
		Grafo grafo = FileUtil.readPrimFile();
		Ponto ponto = grafo.buscar(new PontoBuilder().withId(5l).build());
		List<Aresta> adjacentes = grafo.getAdjacentes(ponto);
		assertFalse(adjacentes.isEmpty());
		assertEquals(3, adjacentes.size());
		
		grafo = FileUtil.readDijkstraFile();
		ponto = grafo.buscar(new PontoBuilder().withId(5l).build());
		adjacentes = grafo.getAdjacentes(Arrays.asList(ponto));
		assertFalse(adjacentes.isEmpty());
		assertEquals(4, adjacentes.size());
	}

}
