package pl.byteit.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface InactiveUserRepository extends JpaRepository<InactiveUser, UUID> {

}
