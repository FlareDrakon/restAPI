package ru.flare.web.webapp;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.junit.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import ru.flare.web.config.TestModule;
import ru.flare.web.container.WebServer;
import ru.flare.web.container.WebServerConfig;
import ru.flare.web.dto.Transaction;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class ServerTest {

	private static WebServer webServer = new WebServer();
	@Spy
	WebServerConfig webServerConfig = new WebServerConfig();

	@Before
	public void startServer() throws Exception {
		MockitoAnnotations.initMocks(this);
		Injector injector = Guice.createInjector(new TestModule());
		Mockito.when(webServerConfig.getInjector()).thenReturn(injector);
		PowerMockito.whenNew(WebServerConfig.class).withAnyArguments().thenReturn(webServerConfig);
		webServer.start(injector, webServerConfig.getBaseURI());
	}

	@After
	public void stopServer() {
		webServer.stop();
	}

	@Test
	public void testGetAll() throws IOException {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		Transaction transaction = new Transaction();
		transaction.count = 100;
		transaction.toUserId = "user2";
		WebResource resource = client.resource(webServerConfig.getBaseURI()).path("services").path("user").path("user1").path("transaction");
		ClientResponse clientResponse = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, transaction);
		System.out.println( "Got stuff: " + clientResponse );
		Transaction result = clientResponse.getEntity( Transaction.class );

		assertEquals( 200, clientResponse.getStatus() );
		assertEquals(result.toUserId, transaction.toUserId);
		assertEquals(result.count, transaction.count);
	}
}
