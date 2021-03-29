using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using System.Data.SqlClient;
using WorldGeography.DAL;
using WorldGeography.Models;
using WorldGeography.Tests.DAL;

namespace WorldGeography.Tests
{
    [TestClass]
    public class CitySqlDAOTests : WorldDAOTests
    {
        [TestMethod]
        [DataRow("USA", 1)]
        [DataRow("FRA", 0)]
        public void GetCitiesByCountryCode_Should_ReturnRightNumberOfCities(string countryCode, int expectedCityCount)
        {
            // Arrange
            CitySqlDAO dao = new CitySqlDAO(ConnectionString);

            // Act
            IList<City> cities = dao.GetCitiesByCountryCode(countryCode);

            // Assert
            Assert.AreEqual(expectedCityCount, cities.Count);
        }

        [TestMethod]
        public void AddCity_Should_IncreaseCountBy1()
        {
            // Arrange
            City city = new City();
            city.CountryCode = "USA";
            city.Name = "Doesn't matter";
            city.Population = 1;
            city.District = "Doesn't matter";
            CitySqlDAO dao = new CitySqlDAO(ConnectionString);
            int startingRowCount = GetRowCount("city");

            // Act
            dao.AddCity(city);

            // Assert
            int endingRowCount = GetRowCount("city");
            Assert.AreNotEqual(startingRowCount, endingRowCount);
        }

        [TestMethod]
        [ExpectedException(typeof(SqlException))]
        public void AddCity_Should_Fail_IfCountryDoesNotExist()
        {
            // Arrange
            City city = new City();
            city.CountryCode = "XYZ";
            city.Name = "Doesn't matter";
            city.Population = 1;
            city.District = "Doesn't matter";
            CitySqlDAO dao = new CitySqlDAO(ConnectionString);

            // Act
            dao.AddCity(city);

            // Assert
            // SqlException is expected to be thrown
        }
    }
}
