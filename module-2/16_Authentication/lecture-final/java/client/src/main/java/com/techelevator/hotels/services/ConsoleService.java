package com.techelevator.hotels.services;

import com.techelevator.hotels.models.Hotel;
import com.techelevator.hotels.models.Reservation;

import java.util.Scanner;

public class ConsoleService {

  private Scanner scanner;

  public ConsoleService() {
    scanner = new Scanner(System.in);
  }

  public int printMainMenu() {
    int menuSelection;
    System.out.println("");
    System.out.println("Welcome to Tech Elevator Hotels. Please make a selection: ");
    System.out.println("1: List Hotels");
    System.out.println("2: List Reservations for Hotel");
    System.out.println("3: Create new Reservation for Hotel");
    System.out.println("4: Update existing Reservation for Hotel");
    System.out.println("5: Delete Reservation");
    System.out.println("6: Login");
    System.out.println("0: Exit");
    System.out.println("");
    System.out.print("Please choose an option: ");
    // ensures no InputMisMatchException is thrown
    if (scanner.hasNextInt()) {
      menuSelection = scanner.nextInt();
      scanner.nextLine();
    } else {
      menuSelection = 999;
    }
    return menuSelection;
  }

  public int promptForHotel(Hotel[] hotels, String action) {
    int menuSelection;
    System.out.println("--------------------------------------------");
    System.out.println("Hotels");
    System.out.println("--------------------------------------------");
    for (Hotel hotel : hotels) {
      System.out.println(hotel.getId() + ": " + hotel.getName());
    }
    System.out.println("");
    System.out.print("Please enter a hotel id to " + action + ": ");
    // ensures no InputMisMatchException is thrown
    if (scanner.hasNextInt()) {
      menuSelection = scanner.nextInt();
      scanner.nextLine();
    } else {
      menuSelection = 999;
    }
    return menuSelection;
  }

  /**
   * Called to add a new hotel
   *
   * Overloads same method with hotel object
   *
   * @return
   */
  public String promptForHotelData() {
    return promptForHotelData(null);
  }

  /**
   * Called to update an existing Hotel
   *
   * @param hotel
   * @return
   */
  public String promptForHotelData(Hotel hotel) {
    String hotelString;
    System.out.println("--------------------------------------------");
    System.out.println("Enter hotel data as a comma separated list containing:");
    System.out.println("name, stars, roomsAvailable");
    if (hotel != null) {
      System.out.println("Hotel " + hotel.getId() + " Data: " + hotel.getName() + ", " + hotel.getStars() + ", "
          + hotel.getRoomsAvailable());
    } else {
      System.out.println("Example: New Hotel Name, 3, 25");
    }
    System.out.println("--------------------------------------------");
    System.out.println("");
    hotelString = scanner.nextLine();
    if (hotel != null) {
      hotelString = hotel.getId() + "," + hotelString;
    }
    return hotelString;
  }

  public void printHotels(Hotel[] hotels) {
    System.out.println("--------------------------------------------");
    System.out.println("Hotels");
    System.out.println("--------------------------------------------");
    for (Hotel hotel : hotels) {
      System.out.println(hotel.getId() + ": " + hotel.getName());
    }
  }

  public void printHotel(Hotel hotel) {
    System.out.println(hotel.toString());
  }

  public void next() {
    System.out.println("\nPress Enter to continue...");
    scanner.nextLine();
  }

  public void exit() {
    scanner.close();
    System.exit(0);
  }

  public void printError(String errorMessage) {
    System.err.println(errorMessage);
  }

  public void printReservations(Reservation[] reservations, int hotelId) {
    String msg = hotelId == -1 ? "All Reservations" : "Reservations for hotel: " + hotelId;

    System.out.println("--------------------------------------------");
    System.out.println(msg);
    System.out.println("--------------------------------------------");
    if(reservations.length !=0 ) {
      for (Reservation reservation : reservations) {
        System.out.println(reservation.toString());
      }
    } else {
      System.out.println("There are no reservations for hotel: " + hotelId);
    }
  }

  public String promptForReservationData() {
    return promptForReservationData(null);
  }

  public String promptForReservationData(Reservation reservation) {
    String reservationString;
    System.out.println("--------------------------------------------");
    System.out.println("Enter reservation data as a comma separated list containing:");
    System.out.println("Hotel ID, Full Name, Checkin Date, Checkout Date, Number of Guests");
    if (reservation != null) {
      System.out.println(reservation.toString());
    } else {
      System.out.println("Example: 1, John Smith, 10/10/2020, 10/14/2020, 2");
    }
    System.out.println("--------------------------------------------");
    System.out.println("");
    reservationString = scanner.nextLine();
    if (reservation != null) {
      reservationString = reservation.getId() + "," + reservationString;
    }
    return reservationString;
  }

  public int promptForReservation(Reservation[] reservations, String action) {
    int menuSelection;
    System.out.println("--------------------------------------------");
    System.out.println("Reservations");
    System.out.println("--------------------------------------------");
    System.out.println("0. Exit");
    for (Reservation reservation : reservations) {
      System.out.println(reservation.toString());
    }
    System.out.println("");
    System.out.print("Please enter a reservation id to " + action + ": ");
    // ensures no InputMisMatchException is thrown
    if (scanner.hasNextInt()) {
      menuSelection = scanner.nextInt();
      scanner.nextLine();
    } else {
      menuSelection = 999;
    }
    return menuSelection;
  }

  public String promptForLogin() {
    String credentials = "";
    System.out.println("Enter username,password");
    credentials = scanner.nextLine();
    return credentials;
  }

}
