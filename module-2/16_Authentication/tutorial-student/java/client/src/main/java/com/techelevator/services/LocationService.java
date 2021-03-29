package com.techelevator.services;

import com.techelevator.models.Location;
import org.springframework.http.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class LocationService {

    public static String AUTH_TOKEN = "";
    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private ConsoleService console = new ConsoleService();

    public LocationService(String url) {
        BASE_URL = url;
    }

    public Location getOne(int id) throws LocationServiceException {
        Location location = null;
        try {
            location = restTemplate.getForObject(BASE_URL + "/" + id, Location.class);
        } catch (RestClientResponseException ex) {
            throw new LocationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return location;
    }

    public Location[] getAll() throws LocationServiceException {
        Location[] locations = null;
        try {
            locations = restTemplate.getForObject(BASE_URL, Location[].class);
        } catch (RestClientResponseException ex) {
            throw new LocationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return locations;
    }

    public Location add(String CSV) throws LocationServiceException {
        Location location = makeLocation(CSV);
        try {
            location = restTemplate.postForObject(BASE_URL, makeLocationEntity(location), Location.class);
        } catch (RestClientResponseException ex) {
            throw new LocationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return location;
    }

    public Location update(String CSV) throws LocationServiceException {
        Location location = makeLocation(CSV);
        try {
            restTemplate.exchange(BASE_URL + "/" + location.getId(), HttpMethod.PUT, makeLocationEntity(location),
                    Location.class);
        } catch (RestClientResponseException ex) {
            throw new LocationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
        return location;
    }

    public void delete(int id) throws LocationServiceException {
        try {
            restTemplate.delete(BASE_URL + id);
        } catch (RestClientResponseException ex) {
            throw new LocationServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        }
    }

    private HttpEntity<Location> makeLocationEntity(Location location) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);
        return entity;
    }

    private Location makeLocation(String CSV) throws LocationServiceException {
        String[] parsed = CSV.split(",");
        // invalid input
        if (parsed.length < 5 || parsed.length > 6) {
            throw new LocationServiceException("Invalid Location data format.");
        }
        // Add method does not include an id and only has 5 strings
        if (parsed.length == 5) {
            // Create a string version of the id and place into an array to be concatenated
            String[] withId = new String[6];
            String[] idArray = new String[]{getAll().length + 1 + ""};
            // place the id into the first position of the data array
            System.arraycopy(idArray, 0, withId, 0, 1);
            System.arraycopy(parsed, 0, withId, 1, 5);
            parsed = withId;
        }
        return new Location(Integer.parseInt(parsed[0].trim()), parsed[1].trim(), parsed[2].trim(), parsed[3].trim(), parsed[4].trim(), parsed[5].trim());
    }

}
