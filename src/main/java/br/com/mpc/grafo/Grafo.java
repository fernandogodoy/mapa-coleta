package br.com.mpc.grafo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
		this.vertices.add(aresta.getOrigin());
		this.vertices.add(aresta.getTarget());
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
	public List<Aresta> getAdjacentes(Ponto ponto) {
		return getAdjacentes(Arrays.asList(ponto));
	}

	/**
	 * Lista todos vertices adjacentes dos vertices já rotulados
	 * 
	 * @param vertices
	 * @return Vertices adjacentes ordenados por peso
	 */
	public List<Aresta> getAdjacentes(List<Ponto> rotulados) {
		List<Aresta> retorno = new ArrayList<>();
		for (Ponto vertice : rotulados) {
			List<Aresta> list = arestas.stream()
					.filter(aresta -> aresta.getOrigin().equals(vertice))
					.sorted()
					.collect(Collectors.toList());
			retorno.addAll(list);
		}
		return retorno;
	}

	/**
	 * Inicializa peso dos vertices com um valor relativamente alto.
	 * 
	 */
	public void inicializar() {
		arestas.forEach(aresta -> {
			aresta.getOrigin().atribuirPesoInicial();
			aresta.getTarget().atribuirPesoInicial();
		});
	}

	public List<Ponto> getVertex() {
		return new ArrayList<>(vertices);
	}
	
	public List<Aresta> getArestas() {
		return new ArrayList<>(arestas);
	}

	public void atualizarPeso(Ponto origem, BigDecimal peso) {
		arestas.stream()
			.filter(aresta -> aresta.getOrigin().equals(origem)) 
			.forEach(aresta -> {
			aresta.getOrigin().atualizarPeso(peso);
		});
	}

	public void atualizarPesoVertices(Ponto origem, List<Aresta> adjacentes) {
		adjacentes.forEach(aresta -> {
			BigDecimal pesoAtual = origem.getCost().add(aresta.getCost());
			Ponto destino = aresta.getTarget();
			if (destino.getCost().compareTo(pesoAtual) == 1) {
				aresta.getTarget().atualizarReferencia(origem, pesoAtual);
				atualizarPeso(aresta.getOrigin(), origem.getCost());
			}
		});
	}
	
	public Ponto buscar(Ponto origin) {
		return getVertex().get(getVertex().indexOf(origin));
	}

	public Aresta getAresta(Ponto origem, Ponto destino) {
		return arestas.stream()
				.filter(aresta -> aresta.getOrigin().equals(origem))
				.filter(aresta -> aresta.getTarget().equals(destino))
				.findFirst()
				.get();
	}
}
