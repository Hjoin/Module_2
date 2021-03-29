package com.techelevator.services;

import com.techelevator.models.Auction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTests {

    private final String BASE_URL = "http://localhost:8080/";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuctionService auctionService = new AuctionService(BASE_URL);

    @Test
    public void step3_getAllAuctions() throws AuctionServiceException {
        Auction[] auctions = { new Auction(), new Auction(), new Auction() };

        when(restTemplate.exchange(
                ArgumentMatchers.eq(BASE_URL + "auctions"),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Auction[]>>any()))
                .thenReturn(new ResponseEntity<>(auctions, HttpStatus.OK));

        // Act
        Auction[] allAuctions = auctionService.getAll();

        // Assert
        assertNotNull(allAuctions);
        assertEquals(auctions.length, allAuctions.length);
    }


}
