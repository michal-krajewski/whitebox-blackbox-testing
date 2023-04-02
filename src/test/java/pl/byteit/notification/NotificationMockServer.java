package pl.byteit.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class NotificationMockServer {

	private static final List<Notification> RECORDED_REQUESTS = new ArrayList<>();

	private final int port;
	private final WireMockServer server;

	public NotificationMockServer(int port) {
		this.port = port;
		this.server = new WireMockServer(configure(port));
	}

	public void start() {
		server.start();
	}

	public void mockOkForRequest() {
		server.stubFor(post(urlEqualTo("/notification")).willReturn(aResponse().withStatus(200)));
	}

	public Notification getLastNotificationRequestBody() {
		return RECORDED_REQUESTS.isEmpty() ? null : RECORDED_REQUESTS.get(RECORDED_REQUESTS.size() - 1);
	}

	public void reset() {
		RECORDED_REQUESTS.clear();
		server.resetAll();
	}

	public void stop() {
		server.stop();
	}

	private static WireMockConfiguration configure(int port) {
		return new WireMockConfiguration().port(port)
				.extensions(new ResponseTransformer() {
					@Override
					public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {
						RECORDED_REQUESTS.add(convert(request.getBodyAsString()));
						return response;
					}

					@Override
					public String getName() {
						return "Config name?";
					}
				});
	}

	private static Notification convert(String rawMessage) {
		try {
			return new ObjectMapper().readValue(rawMessage, Notification.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
