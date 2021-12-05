package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.client.service.TokenClientService;
import dev.kopka.shiptracker.domain.model.Token;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import dev.kopka.shiptracker.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {
    Logger logger = LoggerFactory.getLogger(TokenService.class);

    private final TokenRepository tokenRepository;
    private final TokenClientService tokenClientService;

    @Autowired
    public TokenService(TokenRepository tokenRepository, TokenClientService tokenClientService) {
        this.tokenRepository = tokenRepository;
        this.tokenClientService = tokenClientService;
    }

    public Token getLastToken() throws TokenNotFoundException {
        return tokenRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new TokenNotFoundException("There is a problem with token"));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "${cron.for.token.generator}")
    public Token getNewToken() {
        var clientToken = tokenClientService.getToken();
        var validUntil = addSecondsToCurrentDate(clientToken.getExpiresIn());
        logger.info("New token will be valid until: {}", validUntil);
        Token token = new Token();
        token.setToken(clientToken.getAccessToken());
        token.setValidUntil(validUntil);
        return tokenRepository.save(token);
    }

    private Date addSecondsToCurrentDate(int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
}
