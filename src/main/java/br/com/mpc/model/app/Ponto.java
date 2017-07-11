package br.com.mpc.model.app;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.fggraph.model.Vertex;

/**
 * Representação dos pontos de localização
 * 
 * @author Fernando
 *
 */
public class Ponto implements Vertex {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String endereco;

	private BigDecimal cost;

	private boolean isVisitado;
	
	private Ponto vizinho;

	/**
	 * Restrição para criação de instâncias
	 */
	private Ponto() {
	}

	public String getName() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public Long getId() {
		return id;
	}

	public void visitado() {
		this.isVisitado = true;
	}

	public boolean isVisitado() {
		return isVisitado;
	}

	public BigDecimal getCost() {
		return cost;
	}
	
	public Ponto getVizinho() {
		return vizinho;
	}

	public String toNotificationInfo() {
		return id + " - " + nome;
	}

	public static class PontoBuilder {

		private Ponto ponto;

		public PontoBuilder() {
			this.ponto = new Ponto();
		}

		public PontoBuilder(Ponto ponto) {
			this.ponto = ponto;
		}

		public PontoBuilder withId(Long id) {
			this.ponto.id = id;
			return this;
		}

		public PontoBuilder withNome(String nome) {
			this.ponto.nome = nome;
			return this;
		}

		public PontoBuilder withEndereco(String endereco) {
			this.ponto.endereco = endereco;
			return this;
		}

		public Ponto build() {
			return ponto;
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Ponto)) {
			return false;
		}
		Ponto castOther = (Ponto) other;
		return Objects.equals(id, castOther.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return id.toString();
	}

	/**
	 * Atribui {@link Integer#MAX_VALUE} para cada vertice
	 * 
	 */
	public void atribuirPesoInicial() {
		atualizarPeso(BigDecimal.valueOf(Integer.MAX_VALUE));
	}

	/**
	 * Atualizar valor do peso
	 * 
	 * @param peso
	 */
	public void atualizarPeso(BigDecimal cost) {
		this.cost = cost;
	}

	public void atualizarReferencia(Ponto vertice, BigDecimal peso){
		atualizarPeso(peso);
		this.vizinho = vertice;
	}

}
