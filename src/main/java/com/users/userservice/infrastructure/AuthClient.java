package com.users.userservice.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente HTTP para o serviço de autenticação.
 * Realiza POST em "/auth/register" enviando JSON com { "username", "password" }.
 */
@Component
public class AuthClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AuthClient(RestTemplateBuilder restTemplateBuilder,
                      @Value("${auth.service.base-url}") String baseUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.baseUrl = baseUrl;
    }

    /**
     * Faz o registro de credenciais no serviço de autenticação.
     * @param username nome de usuário (ex: email)
     * @param password senha
     * @return ResponseEntity da chamada (corpo vazio por padrão)
     */
    public ResponseEntity<Void> register(String username, String password) {
        String url = baseUrl + "/auth/register";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RegisterRequest request = new RegisterRequest(username, password);
        HttpEntity<RegisterRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForEntity(url, entity, Void.class);
    }

    /**
     * DTO do corpo de requisição do registro.
     */
    private record RegisterRequest(String username, String password) { }
}

