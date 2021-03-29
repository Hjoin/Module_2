package com.techelevator.services;

import com.techelevator.models.Location;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class LocationService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private ConsoleService console = new ConsoleService();

    public LocationService(String url) {
        //append a trailing slash if not provided
        BASE_URL = url.lastIndexOf('/') == url.length()? url : url + "/";
    }

    public Location getOne(int id) {
        return restTemplate.getForObject(BASE_URL + id, Location.class);
    }

    public Location[] getAll() {
        return restTemplate.getForObject(BASE_URL, Location[].class);
    }

    public Location add(String CSV) {
    	// api code here
	    return null;
    }

    public void delete(int id) {
        // api code here
    }

    public Location update(String CSV) {
        // api code here
        return null;
    }

    private HttpEntity<Location> makeEntity(Location location){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Location> entity = new HttpEntity<>(location, headers);
        return entity;
    }

    private Location makeLocation(String CSV) {
        String[] parsed = CSV.split(",");
        // invalid input
        if (parsed.length < 5 || parsed.length > 6) {
            return null;
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
