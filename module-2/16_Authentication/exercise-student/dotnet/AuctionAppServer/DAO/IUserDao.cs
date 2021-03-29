using AuctionApp.Models;

namespace AuctionApp.DAO
{
    public interface IUserDao
    {
        User GetUser(string username);
    }
}