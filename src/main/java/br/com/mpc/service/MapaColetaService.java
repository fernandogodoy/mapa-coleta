package br.com.mpc.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mpc.configuration.UnidadeConfig;
import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.Ponto;

@Service
public class MapaColetaService {

	private static final Logger LOG = Logger.getLogger(MapaColetaService.class);

	@Autowired
	private GService service;
	
	@Autowired
	private UnidadeConfig unidadeConfig;

	private Set<Aresta> arestas = new HashSet<>();

	public Ponto recept(String idDestino) {

		Optional<Aresta> optional = arestas.parallelStream()
				.filter(aresta -> aresta.getTarget().getId().toString().equals(idDestino)).findFirst();

		if (!optional.isPresent()) {
			buscarNovaAresta(idDestino);
			return unidadeConfig.getUnidade(idDestino);

		}
		LOG.info("Arestas já mapeada");
		return null;
	}

	private void buscarNovaAresta(String idDestino) {
		if (arestas.isEmpty()) {
			inicializar();
		}
		
		carregarArestas(idDestino);
		LOG.info("Quantidade atual de arestas = " + arestas.size());
		LOG.info("Arestas =" + arestas);
	}

	private void inicializar() {
		Aresta aresta = service.addInical();
		aresta.fixar();
		arestas.add(aresta);
		arestas.add(gerarEspelhoAresta(aresta));
	}

	private void carregarArestas(String destino) {
		Set<Aresta> novas = new HashSet<>();
		Set<Aresta> existentes = new HashSet<>(arestas);
		for (Aresta aresta : existentes) {
			Aresta novaAresta = service.addAresta(aresta.getOrigin().getId().toString(), destino);
			novas.add(novaAresta);
			arestas.add(gerarEspelhoAresta(novaAresta));
		}
		arestas.addAll(novas);
	}

	private Aresta gerarEspelhoAresta(Aresta aresta) {
		return new Aresta(aresta.getTarget(), aresta.getOrigin(), aresta.getCost(), aresta.getTempo());
	}
	
	public Set<Aresta> getArestas() {
		return arestas;
	}
	
	public Grafo getGrafo() {
		return new Grafo(arestas);
	}
	
	public Arvore getArvore() {
		Arvore arvore = new Arvore();
		arestas.forEach(arvore::add);
		return arvore;
	}

	public void removeSelecionados(List<Aresta> edges) {
		this.arestas.removeIf(aresta -> !aresta.isFixa());
	}
}
