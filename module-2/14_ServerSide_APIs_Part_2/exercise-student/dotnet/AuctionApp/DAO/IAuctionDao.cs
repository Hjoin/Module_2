using System.Collections.Generic;
using AuctionApp.Models;

namespace AuctionApp.DAO
{
    public interface IAuctionDao
    {
        List<Auction> List();

        Auction Get(int id);

        Auction Create(Auction auction);

        Auction Update(int id, Auction auction);

        bool Delete(int id);

        List<Auction> SearchByTitle(string searchTerm);

        List<Auction> SearchByPrice(double maxPrice);
    }
}
