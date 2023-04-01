package pl.byteit.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.byteit.notification.NotificationClient;

@Configuration
public class UserConfiguration {

	@Bean
	UserService userService(UserRepository userRepository, PasswordEncoder passwordEncoder, NotificationClient notificationClient) {
		return new UserService(userRepository, passwordEncoder, notificationClient);
	}

}
