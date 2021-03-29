using System.ComponentModel.DataAnnotations;

namespace Locations.Models
{
    public class Location
    {
        public int? Id { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string State { get; set; }
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
