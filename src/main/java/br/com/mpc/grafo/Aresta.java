package br.com.mpc.grafo;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.fggraph.model.Edge;
import br.com.mpc.model.app.Ponto;

public class Aresta implements Edge {

	private static final long serialVersionUID = 1L;

	private Ponto origem;

	private Ponto destino;

	private BigDecimal distancia;

	private BigDecimal tempo;
	
	private boolean isFixa;
	
	public Aresta(Ponto origem, Ponto destino, Long distancia) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = BigDecimal.valueOf(distancia);
		this.tempo = BigDecimal.ZERO;
	}

	public Aresta(Ponto origem, Ponto destino, Long distancia, Long tempo) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = BigDecimal.valueOf(distancia);
		this.tempo = BigDecimal.valueOf(tempo);
	}
	
	public Aresta(Ponto origem, Ponto destino, BigDecimal distancia, BigDecimal tempo) {
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
		this.tempo = distancia;
	}

	public Ponto getOrigin() {
		return origem;
	}

	public Ponto getTarget() {
		return destino;
	}

	public BigDecimal getCost() {
		return distancia;
	}

	public BigDecimal getTempo() {
		return tempo;
	}
	
	public boolean isFixa(){
		return isFixa;
	}
	
	public void fixar(){
		this.isFixa = true;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Aresta)) {
			return false;
		}
		Aresta castOther = (Aresta) other;
		return Objects.equals(origem, castOther.origem) && Objects.equals(destino, castOther.destino)
				&& Objects.equals(distancia, castOther.distancia) && Objects.equals(tempo, castOther.tempo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(origem, destino, distancia, tempo);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("origem", origem.getName())
				.append("destino", destino.getName())
				.append("distancia", distancia)
				.append("tempo", tempo).toString();
	}

}
