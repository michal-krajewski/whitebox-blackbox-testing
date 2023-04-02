package pl.byteit.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHttpClient implements HttpClient {

    private final RestTemplate template;

    public RestTemplateHttpClient(RestTemplate template) {
        this.template = template;
    }

    @Override
    public <T> ResponseEntity<T> post(String url, Object requestBody, Class<T> responseType, HttpHeaders headers) throws HttpServerErrorException, HttpClientErrorException {
        HttpEntity<?> request = new HttpEntity<>(requestBody, headers);
        return template.exchange(url, HttpMethod.POST, request, responseType);
    }

    @Override
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType, HttpHeaders headers) throws HttpServerErrorException, HttpClientErrorException {
        HttpEntity<?> request = new HttpEntity<>(headers);
        return template.exchange(url, HttpMethod.GET, request, responseType);
    }
}
