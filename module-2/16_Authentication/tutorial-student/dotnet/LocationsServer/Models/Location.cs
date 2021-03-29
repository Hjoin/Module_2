using System.ComponentModel.DataAnnotations;

namespace Locations.Models
{
    public class Location
    {
        public int? Id { get; set; }
        [Required(ErrorMessage = "The field Name is required.")]
        public string Name { get; set; }
        [Required(ErrorMessage = "The field Address is required.")]
        public string Address { get; set; }
        [Required(ErrorMessage = "The field City is required.")]
        public string City { get; set; }
        [Required(ErrorMessage = "The field State is required.")]
        public string State { get; set; }
        [Required(ErrorMessage = "The field Zip is required.")]
        public string Zip { get; set; }

        public Location()
        {
            //must have parameterless constructor to deserialize
        }

        public Location(int? id, string name, string address, string city, string state, string zip)
        {
            Id = id;
            Name = name;
            Address = address;
            City = city;
            State = state;
            Zip = zip;
        }

        public bool IsValid
        {
            get
            {
                return Name != null && Address != null && City != null && State != null && Zip != null;
            }
        }
    }
}
