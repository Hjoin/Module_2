package com.techelevator.services;

import com.techelevator.models.Auction;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner;

    public ConsoleService() {
        scanner = new Scanner(System.in);
    }

    public int printMainMenu() {
        int menuSelection;
        System.out.println("Welcome to Online Auctions! Please make a selection: ");
        System.out.println("1: List all auctions");
        System.out.println("2: List details for specific auction");
        System.out.println("3: Find auctions with a specific term in the title");
        System.out.println("4: Find auctions below a specified price");
        System.out.println("5: Create a new auction");
        System.out.println("6: Modify an auction");
        System.out.println("7: Delete an auction");
        System.out.println("8: Login");
        System.out.println("0: Exit");
        System.out.print("\nPlease choose an option: ");
        // ensures no InputMisMatchException is thrown
        if (scanner.hasNextInt()) {
            menuSelection = scanner.nextInt();
            scanner.nextLine();
        } else {
            menuSelection = 999;
        }
        return menuSelection;
    }

    public int promptForAuction(Auction[] auctions, String action) {
        int menuSelection;
        System.out.println("--------------------------------------------");
        System.out.println("Auctions");
        System.out.println("--------------------------------------------");
        System.out.println("0. Exit");
        for (Auction auction : auctions) {
            System.out.println(auction.getId() + ": " + auction.getTitle());
        }
        System.out.print("\nPlease enter a auction id to " + action + ": ");
        // ensures no InputMisMatchException is thrown
        if (scanner.hasNextInt()) {
            menuSelection = scanner.nextInt();
            scanner.nextLine();
        } else {
            menuSelection = 999;
        }
        return menuSelection;
    }

    public String promptForAuctionTitle(){
        System.out.println("--------------------------------------------");
        System.out.println("Enter title to search for:");
        return scanner.nextLine();
    }

    public double promptForAuctionPrice(){
        double price;
        System.out.println("--------------------------------------------");
        System.out.println("Enter the maximum price to search for:");
        // ensures no InputMisMatchException is thrown
        if (scanner.hasNextDouble()) {
            price = scanner.nextDouble();
            scanner.nextLine();
        } else {
            price = 0.0;
        }
        return price;
    }

    public String promptForAuctionData() {
        return promptForAuctionData(null);
    }

    public String promptForAuctionData(Auction auction) {
        String auctionString;
        System.out.println("--------------------------------------------");
        System.out.println("Enter auction data as a comma separated list containing:");
        System.out.println("title, description, user, current bid price (without dollar sign)");
        if (auction != null) {
            System.out.println("Auction " + auction.getId() + " Data: " + auction.getTitle() + ", " + auction.getDescription() + ", " + auction.getUser() + ", " + auction.getCurrentBid());
        } else {
            System.out.println("Example: Mad-dog Sneakers, Soles check. Laces check., Cierra_Pagac, 125.23");
        }
        System.out.println("--------------------------------------------\n");
        auctionString = scanner.nextLine();
        if (auction != null) {
            auctionString = auction.getId() + "," + auctionString;
        }
        return auctionString;
    }

    public void printAuctions(Auction[] auctions) {
        if (auctions != null) {
            System.out.println("--------------------------------------------");
            System.out.println("Auctions");
            System.out.println("--------------------------------------------");
            for (Auction auction : auctions) {
                System.out.println(auction.currentBidToString());
            }
        }
    }

    public void printAuction(Auction auction) {
        System.out.println("--------------------------------------------");
        System.out.println("Auction Details");
        System.out.println("--------------------------------------------");
        if (auction == null) {
            System.out.println("No auctions to print");
        } else {
            System.out.println("Id: " + auction.getId());
            System.out.println("Title: " + auction.getTitle());
            System.out.println("Description: " + auction.getDescription());
            System.out.println("User: " + auction.getUser());
            System.out.println("Current Bid: " + auction.getCurrentBid());
            System.out.println("\n");
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

    public void printError(String errorMessage) {
        System.err.println(errorMessage);
    }

    public String promptForLogin() {
        String credentials = "";
        System.out.println("Enter username,password");
        credentials = scanner.nextLine();
        return credentials;
    }
}
