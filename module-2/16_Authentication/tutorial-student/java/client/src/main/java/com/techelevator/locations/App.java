package com.techelevator.locations;

import com.techelevator.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

public class App {

    public static void main(String[] args) {
        final String API_BASE_URL = "http://localhost:8080";
        int menuSelection = 999;
        int locationId = -1;

        ConsoleService consoleService = new ConsoleService();
        LocationService locationService = new LocationService(API_BASE_URL + "/locations");
        AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

        while (menuSelection != 0) {
        	menuSelection = consoleService.printMainMenu();
        	try {
                if (menuSelection == 1) {
                	// List all locations
                    consoleService.printLocations(locationService.getAll());
                }
                else if (menuSelection == 2) {
                	// Print location Details
                    locationId = consoleService.promptForLocation(locationService.getAll(), "View");
                    consoleService.printLocation(locationService.getOne(locationId));
                } 
                else if (menuSelection == 3) { 
                	// Add location
                    String newLocationString = consoleService.promptForLocationData();
                    locationService.add(newLocationString);
                    consoleService.printLocations(locationService.getAll());
                }
                else if (menuSelection == 4) {
                	// Update location
                    locationId = consoleService.promptForLocation(locationService.getAll(), "Update");
                    String CSV = consoleService.promptForLocationData(locationService.getOne(locationId));
                    locationService.update(CSV);
                    consoleService.printLocations(locationService.getAll());
                }
                else if (menuSelection == 5) {
                	// Delete location
                    locationId = consoleService.promptForLocation(locationService.getAll(), "Delete");
                    locationService.delete(locationId);
                    consoleService.printLocations(locationService.getAll());
                }
                else if (menuSelection == 6) {
                    String credentials = consoleService.promptForLogin();
                    if( credentials.split(",").length == 2 ) {
                        ResponseEntity<Map> response = authenticationService.login(credentials);
                        if (response.hasBody()) {
                            String token = (String) response.getBody().get("token");
                            locationService.AUTH_TOKEN = token;
                            System.out.println("Login Successful");
                        }
                    } else {
                    	consoleService.printError("Please enter username and password separated by a comma.");
                    }
                }
                else if (menuSelection == 0) { 
                	// Exit
                    consoleService.exit();
                }
                else { 
                	// defensive programming: anything else is not valid
                    consoleService.printError("Invalid Selection");
                }
                // Press any key to continue...
                if(locationId != 0) {
                    consoleService.next();
                }
        	}
        	catch (AuthenticationServiceException ex) {
        		consoleService.printError(ex.getMessage());
        	}
        	catch (LocationServiceException ex) {
        		consoleService.printError(ex.getMessage());
        	}
        	catch (ResourceAccessException ex) {
        		consoleService.printError(ex.getMessage());
        	}
            // Ensure loop continues
            menuSelection = 999;
        }
        // if the loop exits, so does the program
        consoleService.exit();
    }

}
