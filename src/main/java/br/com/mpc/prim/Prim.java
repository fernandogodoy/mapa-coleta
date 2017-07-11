package br.com.mpc.prim;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.Ponto;

@Component
public class Prim {
	
	private Grafo grafo;
	private Arvore arvore;
	private List<Aresta> adjacentes;
	
	public Prim config(Grafo grafo){
		this.grafo = grafo;
		arvore = new Arvore();
		adjacentes = new ArrayList<>();
		return this;
	}

	public Arvore run() {
		Ponto origem = grafo.selecionarAleatorio();
		Aresta aresta = selecionarAresta(origem);
		while (aresta != null) {
			arvore.add(aresta);
			origem = aresta.getTarget();
			aresta = selecionarAresta(origem);
		}
		return arvore;
	}

	private Aresta selecionarAresta(Ponto origem) {
		adjacentes.addAll(grafo.getAdjacentes(origem));
		Aresta aresta = findByMenorCusto(adjacentes);
		return aresta;
	}

	public Aresta findByMenorCusto(List<Aresta> arestas) {
		BigDecimal custo = new BigDecimal(Long.MAX_VALUE);
		Aresta selecionada = null;
		for (Aresta aresta : arestas) {
			if (custo.compareTo(aresta.getCost()) == 1 && !isVisitado(aresta)) {
				custo = aresta.getCost();
				selecionada = aresta;
			}
		}
		return selecionada;
	}

	private boolean isVisitado(Aresta aresta) {
		return arvore.isAdicionado(aresta.getTarget()) && arvore.isAdicionado(aresta.getOrigin());
	}
}
