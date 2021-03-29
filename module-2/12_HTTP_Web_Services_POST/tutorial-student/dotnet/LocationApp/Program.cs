using System;
using System.Collections.Generic;
using RestSharp;

namespace LocationApp
{
    class Program
    {
        static APIService api = new APIService();

        static void Main(string[] args)
        {
            Run();
        }

        private static void Run()
        {
            Console.WriteLine("Welcome to Tech Elevator Locations. Please make a selection.");
            MenuSelection();
        }

        private static void MenuSelection()
        {
            int menuSelection = -1;
            while (menuSelection != 0)
            {
                Console.WriteLine("");
                Console.WriteLine("Menu:");
                Console.WriteLine("1: List Tech Elevator Locations");
                Console.WriteLine("2: Show Tech Elevator Location Details");
                Console.WriteLine("3: Add a Tech Elevator Location");
                Console.WriteLine("4: Update a Tech Elevator Location");
                Console.WriteLine("5: Delete a Tech Elevator Location");
                Console.WriteLine("0: Exit");
                Console.WriteLine("---------");
                Console.Write("Please choose an option: ");

                if (!int.TryParse(Console.ReadLine(), out menuSelection))
                {
                    Console.WriteLine("Invalid input. Only input a number.");
                }
                else if (menuSelection == 1)
                {
                    try
                    {
                        List<Location> listLocations = api.GetAllLocations();
                        if (listLocations != null)
                        {
                            PrintLocations(listLocations);
                        }
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine(ex.Message);
                    }
                }
                else if (menuSelection == 2)
                {
                    Console.Write("Please enter a location id to get the details: ");
                    string input = Console.ReadLine();
                    if (!int.TryParse(input, out int locationId))
                    {
                        Console.WriteLine("Invalid input. Please enter only a number.");
                    }
                    else if (locationId > 0)
                    {
                        try
                        {
                            Location location = api.GetDetailsForLocation(locationId);
                            if (location != null)
                            {
                                PrintLocation(location);
                            }
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                        }
                    }
                }
                else if (menuSelection == 3)
                {
                    string newLocationString = PromptForLocationData();
                    Location newLocation = new Location(newLocationString);
                    if (newLocation.IsValid)
                    {
                        try
                        {
                            Location returnedLocation = api.AddLocation(newLocation);
                            if (returnedLocation != null)
                            {
                                Console.WriteLine("Location successfully added.");
                                PrintLocations(api.GetAllLocations()); //confirmation of success
                            }
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                        }
                    }
                    else
                    {
                        Console.WriteLine("Location string not in correct format.");
                    }
                }
                else if (menuSelection == 4)
                {
                    Console.Write("Please enter a location id to update: ");
                    string input = Console.ReadLine();
                    if (!int.TryParse(input, out int locationId))
                    {
                        Console.WriteLine("Invalid input. Please enter only a number.");
                    }
                    else if (locationId > 0)
                    {
                        try
                        {
                            Location locationToUpdate = api.GetDetailsForLocation(locationId);
                            if (locationToUpdate != null)
                            {
                                string updLocationString = PromptForLocationData(locationToUpdate);

                                Location updLocation = new Location(updLocationString);
                                if (updLocation.IsValid)
                                {
                                    Location returnedLocation = api.UpdateLocation(updLocation);
                                    if (returnedLocation != null)
                                    {
                                        Console.WriteLine("Location successfully updated.");
                                        PrintLocations(api.GetAllLocations()); //confirmation of success
                                    }
                                }
                                else
                                {
                                    Console.WriteLine("Location string not in correct format.");
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                        }
                    }
                }
                else if (menuSelection == 5)
                {
                    Console.Write("Please enter a location id to delete: ");
                    string input = Console.ReadLine();
                    if (!int.TryParse(input, out int locationId))
                    {
                        Console.WriteLine("Invalid input. Please enter only a number.");
                    }
                    else if (locationId > 0)
                    {
                        try
                        {
                            bool deleteSuccess = api.DeleteLocation(locationId);
                            if (deleteSuccess)
                            {
                                Console.WriteLine("Location successfully deleted.");
                                PrintLocations(api.GetAllLocations()); //confirmation of success
                            }
                            else
                            {
                                Console.WriteLine("Reservation not deleted.");
                            }
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine(ex.Message);
                        }
                    }
                }
                else if (menuSelection == 0)
                {
                    Environment.Exit(0);
                }
                else
                {
                    Console.WriteLine("Invalid selection.");
                }
            }
        }

        private static void PrintLocations(List<Location> locations)
        {
            Console.WriteLine("");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Locations");
            Console.WriteLine("--------------------------------------------");
            foreach (Location location in locations)
            {
                Console.WriteLine(location.Id + ": " + location.Name);
            }
            Console.WriteLine("");
        }

        private static void PrintLocation(Location location)
        {
            Console.WriteLine("");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Location Details");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Id: " + location.Id);
            Console.WriteLine("Name: " + location.Name);
            Console.WriteLine("Address: " + location.Address);
            Console.WriteLine("City: " + location.City);
            Console.WriteLine("State: " + location.State);
            Console.WriteLine("Zip: " + location.Zip);
        }

        private static string PromptForLocationData(Location location = null)
        {
            string locationString;
            Console.WriteLine("");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Enter location data as a comma separated list containing:");
            Console.WriteLine("name, address, city, state, zip");
            if (location != null)
            {
                Console.WriteLine("Location " + location.Id + " Data: " + location.Name + ", " + location.Address + ", " + location.City + ", " + location.State + ", " + location.Zip);
            }
            else
            {
                Console.WriteLine("Example: Memphis Bell, 123 Bell Street, Memphis, TN, 11111");
            }
            Console.WriteLine("--------------------------------------------");

            locationString = Console.ReadLine();
            if (location != null)
            {
                locationString += "," + location.Id;
            }
            return locationString;
        }
    }
}
