package br.com.mpc.model.app;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author Fernando
 *
 */
public class GraphDTO {

	private String graph;

	private BigDecimal distancia;

	private BigDecimal tempoEstimado;

	public GraphDTO(String graph, BigDecimal distancia, BigDecimal tempoEstimado) {
		this.graph = graph;
		this.distancia = distancia;
		this.tempoEstimado = tempoEstimado;
	}

	public String getGraph() {
		return graph;
	}

	public String getDistancia() {
		return distancia.divide(new BigDecimal("1000"), RoundingMode.UP) + " Km";
	}
	
	public String getTempoEstimado() {
		return tempoEstimado.divide(new BigDecimal("60"), RoundingMode.UP) + " Minutos";
	}

}
