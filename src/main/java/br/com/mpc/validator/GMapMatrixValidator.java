package br.com.mpc.validator;

import org.springframework.stereotype.Component;

import br.com.mpc.exception.GMapMatrixValidationException;
import br.com.mpc.model.dto.GMapMatrix;
import br.com.mpc.model.dto.Status;

@Component
public class GMapMatrixValidator {

	private static final int FIRST = 0;

	public void validar(GMapMatrix gMapMatrix) {
		isReturnOk(gMapMatrix);
	}

	private void isReturnOk(GMapMatrix gMapMatrix) {
		if (!Status.OK.equals(gMapMatrix.getStatus())) {
			throw new GMapMatrixValidationException("Status do processamento inválido");
		}

		if (gMapMatrix.getDestinationAdrresses().isEmpty()) {
			throw new GMapMatrixValidationException("Destino não encontrado");
		}

		if (gMapMatrix.getOriginAdrresses().isEmpty()) {
			throw new GMapMatrixValidationException("Origem não encontrado");
		}

		if (gMapMatrix.getRows().isEmpty()) {
			throw new GMapMatrixValidationException("Consulta não retorno resultado");
		}

		if (gMapMatrix.getRows().get(FIRST).getElements().isEmpty()) {
			throw new GMapMatrixValidationException("Consulta não retorno elementos");
		}

		if (!Status.OK.equals(gMapMatrix.getRows().get(FIRST).getElements().get(FIRST).getStatus())) {
			throw new GMapMatrixValidationException("Status de resultados inválido!");
		}

		if (gMapMatrix.getRows().get(FIRST).getElements().get(FIRST).getDistance().isEmpty()) {
			throw new GMapMatrixValidationException("Distância não calculada");
		}

		if (gMapMatrix.getRows().get(FIRST).getElements().get(FIRST).getDuration().isEmpty()) {
			throw new GMapMatrixValidationException("Duração não calculada");
		}
	}

}
