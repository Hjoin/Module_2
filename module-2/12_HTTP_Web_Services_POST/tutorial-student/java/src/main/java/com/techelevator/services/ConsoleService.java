package com.techelevator.services;

import com.techelevator.models.Location;

import java.util.Scanner;

public class ConsoleService {

    private Scanner scanner;

    public ConsoleService() {
        scanner = new Scanner(System.in);
    }

    public int printMainMenu() {
        int menuSelection;
        System.out.println("");
        System.out.println("Welcome to Tech Elevator Locations. Please make a selection: ");
        System.out.println("1: List Tech Elevator Locations");
        System.out.println("2: Show Tech Elevator Location Details");
        System.out.println("3: Add a Tech Elevator Location");
        System.out.println("4: Update a Tech Elevator Location");
        System.out.println("5: Delete a Tech Elevator Location");
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

    public int promptForLocation(Location[] locations, String action) {
        int menuSelection;
        System.out.println("--------------------------------------------");
        System.out.println("Locations");
        System.out.println("--------------------------------------------");
        System.out.println("0. Exit");
        for (Location location : locations) {
            System.out.println(location.getId() + ": " + location.getName());
        }
        System.out.println("");
        System.out.print("Please enter a location id to " + action + ": ");
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
     * Called to add a new location
     *
     * Overloads same method with location object
     * @return
     */
    public String promptForLocationData() {
        return promptForLocationData(null);
    }

    /**
     * Called to update an existing location
     * @param location
     * @return
     */
    public String promptForLocationData(Location location) {
        String locationString;
        System.out.println("--------------------------------------------");
        System.out.println("Enter location data as a comma separated list containing:");
        System.out.println("name, address, city, state, zip");
        if (location != null) {
            System.out.println("Location " + location.getId() + " Data: " + location.getName() + ", " + location.getAddress() + ", " + location.getCity() + ", " + location.getState() + ", " + location.getZip());
        } else {
            System.out.println("Example: Memphis Bell, 123 Bell Street, Memphis, TN, 11111");
        }
        System.out.println("--------------------------------------------");
        System.out.println("");
        locationString = scanner.nextLine();
        if (location != null) {
            locationString = location.getId() + "," + locationString;
        }
        return locationString;
    }

    public void printLocations(Location[] locations) {
        System.out.println("--------------------------------------------");
        System.out.println("Locations");
        System.out.println("--------------------------------------------");
        for (Location location : locations) {
            System.out.println(location.getId() + ": " + location.getName());
        }
    }


    public void printLocation(Location location) {
        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("Location Details");
        System.out.println("--------------------------------------------");
        if(location == null){
            System.out.println("No locations to print");
        } else {
            System.out.println("Id: " + location.getId());
            System.out.println("Name: " + location.getName());
            System.out.println("Address: " + location.getAddress());
            System.out.println("City: " + location.getCity());
            System.out.println("State: " + location.getState());
            System.out.println("Zip: " + location.getZip());
        }
    }

    public void next() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void exit() {
        scanner.close();
        System.exit(0);
    }

    public void printError(String errorMessage){
        System.err.println(errorMessage);
    }

}
