package com.techelevator.services;

import com.techelevator.models.Auction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AuctionService {

    public static String AUTH_TOKEN = "";
    private final String BASE_URL;
    public RestTemplate restTemplate = new RestTemplate();
    private final ConsoleService console = new ConsoleService();

    public AuctionService(String url) {
        BASE_URL = url;
    }

    public Auction[] getAll() throws AuctionServiceException {
        Auction[] auctions = null;
        try {
            auctions = restTemplate.exchange(BASE_URL + "auctions", HttpMethod.GET, makeAuthEntity(), Auction[].class).getBody();
            // send request here
        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return auctions;
    }

    public Auction getOne(int id) throws AuctionServiceException {
        Auction auction = null;
        try {
            auction = restTemplate
                    .exchange(BASE_URL + "auctions/" + id, HttpMethod.GET, makeAuthEntity(), Auction.class).getBody();
        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return auction;
    }

    public Auction[] getByTitle(String title) throws AuctionServiceException {
        Auction[] auctions = null;
        try {
            auctions = restTemplate.exchange(BASE_URL + "auctions?title_like=" + title, HttpMethod.GET,
                    makeAuthEntity(), Auction[].class).getBody();
        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return auctions;
    }

    public Auction[] getByPrice(double price) throws AuctionServiceException {
        Auction[] auctions = null;
        try {
            auctions = restTemplate.exchange(BASE_URL + "auctions?currentBid_lte=" + price, HttpMethod.GET,
                    makeAuthEntity(), Auction[].class).getBody();

        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return auctions;
    }

    public Auction add(String auctionString) throws AuctionServiceException {
        Auction auction = makeAuction(auctionString);
        try {
            auction = restTemplate.postForObject(BASE_URL + "auctions", makeAuctionEntity(auction), Auction.class);
        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return auction;
    }

    public Auction update(String auctionString) throws AuctionServiceException {
        Auction auction = makeAuction(auctionString);
        try {
            restTemplate.put(BASE_URL + "auctions/" + auction.getId(), makeAuctionEntity(auction));
        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return auction;
    }

    /**
     * An alternative approach to all the other methods is to throw an exception and
     * let the caller figure out what the problem means to it. In other words, the
     * delete() method is not making any assumptions about what to do with a network
     * or response exception.
     *
     * @param id
     */
    public void delete(int id) throws AuctionServiceException {
        try {
            restTemplate.exchange(BASE_URL + "auctions/" + id, HttpMethod.DELETE, makeAuthEntity(), String.class);
        } catch (RestClientResponseException ex) {
            throw new AuctionServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }

    private Auction makeAuction(String CSV) {
        String[] parsed = CSV.split(",");
        // invalid input
        if (parsed.length < 4 || parsed.length > 5) {
            return null;
        }
        // Add method does not include an id and only has 5 strings
        if (parsed.length == 4) {
            // Create a string version of the id and place into an array to be concatenated
            String[] withId = new String[6];
            Auction[] auctions = new Auction[0];
            try {
                auctions = getAll();
            } catch (AuctionServiceException e) {
                e.printStackTrace();
            }
            if (auctions == null) {
                return null; // Some exception or other problem occurred.
            }
            String[] idArray = new String[] { auctions.length + 1 + "" };
            // place the id into the first position of the data array
            System.arraycopy(idArray, 0, withId, 0, 1);
            System.arraycopy(parsed, 0, withId, 1, 4);
            parsed = withId;
        }
        return new Auction(Integer.parseInt(parsed[0].trim()), parsed[1].trim(), parsed[2].trim(), parsed[3].trim(),
                Double.parseDouble(parsed[4].trim()));
    }

    /**
     * Creates a new {HttpEntity} with the `Authorization: Bearer:` header and an
     * auction request body
     *
     * @param auction
     * @return
     */
    private HttpEntity<Auction> makeAuctionEntity(Auction auction) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity<Auction> entity = new HttpEntity<>(auction, headers);
        return entity;
    }

    /**
     * Returns an {HttpEntity} with the `Authorization: Bearer:` header
     *
     * @return {HttpEntity}
     */
    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

}
