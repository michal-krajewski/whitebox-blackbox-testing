package pl.byteit.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.byteit.notification.Notification;
import pl.byteit.notification.NotificationClient;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceWhiteboxTest {

	@Captor
	ArgumentCaptor<User> userCaptor;

	@Mock
	UserRepository userRepository;

	@Mock
	NotificationClient notificationClient;

	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	UserService userService;


//	@Test
//	void shouldCreateInactiveUserAndSendNotifcationAfterRegistration() {
//		when(passwordEncoder.encode("pwd1234")).thenReturn("encoded-pwd1234");
//
//		userService.registerNewUser(new UserRegistrationInput("user", "pwd1234"));
//
//		verify(userRepository).save(userCaptor.capture());
//		User savedUser = userCaptor.getValue();
//		verify(notificationClient).sendNotification(new Notification(savedUser.getRegistrationToken().toString()));
//		assertThat(savedUser)
//				.satisfies(user -> assertThat(user.getId()).isNotNull())
//				.extracting(User::getUsername, User::getPassword, User::isActive)
//				.containsExactly("user", "encoded-pwd1234", false);
//	}
//
//	@Test
//	void shouldSaveActivatedUser() {
//		User existingUser = new User("u1", "encoded");
//		when(userRepository.findByRegistrationTokenAndActiveIsFalse(existingUser.getRegistrationToken()))
//				.thenReturn(Optional.of(existingUser));
//
//		userService.activateUser(existingUser.getRegistrationToken());
//
//		verify(userRepository).save(userCaptor.capture());
//		assertThat(userCaptor.getValue())
//				.extracting(User::getId, User::getUsername, User::getPassword, User::getRegistrationToken, User::isActive)
//				.containsExactly(
//						existingUser.getId(),
//						existingUser.getUsername(),
//						existingUser.getPassword(),
//						existingUser.getRegistrationToken(),
//						true
//				);
//	}

}
