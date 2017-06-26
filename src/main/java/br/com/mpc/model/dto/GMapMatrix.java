package br.com.mpc.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Representação do retorno referente a consulta de coordenadas no Google Maps.
 * 
 * @author Fernando
 *
 */
public class GMapMatrix {

	@JsonProperty(value = "destination_addresses")
	private List<Point> destinationAdrresses;

	@JsonProperty(value = "origin_addresses")
	private List<Point> originAdrresses;

	private List<Row> rows;

	private Status status;

	public List<Point> getDestinationAdrresses() {
		return destinationAdrresses;
	}

	public void setDestinationAdrresses(List<Point> destinationAdrresses) {
		this.destinationAdrresses = destinationAdrresses;
	}

	public List<Point> getOriginAdrresses() {
		return originAdrresses;
	}

	public void setOriginAdrresses(List<Point> originAdrresses) {
		this.originAdrresses = originAdrresses;
	}

	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("destinationAdrresses", destinationAdrresses)
				.append("originAdrresses", originAdrresses).append("rows", rows).append("status", status).toString();
	}

	
}
