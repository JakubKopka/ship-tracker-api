package dev.kopka.shiptracker.client.service;

import dev.kopka.shiptracker.client.model.ShipClient;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import dev.kopka.shiptracker.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static dev.kopka.shiptracker.client.ClientConst.*;

@Service
public class ShipClientService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final String apiBaseUrl;
    private final String xMax;
    private final String xMin;
    private final String yMax;
    private final String yMin;
    private final TokenService tokenService;

    @Autowired
    public ShipClientService(@Value("${api.barentswatch.base.url}") String apiBaseUrl,
                             @Value("${api.barentswatch.client.x.max}") String xMax,
                             @Value("${api.barentswatch.client.x.min}") String xMin,
                             @Value("${api.barentswatch.client.y.max}") String yMax,
                             @Value("${api.barentswatch.client.y.min}") String yMin,
                             TokenService tokenService) {
        this.apiBaseUrl = apiBaseUrl;
        this.xMax = xMax;
        this.xMin = xMin;
        this.yMax = yMax;
        this.yMin = yMin;
        this.tokenService = tokenService;
    }

    public List<ShipClient> getShipsFormApi() throws TokenNotFoundException, ApiException {
        ResponseEntity<ShipClient[]> responseEntity = getDataFromApi();
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            throw new ApiException("There is a problem with Ships API");
        }
        logger.info("Found {} records of ships", responseEntity.getBody().length);
        return Arrays.asList(responseEntity.getBody());
    }

    private ResponseEntity<ShipClient[]> getDataFromApi() throws TokenNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = generateParams();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> headers = generateHeaders();

        HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(apiBaseUrl, HttpMethod.GET, httpEntity, ShipClient[].class, params);
    }

    private MultiValueMap<String, String> generateHeaders() throws TokenNotFoundException {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HEADER_ACCEPT_PARAM, HEADER_TEXT_PLAIN_VALUE);
        headers.add(HEADER_AUTHORIZATION_PARAM, HEADER_BEARER_WITH_SUFFIX_SPACE_VALUE + tokenService.getLastToken().getToken());
        return headers;
    }

    private MultiValueMap<String, String> generateParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(X_MAX_PARAM, xMax);
        params.add(X_MIN_PARAM, xMin);
        params.add(Y_MAX_PARAM, yMax);
        params.add(Y_MIN_PARAM, yMin);

        return params;
    }
}
