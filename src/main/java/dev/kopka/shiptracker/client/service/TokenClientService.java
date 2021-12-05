package dev.kopka.shiptracker.client.service;

import dev.kopka.shiptracker.client.model.TokenClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static dev.kopka.shiptracker.client.ClientConst.*;

@Service
public class TokenClientService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final String clientId;
    private final String clientSecret;
    private final String apiBaseTokenUrl;

    public TokenClientService(@Value("${api.barentswatch.client.id}") String clientId,
                              @Value("${api.barentswatch.client.secret}") String clientSecret,
                              @Value("${api.barentswatch.token.url}") String apiBaseTokenUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.apiBaseTokenUrl = apiBaseTokenUrl;
    }

    public TokenClient getToken() {
        ResponseEntity<TokenClient> responseEntity = generateNewAccessToken();
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            throw new IllegalStateException();
        }
        logger.info("New token has been generated!");
        return responseEntity.getBody();
    }

    private ResponseEntity<TokenClient> generateNewAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = generateHeaders();
        MultiValueMap<String, String> body = generateBody();

        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(apiBaseTokenUrl, HttpMethod.POST, httpEntity, TokenClient.class);
    }

    private MultiValueMap<String, String> generateHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HEADER_CONTENT_TYPE_PARAM, BODY_CONTENT_TYPE_TOKEN_VALUE);
        return headers;
    }

    private MultiValueMap<String, String> generateBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(BODY_CLIENT_ID_PARAM, clientId);
        body.add(BODY_CLIENT_SECRET_PARAM, clientSecret);
        body.add(BODY_GRANT_TYPE_PARAM, BODY_GRANT_TYPE_VALUE);
        body.add(BODY_SCOPE_PARAM, BODY_SCOPE_VALUE);
        return body;
    }
}