package pl.byteit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import pl.byteit.user.User;
import pl.byteit.user.UserService;

import java.util.Collections;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.formLogin().disable()
				.httpBasic().and()
				.authorizeHttpRequests()
				.requestMatchers("/users/register/**").permitAll()
				.anyRequest().authenticated()
				.and()

				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED))
				.and()

				.csrf().disable(); //TODO: to be enabled
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserService userService) {
		return auth -> {
			User user = userService.findByUsername(auth.getName())
					.filter(u -> passwordEncoder().matches(((String) auth.getCredentials()), u.getPassword()))
					.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
			return UsernamePasswordAuthenticationToken.authenticated(user.getUsername(), null, Collections.emptyList());
		};
	}

}
