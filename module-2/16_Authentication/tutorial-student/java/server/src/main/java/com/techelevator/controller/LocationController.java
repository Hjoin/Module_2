package com.techelevator.controller;

import com.techelevator.dao.LocationDAO;
import com.techelevator.dao.MemoryLocationDAO;
import com.techelevator.exception.LocationNotFoundException;
import com.techelevator.models.Location;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/locations")
@PreAuthorize("isAuthenticated()")
public class LocationController {

    private LocationDAO dao;

    public LocationController() {
        dao = new MemoryLocationDAO();
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Location> list() {
        return dao.list();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Location get(@PathVariable int id) throws LocationNotFoundException {
        return dao.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Location add(@Valid @RequestBody Location location, Principal principal) {
        System.out.println(principal.getName());
        return dao.create(location);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Location update(@Valid @RequestBody Location location, @PathVariable int id)
            throws LocationNotFoundException {
        return dao.update(location, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws LocationNotFoundException {
        dao.delete(id);
    }

}
