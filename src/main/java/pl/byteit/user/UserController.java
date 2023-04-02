package pl.byteit.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public void register(@RequestBody UserRegistrationInput input) {
		userService.registerNewUser(input);
	}

	@GetMapping //Do not create endpoints like that normally
	public List<String> getAllUsernames() {
		return userService.getAllUsernames();
	}

	@PostMapping("/register/activate")
	public void activate(@RequestBody ActivateUserInput input) {
		userService.activateUser(input.token());
	}

	@GetMapping("/current")
	public UserDto getCurrentUserData() {
		return userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.map(user -> new UserDto(user.getUsername()))
				.orElseThrow();
	}

	public record ActivateUserInput(UUID token){
	}

	public record UserDto(String username) {

	}

}
