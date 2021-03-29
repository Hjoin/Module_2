# Consuming APIs: POST, PUT, and DELETE (C#)

In this exercise, you'll continue working on the command-line application that displays online auction info. The functionality that you wrote in the previous lesson is provided.

Your task is to add web API calls using RestSharp to create new auctions (`POST`), update existing auctions (`PUT`), and delete auctions (`DELETE`).

## Step One: Start the server

Before starting, make sure the web API is up and running. Open the command line and navigate to the `./server/` folder in this exercise.

First, run the command `npm install` to install any dependencies. You won't need to do this on any subsequent run.

To start the server, run the command `npm start`. If there aren't any errors, you'll see the following, which means that you've successfully set up your web API:

```
  \{^_^}/ hi!

  Loading data-generation.js
  Done

  Resources
  http://localhost:3000/auctions

  Home
  http://localhost:3000

  Type s + enter at any time to create a snapshot of the database
```

You can stop the server, or any other process that you've started from the console, by using the keyboard shortcut `ctrl + c`.

In this exercise, you'll modify data on the server. As you're working, you may come across a situation where you want to reset the data. To do this, first stop the server with `ctrl + c`, then restart it with `npm start`.

## Step Two: Explore the API

Before moving on to the next step, explore the web API using Postman. You can access the following endpoints:

- GET: http://localhost:3000/auctions
- GET: http://localhost:3000/auctions/{id} (use a number between 1 and 7 in place of {id})

These are the endpoints you'll work on for this exercise:

- POST: http://localhost:3000/auctions
- PUT: http://localhost:3000/auctions/{id}
- DELETE: http://localhost:3000/auctions/{id}

## Step Three: Evaluation criteria and functional requirements

* All unit tests pass in `AuctionApp.Tests`.
* Code is clean, concise, and readable.

To complete this exercise, you need to complete the `APIService` class by implementing the `AddAuction()`, `UpdateAuction()`, and `DeleteAuction()` methods.

### Tips and tricks

* The `Auction` class has a constructor which takes a CSV string containing either four or five elements: Title, Description, User, Current Bid, and optionally, Auction ID.
* The URL for the API is declared in `APIService.cs`. You may need to append a slash depending on the API method you're using.
* The `AddAuction()` method takes an `Auction` object as a parameter that's passed from `Program.cs`. Have the `AddAuction()` method return the `Auction` object returned from the API when it's successful. Throw an exception if unsuccessful.
* The `UpdateAuction()` method takes an `Auction` object as a parameter that's passed from `Program.cs`. Have the `UpdateAuction()` method return the `Auction` object returned from the API when it's successful. Throw an exception if unsuccessful.
* The `DeleteAuction()` method takes an `int` as a parameter that's passed from the console. It's the `Id` of the auction to delete. Have the `DeleteAuction()` method return `true` if successful. Throw an exception if not successful.
* Consider that the server may return an error, or that the server might not be reached. Implement the necessary error handling.

## Step Four: Add a new auction

The `AddAuction()` method creates a new auction. Make sure to handle any exceptions that might be thrown:

```csharp
public Auction AddAuction(Auction newAuction) {
    // place code here
    throw new NotImplementedException();
}
```

When you've completed the `AddAuction()` method, run the unit tests, and verify that the `AddAuction_ExpectSuccess`, `AddAuction_ExpectFailureResponse`, and `AddAuction_ExpectNoResponse` tests pass.

## Step Five: Update an existing auction

The `UpdateAuction()` method overwrites the existing auction with an updated one for a given ID. Make sure to handle any exceptions that might be thrown:

```csharp
public Auction UpdateAuction(Auction auctionToUpdate) {
    // place code here
    throw new NotImplementedException();
}
```

When you've completed the `UpdateAuction()` method, run the unit tests, and verify that the `UpdateAuction_ExpectSuccess`, `UpdateAuction_ExpectFailureResponse`, and `UpdateAuction_ExpectNoResponse` tests pass.

## Step Six: Delete an auction

The `DeleteAuction()` method removes an auction from the system. Make sure to handle any exceptions that might come up. What happens if you enter an ID for an auction that doesn't exist?

```csharp
public bool DeleteAuction(int auctionId) {
    // place code here
    throw new NotImplementedException();
}
```

When you've completed the `DeleteAuction()` method, run the unit tests, and verify that the `DeleteAuction_ExpectSuccess`, `DeleteAuction_ExpectFailureResponse`, and `DeleteAuction_ExpectNoResponse` tests pass.

Once all unit tests pass, you've completed this exercise.
