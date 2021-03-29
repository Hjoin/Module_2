# Web Services GET Exercise (C#)

In this exercise, you'll work on a command-line application that displays online auction info. A portion of the command-line application is provided. You'll write the remaining functionality.

You'll add web API calls using RestSharp to retrieve a list of auctions, details for a single auction, and filter the list of auctions by title and current bid.

These are the endpoints you'll work on for this exercise:

- GET: http://localhost:3000/auctions
- GET: http://localhost:3000/auctions/{id}
- GET: http://localhost:3000/auctions?title_like=<*value*>
- GET: http://localhost:3000/auctions?currentBid_lte=<*value*>

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
  Watching...
```

## Step Two: Explore the API

Before moving on to the next step, explore the web API using Postman. You can access the following endpoints:

- GET: http://localhost:3000/auctions
- GET: http://localhost:3000/auctions/{id} (use a number between 1 and 7 in place of `{id}`)

## Step Three: Review the starting code

### Data Model

There's a class provided in `/src/Data/Auction.cs` that represents the data model for an auction object. If you've looked at the JSON results from the API, the properties for the class should look familiar.

### Provided Code

In `Program.cs`, you'll find two methods that print information to the console:

- `PrintAuctions()`: Prints a list of auctions
- `PrintAuction()`: Prints a single auction

Take a moment to review `PrintAuctions()` and `PrintAuction()`. Note how the methods access and display the properties of the `auction` object.

### Your Code

In `APIService.cs`, you'll find four methods where you'll add code to call the API methods:

- `GetAllAuctions()`
- `GetDetailsForAuction()`
- `GetAuctionsSearchTitle()`
- `GetAuctionsSearchPrice()`

In the `MenuSelection()` method, you'll see how each menu option calls these methods and passes their return values to one of the `Print` methods described in the previous section.

### Unit tests

In `AuctionApp.Tests`, you'll find the unit tests for the methods you'll write today. After you complete each step, more tests pass.

> Note: The unit tests use two third-party libraries called FluentAssertions and Moq. You can install them through NuGet, like RestSharp. The FluentAssertions library makes it easier to test object comparison, a task that's not always easy to do, even for experienced programmers. Moq is a "mocking" library which allows you to run tests even if the server isn't running.

## Step Four: Write the console application

### 1. Declare the API URL and RestClient

In `APIService.cs`, before adding any API code, you need to declare two public variables. Place these variables between `public static class APIService` and `public static List<Auction> GetAllAuctions()` so that all methods in the class can access it.

Add these variables:

- public const string API_URL = "http://localhost:3000/auctions";
- public IRestClient client = new RestClient();

### 2. List all auctions

In the `GetAllAuctions()` method, remove `throw new NotImplementedException();` and add code here to:

- Create a new `RestRequest` and pass it the API URL.
- Make a `GET` request and save the response in an `IRestResponse` variable using the type parameter so RestSharp can automatically deserialize it. Hint: it'll be a collection of `Auction` items.
- `return` the deserialized object.

Once you've done this, run the unit tests. After `GetAllAuctions_ExpectList` passes, you can run the application. If you select option 1 on the menu, you'll see the ID, title, and current bid for each auction.

### 3. List details for specific auction

In the `GetDetailsForAuction()` method, remove `throw new NotImplementedException();` and add code here to:

- Create a new `RestRequest` and pass it the API URL with a slash `/` and the `auctionId` variable appended to it. Hint: look at the second URL in Step Two.
- Make a `GET` request and save the response in an `IRestResponse` variable using the type parameter so RestSharp can automatically deserialize it. This method only retrieves one `Auction` item.
- `return` the deserialized object.

Once you've done this, run the unit tests. After `GetDetailsForAuction_ExpectSpecificItems` and `GetDetailsForAuction_IdNotFound()` pass, run the application. If you select option 2 on the menu, and enter an ID of one of the auctions, you'll see the full details for that auction.

### 4. Find auctions with a specified term in the title

This API URL uses a query string. If you don't remember what a query string is, refer back to the student book.

Instead of adding a slash `/`, you'll use a question mark `?` and `title_like=` before appending the `searchTitle` variable to the URL. The `title_like` parameter allows you to search for auctions that have a title containing the string you pass to it.

In the `GetAuctionsSearchTitle()` method, remove `throw new NotImplementedException();` and add code here to:

- Create a new `RestRequest` and pass it the API URL with the question mark `?` and query string to appended to it.
- Make a `GET` request and save the response in an `IRestResponse` variable using the type parameter so RestSharp can automatically deserialize it. This one is a collection again.
- `return` the deserialized object.

Once you've done this, run the unit tests. After `GetAuctionsSearchTitle_ExpectList` and `GetAuctionsSearchTitle_ExpectNone` pass, you can run the application. If you select option 3 on the menu, and enter a string, like `watch`, you'll see the ID, title, and current bid for each auction that matches.

### 5. Find auctions below a specified price

This API URL also uses a query string, but the parameter key is `currentBid_lte`. This parameter looks at the `currentBid` field and returns auctions that are **L**ess **T**han or **E**qual to the value you supply.

In the `GetAuctionsSearchPrice()` method, remove `throw new NotImplementedException();` and add code here to:

- Create a new `RestRequest` and pass it the API URL with the question mark `?` and query string to appended to it.
- Make a `GET` request and save the response in an `IRestResponse` variable using the type parameter so RestSharp can automatically deserialize it. This one is a collection again.
- `return` the deserialized object.

Once you've done this, run the unit tests. After `GetAuctionsSearchPrice_ExpectList` and `GetAuctionsSearchPrice_ExpectNone` pass, you can run the application. If you select option 4 on the menu, and enter a number, like `150`, you'll see the ID, title, and current bid for each auction that matches.

Since the value is a `double`, you can enter a decimal value, too. Try entering `125.25`, and then `125.20`, and observe the differences between the two result sets. The "Mad-dog Sneakers" don't appear in the second list because the current bid for them is `125.23`.
