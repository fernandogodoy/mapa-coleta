package br.com.mpc.rpc;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Fernando
 *
 */
@Component
public class GmapHttp {

	public InputStream get(String requestURI) throws IOException {
		URL url = new URL(requestURI);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		InputStream inputStream = connection.getInputStream();
		return inputStream;
	}

}
