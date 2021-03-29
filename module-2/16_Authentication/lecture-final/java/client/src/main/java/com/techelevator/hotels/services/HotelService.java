package com.techelevator.hotels.services;

import com.techelevator.hotels.models.Hotel;
import com.techelevator.hotels.models.Reservation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

public class HotelService {

  public static String AUTH_TOKEN = "";
  private final String INVALID_RESERVATION_MSG = "Invalid Reservation. Please enter the Hotel Id, Full Name, Checkin Date, Checkout Date and Guests";
  private final String BASE_URL;
  private final RestTemplate restTemplate = new RestTemplate();
  private final ConsoleService console = new ConsoleService();

  public HotelService(String url) {
    BASE_URL = url;
  }

  /**
   * Create a new reservation in the hotel reservation system
   * 
   * @param newReservation
   * @return Reservation
   */
  public Reservation addReservation(String newReservation) throws HotelServiceException {
    Reservation reservation = makeReservation(newReservation);
    if (reservation == null) {
      throw new HotelServiceException(INVALID_RESERVATION_MSG);
    }

    //send a post that includes the reservation in the body and the token in the header
    try {
      reservation = restTemplate.postForObject(BASE_URL+"hotels/"+reservation.getHotelID()+"/reservations",
              makeReservationEntity(reservation),Reservation.class);

    }catch (RestClientResponseException ex){
      throw  new HotelServiceException(ex.getRawStatusCode()+" : "+ex.getResponseBodyAsString());
    }
    return reservation;
  }

  /**
   * Updates an existing reservation by replacing the old one with a new
   * reservation
   * 
   * @param CSV
   * @return
   */
  public Reservation updateReservation(String CSV) throws HotelServiceException {
    Reservation reservation = makeReservation(CSV);
    if (reservation == null) {
      throw new HotelServiceException(INVALID_RESERVATION_MSG);
    }

    try {
      restTemplate.exchange(BASE_URL + "reservations/" + reservation.getId(), HttpMethod.PUT,
          makeReservationEntity(reservation), Reservation.class);
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
    return reservation;
  }

  /**
   * Delete an existing reservation
   * 
   * @param id
   */
  public void deleteReservation(int id) throws HotelServiceException {
    try {
      restTemplate.exchange(BASE_URL + "reservations/" + id, HttpMethod.DELETE, makeAuthEntity(), String.class);
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
  }

  /* DON'T MODIFY ANY METHODS BELOW */

  /**
   * List all hotels in the system
   * 
   * @return
   */
  public Hotel[] listHotels() throws HotelServiceException {
    Hotel[] hotels = null;
    try {
      hotels = restTemplate.exchange(BASE_URL + "hotels", HttpMethod.GET, makeAuthEntity(), Hotel[].class).getBody();
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
    return hotels;
  }

  /**
   * Get the details for a single hotel by id
   * 
   * @param id
   * @return Hotel
   */
  public Hotel getHotel(int id) throws HotelServiceException {
    Hotel hotel = null;
    try {
      hotel = restTemplate.exchange(BASE_URL + "hotels/" + id, HttpMethod.GET, makeAuthEntity(), Hotel.class).getBody();
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
    return hotel;
  }

  /**
   * List all reservations in the hotel reservation system
   * 
   * @return Reservation[]
   */
  public Reservation[] listReservations() throws HotelServiceException {
    Reservation[] reservations = null;
    try {
      reservations = restTemplate
          .exchange(BASE_URL + "reservations", HttpMethod.GET, makeAuthEntity(), Reservation[].class).getBody();
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
    return reservations;
  }

  /**
   * List all reservations by hotel id.
   * 
   * @param hotelId
   * @return Reservation[]
   */
  public Reservation[] listReservationsByHotel(int hotelId) throws HotelServiceException {
    Reservation[] reservations = null;
    try {
      reservations = restTemplate.exchange(BASE_URL + "hotels/" + hotelId + "/reservations", HttpMethod.GET,
          makeAuthEntity(), Reservation[].class).getBody();
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
    return reservations;
  }

  /**
   * Find a single reservation by the reservationId
   * 
   * @param reservationId
   * @return Reservation
   */
  public Reservation getReservation(int reservationId) throws HotelServiceException {
    Reservation reservation = null;
    try {
      reservation = restTemplate
          .exchange(BASE_URL + "reservations/" + reservationId, HttpMethod.GET, makeAuthEntity(), Reservation.class)
          .getBody();
    } catch (RestClientResponseException ex) {
      throw new HotelServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
    }
    return reservation;
  }

  /**
   * Creates a reservation from a csv string
   * 
   * @param CSV
   * @return
   */
  private Reservation makeReservation(String CSV) {
    String[] parsed = CSV.split(",");

    // invalid input (
    if (parsed.length < 5 || parsed.length > 6) {
      return null;
    }

    // Add method does not include an id and only has 5 strings
    if (parsed.length == 5) {
      // Create a string version of the id and place into an array to be concatenated
      String[] withId = new String[6];
      String[] idArray = new String[] { new Random().nextInt(1000) + "" };
      // place the id into the first position of the data array
      System.arraycopy(idArray, 0, withId, 0, 1);
      System.arraycopy(parsed, 0, withId, 1, 5);
      parsed = withId;
    }

    return new Reservation(Integer.parseInt(parsed[0].trim()), Integer.parseInt(parsed[1].trim()), parsed[2].trim(),
        parsed[3].trim(), parsed[4].trim(), Integer.parseInt(parsed[5].trim()));
  }

  /**
   * Creates a new {HttpEntity} with the `Authorization: Bearer:` header and a
   * reservation request body
   * 
   * @param reservation
   * @return
   */
  private HttpEntity<Reservation> makeReservationEntity(Reservation reservation) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(AUTH_TOKEN);
    HttpEntity<Reservation> entity = new HttpEntity<>(reservation, headers);
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
