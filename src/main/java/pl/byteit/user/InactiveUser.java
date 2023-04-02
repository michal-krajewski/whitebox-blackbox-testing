package pl.byteit.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "inactiva_users")
public class InactiveUser {

	@Id
	private UUID id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Deprecated //Hibernate only
	InactiveUser() {
	}

	public InactiveUser(String username, String password) {
		this.id = UUID.randomUUID();
		this.username = username;
		this.password = password;
	}

	UUID getId() {
		return id;
	}

	User activate() {
		return new User(username, password);
	}

}
