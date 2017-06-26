package br.com.mpc.model.app;

/**
 * Representação do Arquivo de mapeamento de unidades
 * 
 * @author Fernando
 *
 */
public class UnidadeSaude {

	private Long id;

	private String nome;

	public UnidadeSaude(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

}
