package pl.byteit.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

	@Id
	private UUID id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false)
	private UUID registrationToken;

	@Deprecated //Hibernate only
	User() {
	}

	public User(String username, String password) {
		this.id = UUID.randomUUID();
		this.username = username;
		this.password = password;
		this.active = false;
		this.registrationToken = UUID.randomUUID();
	}

	public void activate() {
		this.active = true;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	UUID getRegistrationToken() {
		return registrationToken;
	}

}
