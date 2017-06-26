package br.com.mpc.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mpc.grafo.Aresta;

@Service
public class MapaColetaService {

	private static final Logger LOG = Logger.getLogger(MapaColetaService.class);

	@Autowired
	private GService service;

	private Set<Aresta> arestas = new HashSet<>();

	public void recept(String idDestino) {

		Optional<Aresta> optional = arestas.parallelStream()
				.filter(aresta -> aresta.getDestino().getId().toString().equals(idDestino)).findFirst();

		if (!optional.isPresent()) {
			buscarNovaAresta(idDestino);
		} else {
			LOG.info("Arestas já mapeada");
		}
	}

	private void buscarNovaAresta(String idDestino) {
		if (arestas.isEmpty()) {
			Aresta aresta = service.addAresta("1", idDestino);
			arestas.add(aresta);
			arestas.add(gerarEspelhoAresta(aresta));
		} else {
			carregarArestas(idDestino);
		}
		LOG.info("Quantidade atual de arestas = " + arestas.size());
		LOG.info("Arestas =" + arestas);
	}

	private void carregarArestas(String destino) {
		Set<Aresta> novas = new HashSet<>();
		Set<Aresta> existentes = new HashSet<>(arestas);
		for (Aresta aresta : existentes) {
			Aresta novaAresta = service.addAresta(aresta.getOrigem().getId().toString(), destino);
			novas.add(novaAresta);
			arestas.add(gerarEspelhoAresta(novaAresta));
		}
		arestas.addAll(novas);
	}

	private Aresta gerarEspelhoAresta(Aresta aresta) {
		return new Aresta(aresta.getDestino(), aresta.getOrigem(), aresta.getDistancia(), aresta.getTempo());
	}
	
	public Set<Aresta> getArestas() {
		return arestas;
	}
}
