package com.techelevator.locations;

import com.techelevator.models.Location;
import com.techelevator.services.ConsoleService;
import com.techelevator.services.LocationService;

public class App {

    public static void main(String[] args) {
        int menuSelection = 999;
        int locationId = -1;

        ConsoleService consoleService = new ConsoleService();
        LocationService locationService = new LocationService("http://localhost:3000/locations/");


        while (menuSelection != 0) {
            menuSelection = consoleService.printMainMenu();
            if (menuSelection == 1) { // List all locations
                consoleService.printLocations(locationService.getAll());
            } else if (menuSelection == 2) { // Print location Details
                locationId = consoleService.promptForLocation(locationService.getAll(), "View");
                if(locationId !=0) {
                    consoleService.printLocation(locationService.getOne(locationId));
                }
            } else if (menuSelection == 3) { // Add location
                String newLocationString = consoleService.promptForLocationData();
                Location location = locationService.add(newLocationString);
                // if unsuccessful
                if(location == null){
                    System.out.println("Invalid location. Please enter the Title, Address, City, State, Zip Code.");
                } else {
                    // Print all locations every time - provides confirmation of action
                    consoleService.printLocations(locationService.getAll());
                }
            } else if (menuSelection == 4) { // Update location
                locationId = consoleService.promptForLocation(locationService.getAll(), "Update");
                String CSV = consoleService.promptForLocationData(locationService.getOne(locationId));
                Location location = locationService.update(CSV);
                // if unsuccessful
                if(location == null){
                    System.out.println("Invalid location. Please enter the Title, Address, City, State, Zip Code.");
                } else {
                    // Print all locations every time - provides confirmation of action
                    consoleService.printLocations(locationService.getAll());
                }
            } else if (menuSelection == 5) { // Delete location
                locationId = consoleService.promptForLocation(locationService.getAll(), "Delete");
                locationService.delete(locationId);
                // Print all locations every time - provides confirmation of action
                consoleService.printLocations(locationService.getAll());
            } else if (menuSelection == 0) { // Exit
                consoleService.exit();
            } else { // defensive programming: anything else is not valid
                System.out.println("Invalid Selection");
            }
            // Press any key to continue...
            if(locationId != 0) {
                consoleService.next();
            }
            // Ensure loop continues
            menuSelection = 999;
        }
        // if the loop exits, so does the program
        consoleService.exit();
    }

}
