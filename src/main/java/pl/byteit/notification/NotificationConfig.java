package pl.byteit.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NotificationConfig {

	@Bean
	NotificationClient notificationClient(@Value("${notification.url}") String url) {
		return new NotificationClient(new RestTemplate(), url);
	}

}
