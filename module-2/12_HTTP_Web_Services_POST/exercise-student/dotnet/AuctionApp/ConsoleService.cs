using System;
using System.Collections.Generic;

namespace AuctionApp
{
    public class ConsoleService
    {

        //Print methods

        public void PrintAuctions(List<Auction> auctions)
        {
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Auctions");
            Console.WriteLine("--------------------------------------------");
            foreach (Auction auction in auctions)
            {
                Console.WriteLine($"{auction.Id} : {auction.Title} : {auction.User} : {auction.CurrentBid:C}");
            }
        }

        public void PrintAuction(Auction auction)
        {
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Auction Details");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine(" Id: " + auction.Id);
            Console.WriteLine(" Name: " + auction.Title);
            Console.WriteLine(" Description: " + auction.Description);
            Console.WriteLine(" User: " + auction.User);
            Console.WriteLine(" Current Bid: " + auction.CurrentBid.ToString("C"));
        }

        //Prompt methods

        public int PromptForAuctionID(List<Auction> auctions, string action)
        {
            PrintAuctions(auctions);
            Console.WriteLine("");
            Console.Write("Please enter an auction ID to " + action + ": ");
            if (!int.TryParse(Console.ReadLine(), out int auctionId))
            {
                Console.WriteLine("Invalid input. Only input a number.");
                return 0;
            }
            else
            {
                return auctionId;
            }
        }

        public string PromptForSearchTitle()
        {
            Console.Write("Please enter a title to search for: ");
            string searchTitle = Console.ReadLine();
            if (string.IsNullOrWhiteSpace(searchTitle))
            {
                Console.WriteLine("Invalid input. Please enter some text.");
                return null;
            }
            else
            {
                return searchTitle;
            }
        }

        public double PromptForMaxPrice()
        {
            Console.Write("Please enter a max price to search for: ");
            if (!double.TryParse(Console.ReadLine(), out double maxPrice))
            {
                Console.WriteLine("Invalid input. Only input a number.");
                return 0;
            }
            else
            {
                return maxPrice;
            }
        }

        public string PromptForAuctionData(Auction auction = null)
        {
            string auctionString;
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Enter auction data as a comma separated list containing:");
            Console.WriteLine("Title, Description, User, Current Bid");
            if (auction != null)
            {
                PrintAuction(auction);
            }
            else
            {
                Console.WriteLine("Example: Dragon Plush, Not a real dragon, Bernice, 19.50");
            }
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("");
            auctionString = Console.ReadLine();
            if (auction != null && auction.Id.HasValue)
            {
                auctionString += "," + auction.Id.Value;
            }
            return auctionString;
        }
    }
}
