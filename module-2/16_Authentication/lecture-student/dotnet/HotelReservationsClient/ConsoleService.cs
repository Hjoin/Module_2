using System;
using System.Collections.Generic;

namespace HotelReservationsClient
{
    class ConsoleService
    {
        //Print methods

        public void PrintHotels(List<Hotel> hotels)
        {
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Hotels");
            Console.WriteLine("--------------------------------------------");
            foreach (Hotel hotel in hotels)
            {
                Console.WriteLine(hotel.Id + ": " + hotel.Name);
            }
        }

        public void PrintHotel(Hotel hotel)
        {
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Hotel Details");
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine(" Id: " + hotel.Id);
            Console.WriteLine(" Name: " + hotel.Name);
            Console.WriteLine(" Stars: " + hotel.Stars);
            Console.WriteLine(" Rooms Available: " + hotel.RoomsAvailable);
            Console.WriteLine(" Cover Image: " + hotel.CoverImage);
        }

        public void PrintReservations(List<Reservation> reservations, int hotelId = -1)
        {
            string msg = hotelId == -1 ? "All Reservations" : "Reservations for hotel: " + hotelId;
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine(msg);
            Console.WriteLine("--------------------------------------------");
            if (reservations.Count > 0)
            {
                foreach (Reservation reservation in reservations)
                {
                    PrintReservationDetails(reservation);
                }
            }
            else
            {
                Console.WriteLine("There are no reservations for hotel: " + hotelId);
            }
        }

        private void PrintReservationDetails(Reservation reservation)
        {
            Console.WriteLine(" Id: " + reservation.id);
            Console.WriteLine(" Hotel ID: " + reservation.hotelID);
            Console.WriteLine(" Name: " + reservation.FullName);
            Console.WriteLine(" Check-in Date: " + reservation.CheckinDate);
            Console.WriteLine(" Check-out Date: " + reservation.CheckoutDate);
            Console.WriteLine(" Guests: " + reservation.Guests);
            Console.WriteLine("");
        }



        //Prompt methods

        public int PromptForHotelID(List<Hotel> hotels, string action)
        {
            PrintHotels(hotels);
            Console.WriteLine("");
            Console.Write("Please enter a hotel ID to " + action + ": ");
            if (!int.TryParse(Console.ReadLine(), out int hotelId))
            {
                Console.WriteLine("Invalid input. Only input a number.");
                return 0;
            }
            else
            {
                return hotelId;
            }
        }

        public int PromptForReservationID(List<Reservation> reservations, string action)
        {
            PrintReservations(reservations);
            Console.WriteLine("");
            Console.Write("Please enter a reservation ID to " + action + ": ");
            if (!int.TryParse(Console.ReadLine(), out int reservationId))
            {
                Console.WriteLine("Invalid input. Only input a number.");
                return 0;
            }
            else
            {
                return reservationId;
            }
        }

        public string PromptForReservationData(Reservation reservation = null)
        {
            string reservationString;
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("Enter reservation data as a comma separated list containing:");
            Console.WriteLine("Hotel ID, Full Name, Checkin Date, Checkout Date, Number of Guests");
            if (reservation != null)
            {
                PrintReservationDetails(reservation);
            }
            else
            {
                Console.WriteLine("Example: 1, John Smith, 10/10/2020, 10/14/2020, 2");
            }
            Console.WriteLine("--------------------------------------------");
            Console.WriteLine("");
            reservationString = Console.ReadLine();
            if (reservation != null && reservation.id.HasValue)
            {
                reservationString += "," + reservation.id.Value;
            }
            return reservationString;
        }
    }
}
