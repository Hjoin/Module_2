# Web Services Get Tutorial (C#)

In this tutorial, you'll work on a command-line application that displays Tech Elevator locations. The command-line application is partially complete. You'll write the remaining functionality.

Once the application is running, you'll need to call a web API to both get a list of locations and the details for a single application.

## Step one: Start the server

Before you start, you need to ensure that the web API is up and running. You need to change directories into the `./server/` folder.

Next, from the command line, run the command `npm install` to install any dependencies. You won't need to do this on any subsequent run.

While still in the command line, run the command `npm start` to start the server. If there aren't any errors, you'll see the following, which means that you've successfully set up your web API:

```
 \{^_^}/ hi!

  Loading ./locations.json
  Done

  Resources
  http://localhost:3000/locations

  Home
  http://localhost:3000

  Type s + enter at any time to create a snapshot of the database
  Watching...
```

> You can stop the server, or any other process that you started from the console, by using the keyboard shortcut `ctrl + c`.

## Step Two: Explore the API

Before moving on to the next step, explore the web API using Postman. You can access the following endpoints:

- GET: http://localhost:3000/locations
- GET: http://localhost:3000/locations/{id}

## Step Three: Review the starting code

Next, open the project in Visual Studio and review the following code:

#### Data Model

There's a class in  `/src/Data/Location.cs` that represents the data model for a location object.

#### Driver

You'll find the `Main()` method in `src/Program.cs`.

#### Provided Code

Also in the `Program.cs` file, you'll find several private methods that you'll use to print information to the console:

- `PrintGreeting()`: Prints the welcome greeting along with the menu options
- `PrintLocations()`: Prints a list of locations
- `PrintLocation()`: Prints a single location

#### Your Code

The main method calls a method called `Run`. You'll place most of the code you write inside the `Run()` method:

```csharp
static void Main(string[] args)
  Run();
}
```

## Step Four: Write the console application

In the `Run()` method, you need to create an integer variable to hold the user input. The `PrintGreeting()` method displays the greeting and asks the user to select one of the menu options:

```csharp
int menuSelection;
PrintGreeting();
```

If you run the application, you'll see the following:

```
Welcome to Tech Elevator Locations. Please make a selection:
1: List Tech Elevator Locations
2: Exit

Please choose an option:
```

Next, you'll need to read in the user's choice with `Console.ReadLine()`. Then, you need to parse the string into a number and store it in the variable `menuSelection`. You can use `TryParse()` for this. The method returns a Boolean `false` if it can't parse the input. Output a message to the user if the input isn't a number:

```csharp
if (!int.TryParse(Console.ReadLine(), out menuSelection))
{
    Console.WriteLine("Invalid input. Only input a number.");
}
```

Now that you have the user's selection, you can decide what action to take next:

```csharp
if (menuSelection == 1)
{
    // list locations
}
else
{
    Environment.Exit(0);
}
```

## Step Five: List all locations

If the user selects `1`, you need to list all of the locations returned from the web API. The first thing you'll do is set up a static variable for the `API_URL` because you'll use this several times. Place this above the `Main()` method:

```csharp
const string API_URL = "http://localhost:3000/locations";
```

Next, you'll create a new instance of the RestClient. This is the class that you use to perform a `GET` request to the web API. The locations API returns an array of locations, so the return type is a `List` of `Location`s. By providing the type, RestSharp automatically converts the response into that type and is available in `response.Data`. The conversion process is called deserialization:

```csharp
if (menuSelection == 1)
{
    RestClient client = new RestClient();
    RestRequest request = new RestRequest(API_URL);
    IRestResponse<List<Location>> response = client.Get<List<Location>>(request);
    PrintLocations(response.Data);
}
```

If you run the application, you'll see this:

```
Welcome to Tech Elevator Locations. Please make a selection:
1: List Tech Elevator Locations
2: Exit

Please choose an option: 1

--------------------------------------------
Locations
--------------------------------------------
1: Tech Elevator Cleveland
2: Tech Elevator Columbus
3: Tech Elevator Cincinnati
4: Tech Elevator Pittsburgh
5: Tech Elevator Detroit
6: Tech Elevator Philadelphia

Please enter a location id to get the details:
```

## Step Six: Get Location data

In the last step, you returned a list of locations to the user. The last line of the `PrintLocations()` method asks the user to select a location. When the user selects a location, you'll read in their response. Add this right after the code from the previous step, still in the `if (menuSelection == 1)` block:

```csharp
int id;
if (!int.TryParse(Console.ReadLine(), out id))
{
    Console.WriteLine("Invalid input. Only input a number.");
}
```

Next, you'll validate the user's input. If the number they enter is greater than 0 and less than the number of locations returned, you can consider it valid. This is because there's a fixed number of results, and each result has a corresponding ID. The if statement conditional looks like this:

```csharp
if (id > 0 && id <= response.Data.Count) {
  //...
}
```

Now that you have the ID, you can use the `client` instance that you already created. You'll use this to call the `API_URL` with the ID appended. If you had a chance to test the API in Postman, you know that calling `/locations/1` returns the location data for Tech Elevator Cleveland.

Once you have the location, you can pass it to the `PrintLocation()` method to print it to the console. Add this code inside the if statement:

```csharp
if (id > 0 && id <= response.Data.Count)
{
  RestRequest requestOne = new RestRequest(API_URL + "/" + id);
  IRestResponse<Location> location = client.Get<Location>(requestOne);
  PrintLocation(location.Data);
}
```

If you run the application, you'll see this:

```
Welcome to Tech Elevator Locations. Please make a selection:
1: List Tech Elevator Locations
2: Exit

Please choose an option: 1

--------------------------------------------
Locations
--------------------------------------------
1: Tech Elevator Cleveland
2: Tech Elevator Columbus
3: Tech Elevator Cincinnati
4: Tech Elevator Pittsburgh
5: Tech Elevator Detroit
6: Tech Elevator Philadelphia

Please enter a location id to get the details: 1

--------------------------------------------
Location Details
--------------------------------------------
Id: 1
Name: Tech Elevator Cleveland
Address: 7100 Euclid Ave #140
City: Cleveland
State: OH
Zip: 44103
```

Last but certainly not least, make sure to exit the program by adding this:

```csharp
Environment.Exit(0);
```

## Summary

In this tutorial you learned:

- How to make an HTTP GET request using Postman and inspect the result
- How to make an HTTP GET request to a RESTful web service using C# process the response
- How to convert a single JSON object into a C# Object
- How to convert an array of JSON objects into an array of C# Objects
