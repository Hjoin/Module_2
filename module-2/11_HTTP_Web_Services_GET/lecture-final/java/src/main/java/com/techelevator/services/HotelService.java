package com.techelevator.services;

import com.techelevator.models.City;
import com.techelevator.models.Hotel;
import com.techelevator.models.Review;
import org.springframework.web.client.RestTemplate;

public class HotelService {

    private final String API_BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();

    //API_BASE_URL = "http://localhost:3000/"
    public HotelService(String apiURL) {
        API_BASE_URL = apiURL;
    }

    public Hotel[] listHotels() {
        //call the api to get the list of hotels, getForObject(path to resource/api call, what i expect back)
        return restTemplate.getForObject(API_BASE_URL+"hotels", Hotel[].class);

        //return restTemplate.getForObject("http://localhost:3000/hotels",Hotel[].class);
    }

    public Review[] listReviews() {

        return restTemplate.getForObject(API_BASE_URL+"reviews",Review[].class);
    }

    public Hotel getHotelById(int id) {

        String url = API_BASE_URL+"hotels/"+id;
        return restTemplate.getForObject(url, Hotel.class);
    }

    public Review[] getReviewsByHotelId(int hotelID) {

        //http://localhost:3000/hotels/1/reviews
        return restTemplate.getForObject(API_BASE_URL+"hotels/"+hotelID+"/reviews",Review[].class);
    }

    public Hotel[] getHotelsByStarRating(int stars) {
        //http://localhost:3000/hotels?stars=3
        return restTemplate.getForObject(API_BASE_URL+"hotels?stars="+stars,Hotel[].class);
    }

    /* Note: It's bad form that this query is in the HotelService class when it's not
    calling the hotel service API. It really belongs in its own service class called CityService
    or something similarly named. it's only here for the sake of simplicity, but it's bad practice to b
    be here.
     */

    public City getWithCustomQuery(){

        return restTemplate.getForObject("https://api.teleport.org/api/cities/geonameid:5128581/", City.class);
    }

}
