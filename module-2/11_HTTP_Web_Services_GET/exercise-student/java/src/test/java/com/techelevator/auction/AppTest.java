package com.techelevator.auction;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.techelevator.models.Auction;
import com.techelevator.services.AuctionService;

public class AppTest {

        private AuctionService auctionService = new AuctionService();
        private RestTemplate mockRestTemplate = mock(RestTemplate.class);

        private final String EXPECTED_API_URL = "http://localhost:3000/auctions";
        private final Auction[] expectedAuctions = { new Auction(0, "Zero", "Zero Auction", "User0", 0.0),
                        new Auction(1, "One", "One Auction", "User1", 1.1) };
        private final Auction expectedAuction = new Auction(0, "Zero", "Zero Auction", "User0", 0.0);

        @Test
        public void listAllAuctions() {
        	// Arrange
            when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL), eq(Auction[].class)))
            	.thenReturn(expectedAuctions);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction[] actualAuctions = auctionService.listAllAuctions();

            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL), eq(Auction[].class));
            assertArrayEquals(expectedAuctions, actualAuctions);
        }

        @Test
        public void listAllAuctionsNoAuctions() {
        	// Arrange
            when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL), eq(Auction[].class)))
            	.thenReturn(new Auction[0]);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction[] actualAuctions = auctionService.listAllAuctions();

            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL), eq(Auction[].class));
            assertArrayEquals(new Auction[0], actualAuctions);
        }

        @Test
        public void listDetailsForAuction() {
        	// Arrange
        	when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL + "/1"), eq(Auction.class)))
        		.thenReturn(expectedAuction);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction actualAuction = auctionService.listDetailsForAuction(1);
            
            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL + "/1"), eq(Auction.class));
            assertEquals(expectedAuction, actualAuction);
        }

        @Test
        public void findAuctionsSearchTitle() {
        	// Arrange
        	when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL + "?title_like=Zero"), eq(Auction[].class)))
        		.thenReturn(expectedAuctions);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction[] actualAuctions = auctionService.findAuctionsSearchTitle("Zero");
            
            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL + "?title_like=Zero"), eq(Auction[].class));
            assertArrayEquals(expectedAuctions, actualAuctions);
        }

        @Test
        public void findAuctionsSearchTitleNotFound() {
        	// Arrange
        	when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL + "?title_like=NoSuchTitle"), eq(Auction[].class)))
        		.thenReturn(new Auction[0]);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction[] actualAuctions = auctionService.findAuctionsSearchTitle("NoSuchTitle");
            
            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL + "?title_like=NoSuchTitle"), eq(Auction[].class));
            assertArrayEquals(new Auction[0], actualAuctions);
        }

        @Test
        public void findAuctionsSearchPrice() {
        	// Arrange
        	when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL + "?currentBid_lte=23.25"), eq(Auction[].class)))
        		.thenReturn(expectedAuctions);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction[] actualAuctions = auctionService.findAuctionsSearchPrice(23.25);
            
            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL + "?currentBid_lte=23.25"), eq(Auction[].class));
            assertArrayEquals(expectedAuctions, actualAuctions);
        }

        @Test
        public void findAuctionsSearchPriceNotFound() {
        	// Arrange
        	when(mockRestTemplate.getForObject(eq(AuctionService.BASE_URL + "?currentBid_lte=0.0"), eq(Auction[].class)))
        		.thenReturn(new Auction[0]);
            auctionService.restTemplate = mockRestTemplate;

            // Act
            Auction[] actualAuctions = auctionService.findAuctionsSearchPrice(0.0);
            
            // Assert
            assertEquals("AuctionService.BASE_URL is not valid. Must be '" + EXPECTED_API_URL + "'", EXPECTED_API_URL, AuctionService.BASE_URL);
            verify(mockRestTemplate, times(1)).getForObject(eq(EXPECTED_API_URL + "?currentBid_lte=0.0"), eq(Auction[].class));
            assertArrayEquals(new Auction[0], actualAuctions);
        }

}
