using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Locations.DAO;
using Locations.Models;

namespace Locations.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class LocationsController : ControllerBase
    {
        private readonly ILocationDao dao;

        public LocationsController(ILocationDao locationsDao)
        {
            dao = locationsDao;
        }

        [HttpGet]
        public List<Location> List()
        {
            return dao.List();
        }

        [HttpGet("{id}")]
        public ActionResult<Location> Get(int id)
        {
            Location location = dao.Get(id);
            if (location != null)
            {
                return Ok(location);
            }
            else
            {
                return NotFound("Location does not exist");
            }
        }

        [HttpPost]
        public Location Add(Location location)
        {
            if (location != null)
            {
                Location returnLocation = dao.Create(location);
                return returnLocation;
            }
            return null;
        }


    }
}
