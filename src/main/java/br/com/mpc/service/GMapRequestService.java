package br.com.mpc.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mpc.exception.GMapRequestException;
import br.com.mpc.model.dto.GMapMatrix;
import br.com.mpc.rpc.GmapHttp;

/**
 * Serviço para execução de consultas na API do Google Maps
 * 
 * @author Fernando
 *
 */
@Service
public class GMapRequestService {

	private static final String URI = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=%s&destinations=%s&mode=driving&language=pt-BR";
	private static final Logger LOG = Logger.getLogger(GMapRequestService.class);

	@Autowired
	private GmapHttp gMapHttp;

	public GMapMatrix findLocation(String origin, String destination) {

		String requestURI = String.format(URI, origin, destination);
		LOG.info("URI: " + requestURI);

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			return mapper.readValue(gMapHttp.get(requestURI), GMapMatrix.class);
		} catch (IOException e) {
			LOG.error("Erro durante durante consulta", e);
			throw new GMapRequestException();
		}
	}
}
