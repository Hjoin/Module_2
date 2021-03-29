using RestSharp;
using System;
using System.Collections.Generic;

namespace LocationApp
{
    public class APIService
    {
        const string API_URL = "http://localhost:3000/locations";
        readonly RestClient client = new RestClient();

        public List<Location> GetAllLocations()
        {
            RestRequest request = new RestRequest(API_URL);
            IRestResponse<List<Location>> response = client.Get<List<Location>>(request);
            return response.Data;
        }

        public Location GetDetailsForLocation(int locationId)
        {
            RestRequest requestOne = new RestRequest(API_URL + "/" + locationId);
            IRestResponse<Location> response = client.Get<Location>(requestOne);
            return response.Data;
        }

        public Location AddLocation(Location newLocation)
        {
            //api code here
            return null;
        }

        public Location UpdateLocation(Location locationToUpdate)
        {
            //api code here
            return null;
        }

        public bool DeleteLocation(int locationId)
        {
            //api code here
            return false;
        }
    }
}