package com.techelevator.reservations.dao;

import com.techelevator.reservations.models.Hotel;

import java.util.List;

public interface HotelDAO {

    List<Hotel> list();

    void create(Hotel hotel);

    Hotel get(int id);

}