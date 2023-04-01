package pl.byteit.notification;

import org.springframework.web.client.RestTemplate;

public class NotificationClient {

	private final RestTemplate restTemplate;
	private final String url;

	NotificationClient(RestTemplate restTemplate, String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public void sendNotification(Notification notification) {
		restTemplate.postForEntity(url, notification, Void.class);
	}

}
