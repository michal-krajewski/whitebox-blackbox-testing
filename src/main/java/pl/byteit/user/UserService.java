package pl.byteit.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.byteit.notification.Notification;
import pl.byteit.notification.NotificationClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final NotificationClient notificationClient;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationClient notificationClient) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.notificationClient = notificationClient;
	}

	List<String> getAllUsernames() {
		return userRepository.findAll().stream()
				.map(User::getUsername)
				.toList();
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsernameAndActiveIsTrue(username);
	}

	void registerNewUser(UserRegistrationInput input) {
		User user = new User(input.username(), passwordEncoder.encode(input.password()));
		log.info("Registering user {} with token {}", user.getUsername(), user.getRegistrationToken());
		notificationClient.sendNotification(new Notification(user.getRegistrationToken().toString()));
		userRepository.save(user);
	}

	void activateUser(UUID token) {
		User user = userRepository.findByRegistrationTokenAndActiveIsFalse(token).orElseThrow();
		user.activate();
		userRepository.save(user);
		log.info("Activated user {}", user.getUsername());
	}

}
