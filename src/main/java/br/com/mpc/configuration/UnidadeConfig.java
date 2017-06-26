package br.com.mpc.configuration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import br.com.mpc.exception.UnidadeNaoEncontradaException;
import br.com.mpc.model.app.Ponto;
import br.com.mpc.model.app.Ponto.PontoBuilder;

@Configuration
@PropertySource("classpath:unidades.properties")
public class UnidadeConfig {

	private static final Logger LOG = Logger.getLogger(UnidadeConfig.class);

	@Autowired
	private Environment env;

	public Ponto getUnidade(String idUnidade) {
		LOG.info("Buscando unidade de saúde contendo ID " + idUnidade);

		String nomeUnidade = env.getProperty(idUnidade);
		if (StringUtils.isNotBlank(nomeUnidade)) {
			return new PontoBuilder()
							.withId(Long.valueOf(idUnidade))
							.withNome(nomeUnidade)
							.build();
		}

		throw new UnidadeNaoEncontradaException(idUnidade);
	}

}
