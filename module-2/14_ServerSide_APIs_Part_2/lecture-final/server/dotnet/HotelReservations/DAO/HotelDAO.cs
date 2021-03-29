using System.Collections.Generic;
using HotelReservations.Models;

namespace HotelReservations.Dao
{
    public class HotelDao : IHotelDao
    {
        private static List<Hotel> Hotels { get; set; }

        public HotelDao()
        {
            InitializeHotelData();
        }

        private void InitializeHotelData()
        {
            if (Hotels == null || Hotels.Count == 0)
            {
                Hotels = new List<Hotel>
                {
                    new Hotel(1, "Aloft Cleveland", new Address("1111 W 10th St", "", "Cleveland", "OH", "44113"), 3, 48, 274, "aloft-cleveland.webp"),
                    new Hotel(2, "Hilton Cleveland Downtown", new Address("100 Lakeside Ave", "", "Cleveland", "OH", "44114"), 4, 12, 287, "hilton-cleveland.webp"),
                    new Hotel(3, "Metropolitan at the 9", new Address("2017 E 9th St", "", "Cleveland", "OH", "44115"), 4, 22, 319, "metropolitan-cleveland.webp"),
                    new Hotel(4, "The Westin Pittsburgh", new Address("1000 Penn Ave", "", "Pittsburgh", "PA", "15222"), 4, 60, 131, "westin-pittsburgh.webp"),
                    new Hotel(5, "Hilton Columbus Downtown", new Address("401 N High St", "", "Columbus", "OH", "43215"), 4, 43, 190, "hilton-columbus.webp"),
                    new Hotel(6, "The Summit A Dolce Hotel", new Address("5345 Medpace Way", "", "Cincinnati", "OH", "43215"), 4, 43, 218, "summit-cincinnati.webp"),
                    new Hotel(7, "Greektown Detroit", new Address("1200 St Antoine St", "", "Detroit", "MI", "48226"), 4, 75, 185, "greektown-detroit.webp")
                };
            }
        }

        public List<Hotel> List()
        {
            return Hotels;
        }

        public Hotel Get(int id)
        {
            foreach (var hotel in Hotels)
            {
                if (hotel.Id == id)
                {
                    return hotel;
                }
            }

            return null;
        }
    }
}
