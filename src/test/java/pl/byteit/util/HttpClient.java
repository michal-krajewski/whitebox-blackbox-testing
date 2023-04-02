package pl.byteit.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public interface HttpClient {

	default ResponseEntity<Void> post(String url, Object requestBody) {
		return post(url, requestBody, Void.class, HttpHeaders.EMPTY);
	}

	<T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, HttpHeaders headers)
			throws HttpServerErrorException, HttpClientErrorException;

	default <T> ResponseEntity<T> get(String url, Class<T> responseType, HttpHeaders headers) throws HttpServerErrorException, HttpClientErrorException {
		return get(url, ParameterizedTypeReference.forType(responseType), headers);
	}

	<T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType, HttpHeaders headers)
			throws HttpServerErrorException, HttpClientErrorException;

}
