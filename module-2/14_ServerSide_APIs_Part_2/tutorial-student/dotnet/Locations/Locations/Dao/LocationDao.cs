using System.Collections.Generic;
using Locations.Models;

namespace Locations.DAO
{
    public class LocationDao : ILocationDao
    {
        private static List<Location> Locations { get; set; }

        public LocationDao()
        {
            InitializeLocationData();
        }

        private void InitializeLocationData()
        {
            if (Locations == null || Locations.Count == 0)
            {
                Locations = new List<Location>
                {
                    new Location(1,"Tech Elevator Cleveland","7100 Euclid Ave #14","Cleveland","OH","44103"),
                    new Location(2,"Tech Elevator Columbus","1275 Kinnear Rd #121","Columbus","OH","43212"),
                    new Location(3,"Tech Elevator Cincinnati","1776 Mentor Ave Suite 355","Cincinnati","OH","45212"),
                    new Location(4,"Tech Elevator Pittsburgh","901 Pennsylvania Ave #3","Pittsburgh","PA","15233"),
                    new Location(5,"Tech Elevator Detroit","440 Burroughs St #316","Detroit","MI","48202"),
                    new Location(6,"Tech Elevator Philadelphia","30 S 17th St","Philadelphia","PA","19103")
                };
            }
        }

        public List<Location> List()
        {
            return Locations;
        }

        public Location Get(int id)
        {
            foreach (var location in Locations)
            {
                if (location.Id == id)
                {
                    return location;
                }
            }

            return null;
        }

        public Location Create(Location location)
        {
            if (location.IsValid)
            {
                if (!location.Id.HasValue)
                {
                    int newId = Locations.Count + 1;
                    location.Id = newId;
                }
                Locations.Add(location);
                return location;
            }
            return null;
        }

        public Location Update(int id, Location updated)
        {
            if (updated.IsValid)
            {
                Location old = Locations.Find(a => a.Id == id);
                if (old != null)
                {
                    updated.Id = old.Id;
                    Locations.Remove(old);
                    Locations.Add(updated);
                    return updated;
                }
            }
            return null;
        }

        public bool Delete(int id)
        {
            Location location = Locations.Find(a => a.Id == id);
            if (location != null)
            {
                Locations.Remove(location);
                return true;
            }
            return false;
        }
    }
}
