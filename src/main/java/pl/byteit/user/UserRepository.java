package pl.byteit.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndActiveIsTrue(String username);

	Optional<User> findByRegistrationTokenAndActiveIsFalse(UUID registrationToken);

}
