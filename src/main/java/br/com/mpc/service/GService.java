package br.com.mpc.service;

import static br.com.mpc.util.SearchFormatter.format;
import static br.com.mpc.util.SearchFormatter.formatNoPrefox;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mpc.configuration.UnidadeConfig;
import br.com.mpc.exception.GMapMatrixValidationException;
import br.com.mpc.grafo.Aresta;
import br.com.mpc.model.app.Ponto;
import br.com.mpc.model.app.Ponto.PontoBuilder;
import br.com.mpc.model.dto.GMapMatrix;
import br.com.mpc.validator.GMapMatrixValidator;

@Service
public class GService {

	private static final int FIRST = 0;

	private static final Logger LOG = Logger.getLogger(GService.class);

	@Autowired
	private GMapRequestService gMapRequest;

	@Autowired
	private GMapMatrixValidator validator;

	@Autowired
	private UnidadeConfig unidadeConfig;

	public Aresta addInical() {
		return addAresta("1", "2");
	}

	public Aresta addAresta(String idOrigem, String idDestino) {
		try {
			Ponto origem = unidadeConfig.getUnidade(idOrigem);
			Ponto destino = unidadeConfig.getUnidade(idDestino);

			LOG.info("Origem= " + origem.getName() + ", Destino= " + destino.getName());

			GMapMatrix gMapMatrix = gMapRequest.findLocation(formataNome(origem), formataNome(destino));
			validator.validar(gMapMatrix);
			return criarAresta(origem, destino, gMapMatrix);
		} catch (GMapMatrixValidationException e) {
			throw e;
		}
	}

	private String formataNome(Ponto ponto) {
		if (ponto.getId().equals(2l)) {
			return formatNoPrefox(ponto.getName());
		}
		return format(ponto.getName());
	}

	private Aresta criarAresta(Ponto origem, Ponto destino, GMapMatrix gMapMatrix) {
		origem = updateOrigem(origem, gMapMatrix);
		destino = updateDestino(destino, gMapMatrix);
		Long distancia = getDistancia(gMapMatrix);
		Long tempo = getTempo(gMapMatrix);
		return new Aresta(origem, destino, distancia, tempo);
	}

	private Long getTempo(GMapMatrix gMapMatrix) {
		return gMapMatrix.getRows().get(FIRST).getElements().get(FIRST).getDuration().get(FIRST).getValue();
	}

	private Long getDistancia(GMapMatrix gMapMatrix) {
		return gMapMatrix.getRows().get(FIRST).getElements().get(FIRST).getDistance().get(FIRST).getValue();
	}

	private Ponto updateDestino(Ponto origem, GMapMatrix gMapMatrix) {
		return new PontoBuilder(origem).withEndereco(gMapMatrix.getDestinationAdrresses().get(FIRST).getAddress())
				.build();
	}

	private Ponto updateOrigem(Ponto origem, GMapMatrix gMapMatrix) {
		return new PontoBuilder(origem).withEndereco(gMapMatrix.getOriginAdrresses().get(FIRST).getAddress()).build();
	}
}
