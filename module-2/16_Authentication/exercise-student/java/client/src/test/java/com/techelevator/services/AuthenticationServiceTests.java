package com.techelevator.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTests {

    private final String BASE_URL = "http://localhost:8080/";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthenticationService authenticationService = new AuthenticationService(BASE_URL);

    @Test
    public void step2_loginMethod() throws AuthenticationServiceException {
        Map<String, String> body = Collections.singletonMap("token","abcdefgh123456789");
        ResponseEntity<Map<String,String>> expected = new ResponseEntity<>(body,HttpStatus.OK);

        when(restTemplate.exchange(
                ArgumentMatchers.contains("/login"),
                ArgumentMatchers.eq(HttpMethod.POST),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Map>>any()))
                .thenReturn(new ResponseEntity<>(body,HttpStatus.OK));

        // Act
        ResponseEntity<Map> response = authenticationService.login("user,password");

        // Assert
        assertNotNull(response);
        assertEquals(expected.getBody().get("token"),response.getBody().get("token"));
    }

}
