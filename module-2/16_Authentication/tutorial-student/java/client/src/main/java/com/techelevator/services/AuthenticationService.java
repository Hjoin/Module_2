package com.techelevator.services;

import com.techelevator.models.Location;
import com.techelevator.models.LoginDTO;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class AuthenticationService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private ConsoleService console = new ConsoleService();

    public AuthenticationService(String url) {
        this.BASE_URL = url;
    }

    public ResponseEntity<Map> login(String credentials) throws AuthenticationServiceException{
        LoginDTO loginDTO = new LoginDTO(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LoginDTO> entity = new HttpEntity<>(loginDTO, headers);
        ResponseEntity<Map> response = null;
        try {
            response = restTemplate.exchange(BASE_URL + "/login", HttpMethod.POST, entity, Map.class);
            // possible outcomes are we get a token back
            // response entity returns au
        } catch(RestClientResponseException ex) {
            throw new AuthenticationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }

        return response;
    }

}
