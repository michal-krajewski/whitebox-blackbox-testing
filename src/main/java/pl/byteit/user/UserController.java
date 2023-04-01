package pl.byteit.user;

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

	public record ActivateUserInput(UUID token){
	}

}
