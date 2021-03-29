using System.Collections.Generic;
using AuctionApp.Models;

namespace AuctionApp.DAO
{
    public interface IAuctionDao
    {
        List<Auction> List();

        Auction Get(int id);

        Auction Create(Auction auction);

        List<Auction> SearchByTitle(string searchTerm);

        List<Auction> SearchByPrice(double maxPrice);

        List<Auction> SearchByTitleAndPrice(string searchTerm, double maxPrice);
    }
}
