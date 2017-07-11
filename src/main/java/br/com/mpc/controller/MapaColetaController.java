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

import br.com.fggraph.response.ResponseBuilder;
import br.com.fggraph.response.View;
import br.com.mpc.grafo.Arvore;
import br.com.mpc.model.app.GraphDTO;
import br.com.mpc.model.app.Ponto;
import br.com.mpc.prim.Prim;
import br.com.mpc.prim.djkstra.Dijkstra;
import br.com.mpc.service.MapaColetaService;

@RestController
@RequestMapping("/mapa-coleta")
public class MapaColetaController {

	@Autowired
	private MapaColetaService service;

	@Autowired
	private Prim prim;

	@Autowired
	private Dijkstra dijkstra;

	@ResponseBody
	@RequestMapping(path = "/notificar/{idDestino}", method = RequestMethod.GET)
	public ResponseEntity<String> receberNotificacao(@PathVariable String idDestino) {
		Ponto ponto = service.recept(idDestino);
		if (ponto == null) {
			return new ResponseEntity<>("Notificação já processada anteriormente", HttpStatus.OK);
		}
		gerarGrafico(service.getArvore(), "Grafo");
		return new ResponseEntity<>("Novo ponto incluído: " + ponto.toNotificationInfo(), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(path = "/arvore-geradora-minima", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<GraphDTO> gerarArvore() {
		Arvore arvore = prim.config(service.getGrafo()).run();
		ResponseBuilder<Arvore> builder = new ResponseBuilder<>(arvore).withTextResult().withCost();
		gerarGrafico(arvore, "Árvore Geradora Mínima - PRIM");
		return new ResponseEntity<>(new GraphDTO(builder.build(), arvore.getTempo()), HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(path = "/caminho-minimo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<GraphDTO> gerarCaminho() {
		Arvore arvore = dijkstra.config(service.getGrafo()).run();
		ResponseBuilder<Arvore> builder = new ResponseBuilder<>(arvore).withTextResult().withCost();
		gerarGrafico(arvore, "Caminho Mínimo - DIJKSTRA");
		return new ResponseEntity<>(new GraphDTO(builder.build(), arvore.getTempo()), HttpStatus.OK);
	}

	private void gerarGrafico(Arvore arvore, String name) {
		Runnable jFramePrint = () -> {
			ResponseBuilder<Arvore> builder = new ResponseBuilder<>(arvore);
			View view = builder.withGraphicResult().build().getView();
			view.name(name).open();
		};
		new Thread(jFramePrint).start();
	}
}
