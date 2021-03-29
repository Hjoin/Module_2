package com.techelevator.auction;

import com.techelevator.models.Auction;
import com.techelevator.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import java.util.Map;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    public static void main(String[] args) {
        int menuSelection;
        int auctionId = -1;

        ConsoleService consoleService = new ConsoleService();
        AuctionService auctionService = new AuctionService(API_BASE_URL);
        AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

        while (true) {
            menuSelection = consoleService.printMainMenu();
            try {
                if (menuSelection == 1) {
                    consoleService.printAuctions(auctionService.getAll());
                } else if (menuSelection == 2) {
                    auctionId = consoleService.promptForAuction(auctionService.getAll(), "View");
                    consoleService.printAuction(auctionService.getOne(auctionId));
                } else if (menuSelection == 3) {
                    Auction[] auctions = auctionService.getByTitle(consoleService.promptForAuctionTitle());
                    consoleService.printAuctions(auctions);
                } else if (menuSelection == 4) {
                    Auction[] auctions = auctionService.getByPrice(consoleService.promptForAuctionPrice());
                    consoleService.printAuctions(auctions);
                } else if (menuSelection == 5) {
                    Auction auction = auctionService.add(consoleService.promptForAuctionData());
                    // if unsuccessful
                    if (auction == null) {
                        System.out.println("Invalid auction. Please enter the Title, Description, User, Current Bid Price (no dollar sign).");
                    } else {
                        // Print all auctions every time - provides confirmation of action
                        Auction[] auctions = auctionService.getAll();
                        consoleService.printAuctions(auctionService.getAll());
                    }
                } else if (menuSelection == 6) {
                    Auction auction = auctionService.getOne(consoleService.promptForAuction(auctionService.getAll(), "Update"));
                    String CSV = consoleService.promptForAuctionData(auction);
                    auction = auctionService.update(CSV);
                    // if unsuccessful
                    if (auction == null) {
                        System.out.println("Invalid auction. Please enter the Title, Description, User, Current Bid Price (no dollar sign).");
                    } else {
                        consoleService.printAuctions(auctionService.getAll());
                    }
                } else if (menuSelection == 7) {
                    auctionId = consoleService.promptForAuction(auctionService.getAll(), "Delete");
                    auctionService.delete(auctionId);
                } else if (menuSelection == 8) {
                    String credentials = consoleService.promptForLogin();
                    if( credentials.split(",").length == 2 ) {
                        ResponseEntity<Map> response = authenticationService.login(credentials);
                        if (response.hasBody()) {
                            String token = (String) response.getBody().get("token");
                            auctionService.AUTH_TOKEN = token;
                            System.out.println("Login Successful");
                        }
                    } else {
                        consoleService.printError("Please enter username and password separated by a comma.");
                    }
                } else if (menuSelection == 0) { // Exit
                    consoleService.exit();
                } else { // defensive programming: anything else is not valid
                    System.out.println("Invalid Selection");
                }

                if (auctionId != 0) {
                    consoleService.next();
                }
            } catch (AuthenticationServiceException ex) {
                consoleService.printError(ex.getMessage());
                consoleService.next();
            } catch (AuctionServiceException ex) {
                consoleService.printError(ex.getMessage());
                consoleService.next();
            } catch (ResourceAccessException ex) {
                consoleService.printError(ex.getMessage());
                consoleService.next();
            }

            // Ensure loop continues
            menuSelection = 999;

        }

    }
}
