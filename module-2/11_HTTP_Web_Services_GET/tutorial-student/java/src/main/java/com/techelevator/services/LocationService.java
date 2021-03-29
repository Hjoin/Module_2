package com.techelevator.services;

import com.techelevator.models.Location;

public class LocationService {

    private String BASE_URL;

    public LocationService(String url) {
        BASE_URL = url.lastIndexOf('/') == url.length() ? url : url + "/";
    }

    public Location[] getAll() {
        return null;
    }

    public Location getOne(int id) {
        return null;
    }

}
