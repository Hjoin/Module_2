using System;

namespace HotelReservationsClient
{
    class Reservation
    {
        public int? id { get; set; }
        public int hotelID { get; set; }
        public string FullName { get; set; }
        // Would use DateTime for dates but storing as a string to keep this example simple
        public string CheckinDate { get; set; }
        public string CheckoutDate { get; set; }
        public int Guests { get; set; }

        public Reservation()
        {
            //must have parameterless constructor to use as a type parameter (i.e., client.Get<Reservation>())
        }

        public Reservation(string csv)
        {
            string[] parsed = csv.Split(",");
            if (parsed.Length == 5 || parsed.Length == 6)
            {
                if (int.TryParse(parsed[0].Trim(), out int hotelId) && int.TryParse(parsed[4].Trim(), out int guests))
                {
                    hotelID = hotelId;
                    FullName = parsed[1].Trim();
                    CheckinDate = parsed[2].Trim();
                    CheckoutDate = parsed[3].Trim();
                    Guests = guests;
                    if (parsed.Length == 6)
                    {
                        if (int.TryParse(parsed[5].Trim(), out int reservationId))
                        {
                            id = reservationId;
                        }
                    }
                }
                else
                {
                    Console.WriteLine("Invalid Reservation. Please enter the Hotel Id, Full Name, Checkin Date, Checkout Date, and Guests.");
                }
            }
            else
            {
                Console.WriteLine("Invalid Reservation. Please enter the Hotel Id, Full Name, Checkin Date, Checkout Date, and Guests.");
            }
        }

        public bool IsValid
        {
            get
            {
                return hotelID != 0 && FullName != null && CheckinDate != null && CheckoutDate != null && Guests != 0;
            }
        }
    }
}
