package com.techelevator.hotels;

import com.techelevator.hotels.models.Hotel;
import com.techelevator.hotels.models.Reservation;
import com.techelevator.hotels.services.ConsoleService;
import com.techelevator.hotels.services.HotelService;

public class App {

    private static final String API_BASE_URL = "http://localhost:3000/";

    public static void main(String[] args) {
        int menuSelection = 999;
        int hotelId = -1;

        ConsoleService consoleService = new ConsoleService();
        HotelService hotelService = new HotelService(API_BASE_URL);

        while (menuSelection != 0) {
            menuSelection = consoleService.printMainMenu();
            if(menuSelection == 1) {
                // List all hotels
                Hotel[] hotels = hotelService.listHotels();
                if( hotels != null ){
                    consoleService.printHotels(hotels);
                }
            } else if(menuSelection == 2) {
                // List Reservations for hotel
                Hotel[] hotels = hotelService.listHotels();
                if( hotels != null ) {
                    hotelId = consoleService.promptForHotel(hotels, "List Reservations");
                    if(hotelId !=0) {
                        Reservation[] reservations = hotelService.listReservationsByHotel(hotelId);
                        if( reservations != null ) {
                            consoleService.printReservations(reservations,hotelId);
                        }
                    }
                }
            } else if(menuSelection == 3) {
                // Create new reservation for a given hotel
                String newReservationString = consoleService.promptForReservationData();
                Reservation reservation = hotelService.addReservation(newReservationString);
                // if unsuccessful
                if(reservation == null){
                    System.out.println("Invalid Reservation. Please enter the Hotel Id, Full Name, Checkin Date, Checkout Date and Guests");
                }
            } else if(menuSelection == 4) {
                // Update an existing reservation
                Reservation[] reservations = hotelService.listReservations();
                if( reservations != null ) {
                    int reservationId = consoleService.promptForReservation(reservations, "Update Reservation");
                    Reservation existingReservation = hotelService.getReservation(reservationId);
                    if( existingReservation != null ) {
                        String csv = "" + reservationId + ",";
                        csv += consoleService.promptForReservationData();
                        Reservation reservation = hotelService.updateReservation(csv);
                        if(reservation == null){
                            System.out.println("Invalid Reservation. Hotel Id, Full Name, Checkin Date, Checkout Date, Guests");
                        }
                    }
                }
            } else if(menuSelection == 5) {
                // Delete reservation
                Reservation[] reservations = hotelService.listReservations();
                if( reservations != null ) {
                    int reservationId = consoleService.promptForReservation(reservations, "Delete Reservation");
                    hotelService.deleteReservation(reservationId);
                }
            }
            else if (menuSelection == 0) { // Exit
                consoleService.exit();
            } else {
                // anything else is not valid
                System.out.println("Invalid Selection");
            }
            // Press any key to continue...
            if(hotelId != 0) {
                consoleService.next();
            }
            // Ensure loop continues
            menuSelection = 999;
        }
        // if the loop exits, so does the program
        consoleService.exit();
    }

}
