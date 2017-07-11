package br.com.mpc.prim.djkstra;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mpc.configuration.UnidadeConfig;
import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.Ponto;

/**
 * 
 * @author Fernando
 *
 */
@Component
public class Dijkstra {

	private List<Ponto> rotulados;
	private List<Ponto> naoRotulados;

	@Autowired
	private UnidadeConfig unidadeConfig;

	private Grafo grafo;
	private Ponto destino;
	private Ponto origem;

	public Dijkstra config(Grafo grafo) {
		this.grafo = grafo;
		this.origem = grafo.buscar(unidadeConfig.getUnidade("1"));
		this.destino =  grafo.buscar(unidadeConfig.getUnidade("2"));
		this.rotulados = new ArrayList<>();
		this.naoRotulados = new ArrayList<>();
		adicionarNaoRotulados(grafo);
		return this;
	}

	public Arvore run() {
		grafo.inicializar();
		grafo.atualizarPeso(origem, BigDecimal.ZERO);
		rotular(origem);

		while (!naoRotulados.isEmpty()) {
			List<Aresta> adjacentes = grafo.getAdjacentes(rotulados);
			grafo.atualizarPesoVertices(origem, adjacentes);
			Aresta arestaMenorCusto = selecionarMenorCusto(adjacentes);
			rotular(arestaMenorCusto.getTarget());
			origem = arestaMenorCusto.getTarget();

			if (origem.equals(destino))
				break;
		}

		return montarArvore(grafo, origem);
	}

	private Arvore montarArvore(Grafo grafo, Ponto destino) {
		Arvore arvore = new Arvore();
		List<Aresta> arestas = new ArrayList<>();
		
		while(destino.getVizinho() != null){
			arestas.add(grafo.getAresta(destino.getVizinho(), destino));
			destino = destino.getVizinho();
		}
		
		arestas.forEach(aresta -> arvore.add(aresta));
		return arvore;
	}

	/**
	 * Rotula o vertice selecionado
	 * 
	 * @param grafo
	 * 
	 * @param vertice
	 */
	private void rotular(Ponto vertice) {
		rotulados.add(vertice);
		naoRotulados.remove(vertice);
	}

	/**
	 * Seleciona o vertice não rotulado de menor custo
	 * 
	 * @param adjacentes
	 * @return
	 */
	private Aresta selecionarMenorCusto(List<Aresta> adjacentes) {
		Aresta selecionada = null;
		BigDecimal peso = BigDecimal.valueOf(Long.MAX_VALUE);
		for (Aresta aresta : adjacentes) {
			if (aresta.getTarget().getCost().compareTo(peso) == -1 && naoRotulados.contains(aresta.getTarget()) && !aresta.isFixa()) {
				selecionada = aresta;
				peso = aresta.getTarget().getCost();
			}
		}
		return selecionada;
	}

	public void adicionarNaoRotulados(Grafo grafo) {
		naoRotulados.addAll(grafo.getVertex());
	}

}
