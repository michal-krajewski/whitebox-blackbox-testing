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
	private final InactiveUserRepository inactiveUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final NotificationClient notificationClient;

	public UserService(
			UserRepository userRepository,
			InactiveUserRepository inactiveUserRepository,
			PasswordEncoder passwordEncoder,
			NotificationClient notificationClient) {
		this.userRepository = userRepository;
		this.inactiveUserRepository = inactiveUserRepository;
		this.passwordEncoder = passwordEncoder;
		this.notificationClient = notificationClient;
	}

	List<String> getAllUsernames() {
		return userRepository.findAll().stream()
				.map(User::getUsername)
				.toList();
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	void registerNewUser(UserRegistrationInput input) {
		InactiveUser user = new InactiveUser(input.username(), passwordEncoder.encode(input.password()));
		log.info("Registering user {} with token {}", input.username(), user.getId());
		notificationClient.sendNotification(new Notification(user.getId().toString()));
		inactiveUserRepository.save(user);
	}

	void activateUser(UUID token) {
		InactiveUser user = inactiveUserRepository.findById(token).orElseThrow();
		User saved = userRepository.save(user.activate());
		log.info("Activated user {}", saved.getUsername());
	}

}
