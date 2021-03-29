using System.Collections.Generic;
using Locations.Models;

namespace Locations.DAO
{
    public interface ILocationDao
    {
        List<Location> List();

        Location Get(int id);

        Location Create(Location location);

        Location Update(int id, Location location);

        bool Delete(int id);
    }
}
