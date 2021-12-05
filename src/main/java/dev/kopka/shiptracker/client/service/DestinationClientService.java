package dev.kopka.shiptracker.client.service;

import dev.kopka.shiptracker.client.model.DestinationClient;
import dev.kopka.shiptracker.client.model.PointClient;
import dev.kopka.shiptracker.exception.exceptions.ApiException;
import dev.kopka.shiptracker.exception.exceptions.DestinationNotFoundException;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static dev.kopka.shiptracker.client.ClientConst.*;

@Service
public class DestinationClientService {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final String accessKey;
    private final String apiBaseDestinationUrl;

    public DestinationClientService(@Value("${api.positionstack.accessKey}") String accessKey,
                                    @Value("${api.positionstack.url}") String apiBaseDestinationUrl) {
        this.accessKey = accessKey;
        this.apiBaseDestinationUrl = apiBaseDestinationUrl;
    }

    public PointClient getDestinationFromAPI(String destinationName) throws ApiException, DestinationNotFoundException {
        logger.info("Creating request to API for destination name: {}", destinationName);
        ResponseEntity<DestinationClient> responseEntity = createRestTemplate(destinationName);
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            logger.error("There is a problem with API");
            throw new ApiException("There is a problem with API");
        }
        var point = responseEntity.getBody().getData();
        if (point.size() > 0) {
            return point.stream()
                    .filter(pointFromApi -> destinationName.equalsIgnoreCase(pointFromApi.getName()))
                    .findFirst()
                    .orElseThrow(() -> new DestinationNotFoundException("Destination " + destinationName + " not found!"));
        }
        throw new DestinationNotFoundException("Destination " + destinationName + " not found!");
    }

    private ResponseEntity<DestinationClient> createRestTemplate(String destinationName) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                createApiUrlWithParams(destinationName),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                DestinationClient.class);
    }

    private URI createApiUrlWithParams(String destinationName) {
        URI uri = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(apiBaseDestinationUrl);
            uriBuilder.addParameter(PARAMETER_ACCESS_KEY_PARAM, accessKey);
            uriBuilder.addParameter(PARAMETER_QUERY_PARAM, destinationName);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uri;
    }
}