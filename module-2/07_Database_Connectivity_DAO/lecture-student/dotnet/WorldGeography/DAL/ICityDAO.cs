using System.Collections.Generic;
using WorldGeography.Models;

namespace WorldGeography.DAL
{
    public interface ICityDAO
    {
        /// <summary>
        /// Gets all cities provided a country code.
        /// </summary>
        /// <param name="countryCode">The country code to search for.</param>
        /// <returns></returns>
        IList<City> GetCitiesByCountryCode(string countryCode);

        /// <summary>
        /// Adds a new city.
        /// </summary>
        /// <param name="city">The city to add.</param>
        void AddCity(City city);
    }
}
