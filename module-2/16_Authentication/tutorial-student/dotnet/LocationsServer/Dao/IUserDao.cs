using Locations.Models;

namespace Locations.DAO
{
    public interface IUserDao
    {
        User GetUser(string username);
    }
}