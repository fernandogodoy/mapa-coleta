package br.com.mpc.prim;

import java.math.BigDecimal;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.model.app.Ponto;

/**
 * 
 * @author Fernando
 *
 */
public class GraphHandler {

	private Arvore arvore;

	public GraphHandler(Arvore arvore) {
		this.arvore = arvore;
	}

	@Override
	public String toString() {
		Graph<Ponto, DefaultEdge> grafo = new DefaultDirectedGraph<>(DefaultEdge.class);

		for (Ponto vertice : arvore.getVertices()) {
			grafo.addVertex(vertice);
		}

		for (Aresta aresta : arvore.getArestas()) {
			grafo.addEdge(aresta.getOrigem(), aresta.getDestino());
		}

		return grafo.toString();
	}

	public BigDecimal getDistancia(Arvore arvore) {
		return arvore.getArestas().parallelStream()
				.map(aresta -> aresta.getDistancia()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}
	
	public BigDecimal getTempo(Arvore arvore) {
		return arvore.getArestas().parallelStream()
				.map(aresta -> aresta.getTempo()).reduce(BigDecimal.ZERO,
				BigDecimal::add);
	}

}
