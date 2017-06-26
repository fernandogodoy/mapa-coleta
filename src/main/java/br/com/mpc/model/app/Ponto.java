package br.com.mpc.model.app;

import java.util.Objects;

/**
 * Representação dos pontos de localização
 * 
 * @author Fernando
 *
 */
public class Ponto {

	private Long id;

	private String nome;

	private String endereco;

	private boolean isVisitado;
	
	/**
	 * Restrição para criação de instâncias
	 */
	private Ponto() {
	}

	public String getNome() {
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
		return id + " - "+ nome;
	}

	

}
