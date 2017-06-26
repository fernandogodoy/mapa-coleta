package br.com.mpc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mpc.grafo.Arvore;
import br.com.mpc.model.app.GraphDTO;
import br.com.mpc.prim.GraphHandler;
import br.com.mpc.prim.GraphJFrameHandler;
import br.com.mpc.prim.Prim;
import br.com.mpc.service.MapaColetaService;

@RestController
@RequestMapping("/mapa-coleta")
public class MapaColetaController {

	@Autowired
	private MapaColetaService service;

	@ResponseBody
	@RequestMapping(path = "/notificar/{idDestino}", method = RequestMethod.GET)
	public ResponseEntity<String> receberNotificacao(@PathVariable String idDestino) {
		service.recept(idDestino);
		return new ResponseEntity<>("Notificação Processada!", HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(path = "/arvore-geradora-minima", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<GraphDTO> montarArvoreGeradoraMinina() {
		Prim prim = new Prim(service.getArestas());
		Arvore arvore = prim.execute();

		GraphHandler handler = new GraphHandler(arvore);
		GraphDTO dto = new GraphDTO(handler.toString(), handler.getDistancia(arvore), handler.getTempo(arvore));
		
		gerarGrafico(arvore);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	private void gerarGrafico(Arvore arvore) {
		Runnable jFramePrint = () -> {
			GraphJFrameHandler jHandler = new GraphJFrameHandler(arvore);
			jHandler.print();
		};
		new Thread(jFramePrint).start();
	}
}
