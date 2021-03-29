package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.HotelDAO;
import com.techelevator.reservations.dao.MemoryHotelDAO;
import com.techelevator.reservations.dao.MemoryReservationDAO;
import com.techelevator.reservations.dao.ReservationDAO;
import com.techelevator.reservations.models.Hotel;
import com.techelevator.reservations.models.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {

    private HotelDAO hotelDAO;
    private ReservationDAO reservationDAO;

    public HotelController() {
        this.hotelDAO = new MemoryHotelDAO();
        this.reservationDAO = new MemoryReservationDAO(hotelDAO);
    }

    /**
     * Return All Hotels
     *
     * @return a list of all hotels in the system
     */
    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
    public List<Hotel> list() {
        return hotelDAO.list();
    }

    /**
     * Return hotel information
     *
     * @param id the id of the hotel
     * @return all info for a given hotel
     */
    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.GET)
    public Hotel get(@PathVariable int id) {

        return hotelDAO.get(id);
    }

    /**
     * List all reservations
     *
     */
    @RequestMapping(path="/reservations",method = RequestMethod.GET)
    public List<Reservation> getReservations(){
        return reservationDAO.findAll();
    }

    /*
    get reservation by id
    path: /reservation/{id}
    request method: GET
    return: reservation info for given id using path variable
    TODO - verify that reservation vs reservations is what the client is expecting
     */
    @RequestMapping(path="/reservation/{id}",method = RequestMethod.GET)
    public Reservation getReservationById(@PathVariable int id){
        return reservationDAO.get(id);
    }

    /*
    list all reservations by hotel
    path: /hotels/{id}/reservations
    request method: GET
    return: list of all reservations in the system by hotel
     */
    @RequestMapping(path = "/hotels/{id}/reservations",method = RequestMethod.GET)
    public List<Reservation> getReservationsForHotel(@PathVariable int id){
        return reservationDAO.findByHotel(id);
    }

    /*
    add new reservation
    path: /hotels/{id}/reservations
    request method: POST
    add a new reservation to the given hotel based on the request body
     */
    @RequestMapping(path="hotels/{id}/reservations",method=RequestMethod.POST)
    public Reservation addReservation(@PathVariable int id,@RequestBody Reservation reservation){
        return reservationDAO.create(reservation,id);
    }

    /*
    filter hotels
    path: /hotels/filter?state={state}&city={city}
    request method: GET
    find hotels by state and city (optional)
     */
    @RequestMapping(path="/filter",method=RequestMethod.GET)
    public List<Hotel> filterByCityAndState(@RequestParam String state, @RequestParam(required = false) String city){
        //if city is populated use that, otherwise use state
        if (city!=null){
            return hotelDAO.filterByCity(city);
        } else {
            return hotelDAO.filterByState(state);
        }
    }
}
