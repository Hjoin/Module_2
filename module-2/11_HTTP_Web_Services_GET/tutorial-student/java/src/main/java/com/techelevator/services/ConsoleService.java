package com.techelevator.services;

import com.techelevator.models.Location;

import java.util.Scanner;

public class ConsoleService {

    private Scanner scanner;

    public ConsoleService() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints the main menu for the client application
     * @return
     */
    public int printMainMenu() {
        int menuSelection;
        System.out.println("");
        System.out.println("Welcome to Tech Elevator Locations. Please make a selection: ");
        System.out.println("1: List Tech Elevator Locations");
        System.out.println("2: Show Tech Elevator Location Details");
        System.out.println("0: Exit");
        System.out.println("");
        System.out.print("Please choose an option: ");
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException ex) {
            menuSelection = 999;
        }
        return menuSelection;
    }

    /**
     * Given a location this method will print out the location to the console.
     * @param location
     */
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

    /**
     * Given an array of locations this method will print the array to the console.
     * @param locations
     */
    public void printLocations(Location[] locations) {
        System.out.println("--------------------------------------------");
        System.out.println("Locations");
        System.out.println("--------------------------------------------");
        for (Location location : locations) {
            System.out.println(location.getId() + ": " + location.getName());
        }
    }

    /**
     * Will ask for a location id and print the details of that location to the console.
     * @param locations
     * @param action
     * @return
     */
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
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException ex) {
            menuSelection = 999;
        }
        return menuSelection;
    }

    /**
     * Will close the scanner and exit the program.
     */
    public void exit() {
        scanner.close();
        System.exit(0);
    }

}
