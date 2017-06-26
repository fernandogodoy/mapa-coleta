package br.com.mpc.grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.mpc.model.app.Ponto;

/**
 * Representação de um Grafo
 * 
 * @author Fernando
 *
 */
public class Grafo {

	private Set<Ponto> vertices = new HashSet<>();
	private Set<Aresta> arestas = new HashSet<>();

	public void add(Aresta aresta) {
		this.arestas.add(aresta);
		this.vertices.add(aresta.getOrigem());
		this.vertices.add(aresta.getDestino());
	}

	public Grafo() {
		// Empty
	}

	public Grafo(Set<Aresta> arestas) {
		for (Aresta aresta : arestas) {
			this.add(aresta);
		}
	}

	@Override
	public String toString() {
		return "Arvore [" + arestas + "], size: " + arestas.size();
	}

	/**
	 * Seleciona um vertice de forma aleatória
	 * 
	 * @return
	 */
	public Ponto selecionarAleatorio() {
		List<Ponto> list = new ArrayList<>(vertices);
		Collections.shuffle(list);
		return list.get(new Random().nextInt(vertices.size() - 1));
	}

	/**
	 * Lista todos vertices adjacentes ao vertice origem
	 * 
	 * @param vertices
	 * @return Vertices adjacentes ordenados por peso
	 */
	public List<Aresta> getAdjacentes(Ponto origem) {
		return arestas.stream().filter(aresta -> aresta.getOrigem().equals(origem)).sorted()
				.collect(Collectors.toList());
	}

	public List<Ponto> getVertices() {
		return new ArrayList<>(vertices);
	}

	public Integer getQtdArestas() {
		return arestas.size();
	}

}
