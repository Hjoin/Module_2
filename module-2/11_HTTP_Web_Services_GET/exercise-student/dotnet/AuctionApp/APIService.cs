using RestSharp;
using System;
using System.Collections.Generic;

namespace AuctionApp
{
    public class APIService
    {
        public const string API_URL = "http://localhost:3000/auctions";
        public IRestClient client = new RestClient();

        public List<Auction> GetAllAuctions()
        {
            throw new NotImplementedException();
        }

        public Auction GetDetailsForAuction(int auctionId)
        {
            throw new NotImplementedException();
        }

        public List<Auction> GetAuctionsSearchTitle(string searchTitle)
        {
            throw new NotImplementedException();
        }

        public List<Auction> GetAuctionsSearchPrice(double searchPrice)
        {
            throw new NotImplementedException();
        }
    }
}
