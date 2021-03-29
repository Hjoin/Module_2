package com.techelevator.dao;

import com.techelevator.exception.LocationNotFoundException;
import com.techelevator.models.Location;

import java.util.List;

public interface LocationDAO {

    List<Location> list();

    Location get(int id) throws LocationNotFoundException;

    Location create(Location location);

    Location update(Location location, int id) throws LocationNotFoundException;

    void delete(int id) throws LocationNotFoundException;

}
