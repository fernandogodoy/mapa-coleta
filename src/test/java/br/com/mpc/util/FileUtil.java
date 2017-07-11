package br.com.mpc.util;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.mpc.grafo.Aresta;
import br.com.mpc.grafo.Grafo;
import br.com.mpc.model.app.Ponto;
import br.com.mpc.model.app.Ponto.PontoBuilder;

public class FileUtil {

	private static final String EMPTY = "";
	private static final Pattern REGEX = Pattern.compile("(.*) -> (.*)", Pattern.DOTALL);
	private static final Pattern REGEX_VERTEX = Pattern.compile("(.*)-(.*)", Pattern.UNIX_LINES);

	public static Grafo readPrimFile() {
		Path path = getPath("listaAdjacenciaPrim");
		Grafo grafo = gerar(path);
		return grafo;
	}
	
	public static Grafo readDijkstraFile() {
		Path path = getPath("listaAdjacenciaDijkstra");
		Grafo grafo = gerar(path);
		return grafo;
	}

	private static Grafo gerar(Path path) {
		Grafo grafo = new Grafo();
		try (Scanner scanner = new Scanner(path.toFile())) {
			while (scanner.hasNext()) {
				Matcher matcher = REGEX.matcher(scanner.nextLine().trim());
				if (matcher.find()) {
					String idVerticeOrigem = matcher.group(1);
					String replaced = matcher.group(2).trim().replace(",", System.lineSeparator());
					Matcher matcherVertex = REGEX_VERTEX.matcher(replaced.toString());
					while (matcherVertex.find()) {
						String idVerticeDestino = matcherVertex.group(1).trim();
						String peso = matcherVertex.group(2).replaceAll(System.lineSeparator(), EMPTY).trim();
						Ponto origem = createNewPonto(idVerticeOrigem);
						Ponto destino = createNewPonto(idVerticeDestino);
						grafo.add(new Aresta(origem, destino, Long.valueOf(peso)));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return grafo;
	}

	private static Ponto createNewPonto(String idVertice) {
		return new PontoBuilder().withId(Long.valueOf(idVertice)).withNome(idVertice).build();
	}

	private static Path getPath(String fileName) {
		try {
			return Paths.get(ClassLoader.getSystemResource(fileName).toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
