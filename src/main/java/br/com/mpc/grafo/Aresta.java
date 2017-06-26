package br.com.mpc.grafo;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.com.mpc.model.app.Ponto;

public class Aresta implements Comparable<Aresta> {

	private Ponto origem;

	private Ponto destino;

	private BigDecimal distancia;

	private BigDecimal tempo;

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

	public Ponto getOrigem() {
		return origem;
	}

	public Ponto getDestino() {
		return destino;
	}

	public BigDecimal getDistancia() {
		return distancia;
	}

	public BigDecimal getTempo() {
		return tempo;
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
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).append("origem", origem.getNome()).append("destino", destino.getNome())
				.append("distancia", distancia).append("tempo", tempo).toString();
	}

	@Override
	public int compareTo(Aresta o) {
		return this.getDistancia().compareTo(o.getDistancia());
	}

}
