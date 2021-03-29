package com.techelevator.locations.controllers;

import com.techelevator.locations.dao.LocationDAO;
import com.techelevator.locations.exception.LocationNotFoundException;
import com.techelevator.locations.models.Location;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationDAO dao;

    public LocationController(LocationDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Location> list() {
        return dao.list();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Location get(@PathVariable int id) throws LocationNotFoundException {
        return dao.get(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Location add(@RequestBody Location location) {
        return dao.create(location);
    }

}
