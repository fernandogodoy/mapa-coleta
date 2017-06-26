package br.com.mpc.prim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.Ponto;

public class Prim {
	
	private Grafo grafo;
	private Arvore arvore;
	private List<Aresta> adjacentes;
	
	public Prim(Set<Aresta> arestas) {
		arvore = new Arvore();
		adjacentes = new ArrayList<>();
		grafo = new Grafo(arestas);
	}

	public Arvore execute() {
		Ponto origem = grafo.selecionarAleatorio();
		Aresta aresta = selecionarAresta(grafo, origem);

		while (aresta != null) {
			arvore.add(aresta);
			origem = aresta.getDestino();
			aresta = selecionarAresta(grafo, origem);
		}
		
		return arvore;
	}

	private Aresta selecionarAresta(Grafo grafo, Ponto origem) {
		adjacentes.addAll(grafo.getAdjacentes(origem));
		Aresta aresta = findByMenorCusto(adjacentes);
		return aresta;
	}

	private Aresta findByMenorCusto(List<Aresta> arestas) {
		BigDecimal custo = new BigDecimal(Long.MAX_VALUE);
		Aresta selecionada = null;
		for (Aresta aresta : arestas) {
			if (custo.compareTo(aresta.getDistancia()) == 1 && !isVisitado(aresta)) {
				custo = aresta.getDistancia();
				selecionada = aresta;
			}
		}
		return selecionada;
	}

	private boolean isVisitado(Aresta aresta) {
		return arvore.isAdicionado(aresta.getDestino()) && arvore.isAdicionado(aresta.getOrigem());
	}
}
