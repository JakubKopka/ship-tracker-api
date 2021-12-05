package dev.kopka.shiptracker.service;

import dev.kopka.shiptracker.client.model.TokenClient;
import dev.kopka.shiptracker.client.service.TokenClientService;
import dev.kopka.shiptracker.domain.model.Token;
import dev.kopka.shiptracker.exception.exceptions.TokenNotFoundException;
import dev.kopka.shiptracker.repository.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TokenServiceTest {
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private TokenClientService tokenClientService;
    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return last token")
    void shouldReturnLastToken() throws TokenNotFoundException {
        //given
        var tokenDb = new Token();
        tokenDb.setToken("TOKENtokenToKeN");
        tokenDb.setValidUntil(new Date());
        when(tokenRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(tokenDb));

        //when
        var token = tokenService.getLastToken();

        //then
        assertNotNull(token);
        assertEquals(tokenDb.getToken(), token.getToken());
        assertEquals(tokenDb.getValidUntil(), token.getValidUntil());
    }

    @Test
    @DisplayName("Should not return last token with TokenNotFoundException")
    void shouldNotReturnLastTokenWithTokenNotFoundException() {
        //given
        var exceptionMessage = "There is a problem with token";
        when(tokenRepository.findTopByOrderByIdDesc()).thenReturn(Optional.empty());
        //when
        //then
        TokenNotFoundException e =
                Assertions.assertThrows(TokenNotFoundException.class, () -> tokenService.getLastToken());
        Assertions.assertEquals(exceptionMessage, e.getMessage());
    }

    @Test
    @DisplayName("Should create and return new token")
    void shouldGenerateAndReturnNewToken() {
        //given
        var tokenString = "TOKEN";
        var tokenClient = new TokenClient();
        tokenClient.setAccessToken(tokenString);
        tokenClient.setExpiresIn(3600);
        var token = new Token();
        token.setToken(tokenString);
        when(tokenClientService.getToken()).thenReturn(tokenClient);
        when(tokenRepository.save(any())).thenReturn(token);

        //when
        var newToken = tokenService.getNewToken();

        //then
        assertNotNull(newToken);
        assertEquals(tokenString, token.getToken());
    }
}