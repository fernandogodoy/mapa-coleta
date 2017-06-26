package br.com.mpc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.mpc.exception.GMapRequestException;
import br.com.mpc.model.dto.GMapMatrix;
import br.com.mpc.model.dto.Status;
import br.com.mpc.rpc.GmapHttp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GMapRequestService.class })
public class GMapRequestTest {

	@MockBean
	private GmapHttp gMapHttp;

	@Autowired
	private GMapRequestService gMapRequest;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void shouldReturnResult() throws IOException {
		URL resource = ClassLoader.getSystemResource("exemplo-retorno.json");
		given(gMapHttp.get(Mockito.anyString())).willReturn(resource.openStream());

		GMapMatrix gMapMatrix = gMapRequest.findLocation("UEM", "UBS");
		
		assertNotNull(gMapMatrix);
		assertFalse(gMapMatrix.getRows().isEmpty());
		assertFalse(gMapMatrix.getRows().get(0).getElements().isEmpty());
		assertFalse(gMapMatrix.getRows().get(0).getElements().get(0).getDistance().isEmpty());
		assertFalse(gMapMatrix.getRows().get(0).getElements().get(0).getDuration().isEmpty());
		assertEquals(Status.OK, gMapMatrix.getRows().get(0).getElements().get(0).getStatus());
		assertEquals("2,8 km", gMapMatrix.getRows().get(0).getElements().get(0).getDistance().get(0).getText());
		assertEquals("7 minutos", gMapMatrix.getRows().get(0).getElements().get(0).getDuration().get(0).getText());
	}
	

	@Test
	public void shouldReturnEmpty() throws IOException {
		URL resource = ClassLoader.getSystemResource("exemplo-retorno-empty.json");
		given(gMapHttp.get(Mockito.anyString())).willReturn(resource.openStream());

		GMapMatrix gMapMatrix = gMapRequest.findLocation("UEM", "UBS");
		
		assertNotNull(gMapMatrix);
		assertFalse(gMapMatrix.getRows().isEmpty());
		assertFalse(gMapMatrix.getRows().get(0).getElements().isEmpty());
		assertEquals(Status.ZERO_RESULTS, gMapMatrix.getRows().get(0).getElements().get(0).getStatus());
	}
	
	@Test
	public void shouldReturnInvalidRequest() throws IOException {
		URL resource = ClassLoader.getSystemResource("exemplo-retorno-invalid.json");
		given(gMapHttp.get(Mockito.anyString())).willReturn(resource.openStream());

		GMapMatrix gMapMatrix = gMapRequest.findLocation("UEM", "UBS");
		
		assertNotNull(gMapMatrix);
		assertTrue(gMapMatrix.getRows().isEmpty());
		assertEquals(Status.INVALID_REQUEST, gMapMatrix.getStatus());
	}
	
	@Test
	public void shouldReturnException() throws IOException {
		expectedException.expect(GMapRequestException.class);
		
		given(gMapHttp.get(Mockito.anyString())).willReturn(null);
		gMapRequest.findLocation("UEM", "UBS");
	}

}
