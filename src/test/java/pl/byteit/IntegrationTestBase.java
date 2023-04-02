package pl.byteit;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import pl.byteit.util.HttpClient;
import pl.byteit.util.RestTemplateHttpClient;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestExecutionListeners(value = { DatabaseCleaner.class }, mergeMode = MERGE_WITH_DEFAULTS)
public abstract class IntegrationTestBase {

	@LocalServerPort
	protected int port;

	protected HttpClient client;

	@BeforeEach
	void setupBase() {
		client = httpClient();
	}

	public HttpClient httpClient() {
		return new RestTemplateHttpClient(
				new RestTemplateBuilder().rootUri("http://localhost:" + port).build()
		);
	}

}
