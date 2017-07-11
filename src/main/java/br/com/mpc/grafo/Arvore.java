package br.com.mpc.grafo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.fggraph.model.Tree;
import br.com.mpc.model.app.Ponto;

/**
 * Representação da Árvore
 * 
 * @author Fernando
 *
 */
public class Arvore implements Tree<Aresta, Ponto> {

	private static final long serialVersionUID = 1L;

	private List<Aresta> arestas = new ArrayList<>();

	private List<Ponto> vertices = new ArrayList<>();

	public void add(Aresta aresta) {
		this.arestas.add(aresta);
		this.vertices.add(aresta.getOrigin());
		this.vertices.add(aresta.getTarget());
	}

	public boolean isAdicionado(Ponto destino) {
		return this.getVertex().contains(destino);
	}

	public final List<Ponto> getVertex() {
		return vertices;
	}

	public final List<Aresta> getEdges() {
		return arestas;
	}
	
	public BigDecimal getTempo() {
		return getEdges().parallelStream()
					.map(aresta -> aresta.getTempo())
					.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
