package br.com.mpc.model.app;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.StringUtils;

import br.com.fggraph.response.ResultDTO;

/**
 * 
 * @author Fernando
 *
 */
public class GraphDTO {

	private String arestas;

	private String caminho;

	private BigDecimal distancia;

	private BigDecimal tempoEstimado;

	public GraphDTO(ResultDTO result, BigDecimal tempo) {
		String[] splited = result.getResult().split("],");
		this.arestas = cleanText(splited[1].substring(1, splited[1].length() - 1));
		this.caminho = cleanText(splited[0]).replace("(", StringUtils.EMPTY);
		this.distancia = result.getCost();
		this.tempoEstimado = tempo;
	}

	private String cleanText(String texto) {
		return texto.replace("[", StringUtils.EMPTY).replaceAll("]", StringUtils.EMPTY);
	}

	public String getArestas() {
		return arestas;
	}

	public String getCaminho() {
		return caminho;
	}

	public String getDistancia() {
		return distancia.divide(new BigDecimal("1000"), RoundingMode.UP) + " Km";
	}

	public String getTempoEstimado() {
		return tempoEstimado.divide(new BigDecimal("60"), RoundingMode.UP) + " Minutos";
	}

}
