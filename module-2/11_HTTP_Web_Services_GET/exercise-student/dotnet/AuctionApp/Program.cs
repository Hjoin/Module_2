using System;
using System.Collections.Generic;

namespace AuctionApp
{
    public class Program
    {
        static void Main(string[] args)
        {
            Run();
        }
        private static void Run()
        {
            Console.WriteLine("Welcome to Online Auctions! Please make a selection:");
            MenuSelection();
        }

        private static void MenuSelection()
        {
            APIService api = new APIService();
            int menuSelection = -1;
            while (menuSelection != 0)
            {
                Console.WriteLine("");
                Console.WriteLine("Menu:");
                Console.WriteLine("1: List all auctions");
                Console.WriteLine("2: List details for specific auction");
                Console.WriteLine("3: Find auctions with a specified term in the title");
                Console.WriteLine("4: Find auctions below a specified price");
                Console.WriteLine("5: Exit");
                Console.WriteLine("---------");
                Console.Write("Please choose an option: ");

                if (!int.TryParse(Console.ReadLine(), out menuSelection))
                {
                    Console.WriteLine("Invalid input. Please enter only a number.");
                }
                else if (menuSelection == 1)
                {
                    List<Auction> auctions = api.GetAllAuctions();
                    PrintAuctions(auctions);
                }
                else if (menuSelection == 2)
                {
                    Console.Write("Please enter an auction id to get the details: ");
                    string input = Console.ReadLine();
                    if (!int.TryParse(input, out int auctionId))
                    {
                        Console.WriteLine("Invalid input. Please enter only a number.");
                    }
                    else if (auctionId > 0)
                    {
                        Auction auction = api.GetDetailsForAuction(auctionId);
                        PrintAuction(auction);
                    }
                }
                else if (menuSelection == 3)
                {
                    Console.Write("Please enter a title to search for: ");
                    string searchTitle = Console.ReadLine();
                    if (string.IsNullOrWhiteSpace(searchTitle))
                    {
                        Console.WriteLine("Invalid input. Please enter some text.");
                    }
                    else
                    {
                        List<Auction> auctions = api.GetAuctionsSearchTitle(searchTitle);
                        PrintAuctions(auctions);
                    }
                }
                else if (menuSelection == 4)
                {
                    Console.Write("Please enter a max price to search for: ");
                    string input = Console.ReadLine();
                    if (!double.TryParse(input, out double searchPrice))
                    {
                        Console.WriteLine("Invalid input. Please enter only a number.");
                    }
                    else
                    {
                        List<Auction> auctions = api.GetAuctionsSearchPrice(searchPrice);
                        PrintAuctions(auctions);
                    }
                }
                else
                {
                    Console.WriteLine("Goodbye!");
                    Environment.Exit(0);
                }
            }
        }

        private static void PrintAuctions(List<Auction> auctions)
        {
            Console.WriteLine("");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Auctions");
            Console.WriteLine("--------------------------------------------");
            foreach (Auction auction in auctions)
            {
                Console.WriteLine(auction.Id + ": " + auction.Title + " | Current Bid: " + auction.CurrentBid.ToString("C"));
            }
            Console.WriteLine("");
        }

        private static void PrintAuction(Auction auction)
        {
            Console.WriteLine("");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Auction Details");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Id: " + auction.Id);
            Console.WriteLine("Title: " + auction.Title);
            Console.WriteLine("Description: " + auction.Description);
            Console.WriteLine("User: " + auction.User);
            Console.WriteLine("Current Bid: " + auction.CurrentBid.ToString("C"));
            Console.WriteLine("");
        }
    }
}
