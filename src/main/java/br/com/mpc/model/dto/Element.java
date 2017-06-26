package br.com.mpc.model.dto;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Representação dos elementos contidos no retorno da consulta
 * 
 * @author Fernando
 *
 */
public class Element {

	private List<Detail> distance;

	private List<Detail> duration;

	private Status status;

	public List<Detail> getDistance() {
		return distance;
	}

	public List<Detail> getDuration() {
		return duration;
	}

	public Status getStatus() {
		return status;
	}

	public void setDistance(List<Detail> distance) {
		this.distance = distance;
	}

	public void setDuration(List<Detail> duration) {
		this.duration = duration;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("distance", distance)
				.append("duration", duration).append("status", status).toString();
	}
	
}
