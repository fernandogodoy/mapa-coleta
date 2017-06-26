package br.com.mpc.grafo;

import java.util.ArrayList;
import java.util.List;

import br.com.mpc.model.app.Ponto;

/**
 * Representação da Árvore
 * 
 * @author Fernando
 *
 */
public class Arvore {

	private List<Aresta> arestas = new ArrayList<>();

	private List<Ponto> vertices = new ArrayList<>();

	public void add(Aresta aresta) {
		this.arestas.add(aresta);
		this.vertices.add(aresta.getOrigem());
		this.vertices.add(aresta.getDestino());
	}

	public boolean isAdicionado(Ponto destino) {
		return this.getVertices().contains(destino);
	}

	public final List<Ponto> getVertices() {
		return vertices;
	}

	public final List<Aresta> getArestas() {
		return arestas;
	}

}
