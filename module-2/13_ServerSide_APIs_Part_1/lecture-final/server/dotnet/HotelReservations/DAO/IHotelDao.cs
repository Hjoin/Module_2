using System.Collections.Generic;
using HotelReservations.Models;

namespace HotelReservations.Dao
{
    interface IHotelDao
    {
        List<Hotel> List();

        Hotel Get(int id);
    }
}
