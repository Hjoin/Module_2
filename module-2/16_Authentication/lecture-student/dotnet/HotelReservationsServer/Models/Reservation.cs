using System;
using System.ComponentModel.DataAnnotations;

namespace HotelReservations.Models
{
    public class Reservation
    {
        public int? Id { get; set; }

        [Required(ErrorMessage = "The field `HotelID` is required.")]
        public int HotelID { get; set; }
        [Required(ErrorMessage = "The field `FullName` is required.")]
        public string FullName { get; set; }
        [Required(ErrorMessage = "The field `CheckinDate` is required.")]
        public string CheckinDate { get; set; }
        [Required(ErrorMessage = "The field `CheckoutDate` is required.")]
        public string CheckoutDate { get; set; }
        [Range(1,5, ErrorMessage = "The minimum number of guests is 1 and the maximum number is 5.")]
        public int Guests { get; set; }

        public Reservation()
        {
            //must have parameterless constructor to use as a type parameter (i.e., client.Get<Reservation>())
        }

        public Reservation(int? id, int hotelId, string fullName, string checkinDate, string checkoutDate, int guests)
        {
            Id = id ?? new Random().Next(100, int.MaxValue);
            HotelID = hotelId;
            FullName = fullName;
            CheckinDate = checkinDate;
            CheckoutDate = checkoutDate;
            Guests = guests;
        }
    }
}
