package com.techelevator.hotels.services;

public class HotelServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public HotelServiceException(String message) {
        super(message);
    }
}