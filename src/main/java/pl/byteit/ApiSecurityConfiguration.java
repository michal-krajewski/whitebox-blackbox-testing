package pl.byteit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.formLogin().disable()
				.authorizeHttpRequests()
				.anyRequest().permitAll()
				.and()

				.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED))
				.and()

				.csrf().disable(); //TODO: to be enabled
		return http.build();
	}

}
