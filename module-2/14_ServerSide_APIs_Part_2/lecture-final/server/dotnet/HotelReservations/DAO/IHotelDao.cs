using System.Collections.Generic;
using HotelReservations.Models;

namespace HotelReservations.Dao
{
    public interface IHotelDao
    {
        List<Hotel> List();

        Hotel Get(int id);
    }
}
