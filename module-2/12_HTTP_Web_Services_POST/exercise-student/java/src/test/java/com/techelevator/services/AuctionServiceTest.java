package com.techelevator.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.models.Auction;

public class AuctionServiceTest {

    private final String EXPECTED_API_URL = "http://localhost:3000/auctions";

    private AuctionService auctionService;
    private RestTemplate mockRestTemplate = null;

    private Auction[] auctions = {
            new Auction(0, "Zero", "Zero Auction", "User0", 0.0),
            new Auction(1, "One", "One Auction", "User1", 1.1)};

    private Auction auction = new Auction(0, "Zero", "Zero Auction", "User0", 0.0);

    @Before
    public void setUp() {
        mockRestTemplate = Mockito.mock(RestTemplate.class);
        auctionService = new AuctionService();
    }

    @After
    public void tearDown() {
        auctionService = null;
        mockRestTemplate = null;
    }

    @Test
    public void add_should_return_new_auction() {
    	// Arrange
        String testAddString = "a,b,c,99.99";
        Auction fake = new Auction(3, "a", "b", "c", 99.99);
        // Need array length from getAll() - mocked with mock autions array
        when(mockRestTemplate.getForObject(
                Mockito.eq(AuctionService.API_URL),
                Mockito.eq(Auction[].class)))
                .thenReturn(auctions);
        when(mockRestTemplate.postForObject(
                Mockito.eq(AuctionService.API_URL),
                any(HttpEntity.class),
                Mockito.eq(Auction.class)))
                .thenReturn(fake);
        auctionService.restTemplate = mockRestTemplate;

        // Act
        Auction actualAuction = auctionService.add(testAddString);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL), eq(Auction[].class));
        verify(mockRestTemplate, times(1)).postForObject(eq(EXPECTED_API_URL), any(HttpEntity.class), eq(Auction.class));
        assertEquals("auctionService.add() should return a new auction",fake, actualAuction);
    }

    @Test
    public void add_should_return_null_for_failure_response_code() {
    	// Arrange
        String testAddString = "a,b,c,99.99";
        // Need array length from getAll() - mocked with mock auctions array
        when(mockRestTemplate.getForObject(
                Mockito.eq(AuctionService.API_URL),
                Mockito.eq(Auction[].class)))
                .thenReturn(auctions);
        when(mockRestTemplate.postForObject(
                Mockito.eq(AuctionService.API_URL),
                any(HttpEntity.class),
                Mockito.eq(Auction.class)))
                .thenThrow(RestClientResponseException.class);
        auctionService.restTemplate = mockRestTemplate;

        // Act
        Auction actualAuction = auctionService.add(testAddString);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL), eq(Auction[].class));
        verify(mockRestTemplate, times(1)).postForObject(eq(EXPECTED_API_URL), any(HttpEntity.class), eq(Auction.class));
        assertNull("auctionService.add() should return null when RestTemplate throws a RestClientResponseException",actualAuction);
    }

    @Test
    public void add_should_return_null_for_communication_failure() {
    	// Arrange
        String testAddString = "a,b,c,99.99";
        // Need array length from getAll() - mocked with mock auctions array
        when(mockRestTemplate.getForObject(
                Mockito.eq(AuctionService.API_URL),
                Mockito.eq(Auction[].class)))
                .thenReturn(auctions);
        when(mockRestTemplate.postForObject(
                Mockito.eq(AuctionService.API_URL),
                any(HttpEntity.class),
                Mockito.eq(Auction.class)))
                .thenThrow(ResourceAccessException.class);
        auctionService.restTemplate = mockRestTemplate;

        // Act
        Auction actualAuction = auctionService.add(testAddString);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL), eq(Auction[].class));
        verify(mockRestTemplate, times(1)).postForObject(eq(EXPECTED_API_URL), any(HttpEntity.class), eq(Auction.class));
        assertNull("auctionService.add() should return null when RestTemplate throws a RestClientResponseException",actualAuction);
    }

    @Test
    public void update_should_return_updated_auction() {
    	// Arrange
        String testUpdateString = "9,q,w,e,11.11";
        Auction fake = new Auction(9, "q", "w", "e", 11.11);
        Mockito.doNothing().when(mockRestTemplate).put(
                Mockito.eq(AuctionService.API_URL + "/9"),
                Mockito.eq(makeEntityHelper(fake)));
        auctionService.restTemplate = mockRestTemplate;

        // Act
        Auction updatedAuction = auctionService.update(testUpdateString);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).put(eq(EXPECTED_API_URL + "/9"), any(HttpEntity.class));
        assertNotNull("auctionService.update() should return the updated auction", updatedAuction);
        assertEquals("The updated auction does not match the expected result ",fake.toString(), updatedAuction.toString());
    }

    @Test
    public void update_should_return_null_for_failure_response_code() {
        // Arrange
    	String testUpdateString = "9,q,w,e,11.11";
        Mockito.doThrow(RestClientResponseException.class).when(mockRestTemplate).put(
                Mockito.eq(AuctionService.API_URL + "/9"),
                any(HttpEntity.class));
        auctionService.restTemplate = mockRestTemplate;

        // Act
        Auction updatedAuction = auctionService.update(testUpdateString);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).put(eq(EXPECTED_API_URL + "/9"), any(HttpEntity.class));
        assertNull("auctionService.update() should return null when RestTemplate throws a RestClientResponseException", updatedAuction);
    }

    @Test
    public void update_should_return_null_for_communication_failure() {
        // Arrange
    	String testUpdateString = "9,q,w,e,11.11";
        Mockito.doThrow(ResourceAccessException.class).when(mockRestTemplate).put(
                Mockito.eq(AuctionService.API_URL + "/9"),
                any(HttpEntity.class));
        auctionService.restTemplate = mockRestTemplate;

        // Act
        Auction updatedAuction = auctionService.update(testUpdateString);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).put(eq(EXPECTED_API_URL + "/9"), any(HttpEntity.class));
        assertNull("auctionService.update() should return null when RestTemplate throws a RestClientResponseException", updatedAuction);
    }

    @Test
    public void delete_should_return_true_when_resource_successfully_deleted() {
        // Arrange
    	Mockito.doNothing().when(mockRestTemplate).delete(
                Mockito.eq(AuctionService.API_URL + "/1"),
                any(HttpEntity.class));
        auctionService.restTemplate = mockRestTemplate;

        // Act
        boolean result = auctionService.delete(1);

        // Assert
        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).delete(eq(EXPECTED_API_URL + "/1"), any(HttpEntity.class));
        assertTrue("auctionService.delete() should return true for successful deletion.", result);
    }

    @Test
    public void delete_should_return_false_for_failure_response_code() {
        // Arrange
    	Mockito.doThrow(RestClientResponseException.class).when(mockRestTemplate).delete(
                Mockito.eq(AuctionService.API_URL + "/1"),
                any(HttpEntity.class));
        auctionService.restTemplate = mockRestTemplate;

        // Act
        boolean result = auctionService.delete(1);

        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).delete(eq(EXPECTED_API_URL + "/1"), any(HttpEntity.class));
        assertFalse("auctionService.delete() should return false when a RestClientResponseException is thrown.", result);
    }

    @Test
    public void delete_should_return_false_for_communication_failure() {
        // Arrange
    	Mockito.doThrow(ResourceAccessException.class).when(mockRestTemplate).delete(
                Mockito.eq(AuctionService.API_URL + "/1"),
                any(HttpEntity.class));
        auctionService.restTemplate = mockRestTemplate;

        // Act
        boolean result = auctionService.delete(1);

        assertEquals("AuctionService.API_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.API_URL);
        verify(mockRestTemplate, times(1)).delete(eq(EXPECTED_API_URL + "/1"), any(HttpEntity.class));
        assertFalse("auctionService.delete() should return false when a RestClientResponseException is thrown.", result);
    }

    private HttpEntity<Auction> makeEntityHelper(Auction auction) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Auction> entity = new HttpEntity<>(auction, headers);
        return entity;
    }


}
