package pl.byteit.user;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pl.byteit.IntegrationTestBase;
import pl.byteit.notification.NotificationMockServer;
import pl.byteit.user.UserController.ActivateUserInput;
import pl.byteit.user.UserController.UserDto;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

class UserServiceBlackboxTest extends IntegrationTestBase {

	private static final NotificationMockServer MOCK_SERVER = new NotificationMockServer(8182);

	@BeforeAll
	public static void setupSuite() {
		MOCK_SERVER.start();
	}

	@AfterAll
	public static void teardown() {
		MOCK_SERVER.stop();
	}

	@BeforeEach
	void setup() {
		MOCK_SERVER.reset();
	}

	@Test
	void shouldAuthorizeActivatedUser() { //Could be split into 2 tests to verify separately sent notification
		MOCK_SERVER.mockOkForRequest();
		client.post("/users/register", new UserRegistrationInput("usr", "pwd"));
		client.post("/users/register/activate", new ActivateUserInput(extractRegistrationToken()));

		ResponseEntity<UserDto> response = client.get("/users/current", UserDto.class, basicAuth("usr", "pwd"));

		assertThat(response.getStatusCode()).isEqualTo(OK);
		assertThat(response.getBody())
				.extracting(UserDto::username)
				.isEqualTo("usr");
	}

	private static UUID extractRegistrationToken() {
		return UUID.fromString(MOCK_SERVER.getLastNotificationRequestBody().message());
	}

	private static HttpHeaders basicAuth(String username, String password) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBasicAuth(username, password);
		return httpHeaders;
	}

}
