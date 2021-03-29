using System;

namespace AuctionApp
{
    public class Auction
    {
        public int? Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string User { get; set; }
        public double CurrentBid { get; set; }

        public Auction()
        {
            //must have parameterless constructor to use as a type parameter (i.e., client.Get<Auction>())
        }

        public Auction(string csv)
        {
            string[] parsed = csv.Split(",");
            if (parsed.Length == 4 || parsed.Length == 5)
            {
                if (double.TryParse(parsed[3].Trim(), out double currentBid))
                {
                    Title = parsed[0].Trim();
                    Description = parsed[1].Trim();
                    User = parsed[2].Trim();
                    CurrentBid = currentBid;
                    if (parsed.Length == 5)
                    {
                        if (int.TryParse(parsed[4].Trim(), out int auctionId))
                        {
                            Id = auctionId;
                        }
                    }
                }
                else
                {
                    Console.WriteLine("Invalid Auction. Please enter the Title, Description, User, and Current Bid.");
                }
            }
            else
            {
                Console.WriteLine("Invalid Auction. Please enter the Title, Description, User, and Current Bid.");
            }
        }

        public bool IsValid
        {
            get
            {
                return Title != null && Description != null && User != null && CurrentBid != 0;
            }
        }
    }
}
