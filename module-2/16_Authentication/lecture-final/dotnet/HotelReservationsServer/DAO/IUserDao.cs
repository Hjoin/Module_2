using HotelReservations.Models;

namespace HotelReservations.DAO
{
    public interface IUserDao
    {
        User GetUser(string username);
    }
}