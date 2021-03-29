using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using FluentAssertions;
using Moq;
using RestSharp;
using System.Net;

namespace AuctionApp.Tests
{
    [TestClass]
    public class APIServiceTests
    {
        private const string EXPECTED_API_URL = "http://localhost:3000/auctions";

        private static List<Auction> expectedAuctions = new List<Auction>
        {
            new Auction() { Id = 1, Title = "Bell Computer Monitor", Description = "4K LCD monitor from Bell Computers, HDMI & DisplayPort", User = "Queenie34", CurrentBid = 100.39 },
            new Auction() { Id = 2, Title = "Pineapple Smart Watch", Description = "Pears with Pineapple ePhone", User = "Miller.Fahey", CurrentBid = 377.44 },
            new Auction() { Id = 3, Title = "Mad-dog Sneakers", Description = "Soles check. Laces check.", User = "Cierra_Pagac", CurrentBid = 125.23 },
            new Auction() { Id = 4, Title = "Annie Sunglasses", Description = "Keep the sun from blinding you", User = "Sallie_Kerluke4", CurrentBid = 69.67 },
            new Auction() { Id = 5, Title = "Byson Vacuum", Description = "Clean your house with a spherical vacuum", User = "Lisette_Crist", CurrentBid = 287.73 },
            new Auction() { Id = 6, Title = "Fony Headphones", Description = "Listen to music, movies, games and not bother people around you!", User = "Chester67", CurrentBid = 267.38 },
            new Auction() { Id = 7, Title = "Molex Gold Watch", Description = "Definitely not fake gold watch", User = "Stuart27", CurrentBid = 188.39 }
        };
        private static Auction expectedAuction = expectedAuctions[0];

        private APIService apiService = new APIService();

        [TestMethod]
        public void GetAllAuctions_ExpectList()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == APIService.API_URL), Method.GET))
                .Returns(new RestResponse<List<Auction>>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = expectedAuctions,
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            List<Auction> actualAuctions = apiService.GetAllAuctions();

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == EXPECTED_API_URL), Method.GET), Times.Once());
            actualAuctions.Should().BeEquivalentTo(expectedAuctions);
        }

        [TestMethod]
        public void GetDetailsForAuction_ExpectSpecificItem()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<Auction>(It.Is<IRestRequest>(r => r.Resource == (APIService.API_URL + "/1")), Method.GET))
                .Returns(new RestResponse<Auction>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = expectedAuction,
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            Auction actualAuction = apiService.GetDetailsForAuction(1);

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<Auction>(It.Is<IRestRequest>(r => r.Resource == (EXPECTED_API_URL + "/1")), Method.GET), Times.Once());
            actualAuction.Should().BeEquivalentTo(expectedAuction);
        }

        [TestMethod]
        public void GetDetailsForAuction_IdNotFound()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<Auction>(It.Is<IRestRequest>(r => r.Resource == (APIService.API_URL + "/99")), Method.GET))
                .Returns(new RestResponse<Auction>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = null,
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            Auction actualAuction = apiService.GetDetailsForAuction(99);

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<Auction>(It.Is<IRestRequest>(r => r.Resource == (EXPECTED_API_URL + "/99")), Method.GET), Times.Once());
            actualAuction.Should().BeNull();
        }

        [TestMethod]
        public void GetAuctionsSearchTitle_ExpectList()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == APIService.API_URL 
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "title_like" && (string)r.Parameters[0].Value == "watch"), Method.GET))
                .Returns(new RestResponse<List<Auction>>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = new List<Auction>() { expectedAuctions[0], expectedAuctions[6] },
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            List<Auction> actualAuctions = apiService.GetAuctionsSearchTitle("watch");

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == EXPECTED_API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "title_like" && (string)r.Parameters[0].Value == "watch"), Method.GET), Times.Once());
            actualAuctions.Should().BeEquivalentTo(new List<Auction>() { expectedAuctions[0], expectedAuctions[6] });
        }

        [TestMethod]
        public void GetAuctionsSearchTitle_ExpectNone()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == APIService.API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "title_like" && (string)r.Parameters[0].Value == "nosuchtitle"), Method.GET))
                .Returns(new RestResponse<List<Auction>>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = new List<Auction>(),
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            List<Auction> actualAuctions = apiService.GetAuctionsSearchTitle("nosuchtitle");

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == EXPECTED_API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "title_like" && (string)r.Parameters[0].Value == "nosuchtitle"), Method.GET), Times.Once());
            actualAuctions.Should().BeEquivalentTo(new List<Auction>());
        }

        [TestMethod]
        public void GetAuctionsSearchPrice_ExpectList()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == APIService.API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "currentBid_lte" && (string)r.Parameters[0].Value == "1000000"), Method.GET))
                .Returns(new RestResponse<List<Auction>>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = expectedAuctions,
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            List<Auction> actualAuctions = apiService.GetAuctionsSearchPrice(1000000);

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == EXPECTED_API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "currentBid_lte" && (string)r.Parameters[0].Value == "1000000"), Method.GET), Times.Once());
            actualAuctions.Should().BeEquivalentTo(expectedAuctions);
        }

        [TestMethod]
        public void GetAuctionsSearchPrice_ExpectNone()
        {
            // Arrange
            Mock<IRestClient> restClient = new Mock<IRestClient>();
            restClient.Setup(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == APIService.API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "currentBid_lte" && (string)r.Parameters[0].Value == "0"), Method.GET))
                .Returns(new RestResponse<List<Auction>>
                {
                    StatusCode = HttpStatusCode.OK,
                    Data = new List<Auction>(),
                    ResponseStatus = ResponseStatus.Completed
                });
            apiService.client = restClient.Object;

            // Act
            List<Auction> actualAuctions = apiService.GetAuctionsSearchPrice(0.0);

            // Assert
            APIService.API_URL.Should().Be(EXPECTED_API_URL);
            restClient.Verify(x => x.Execute<List<Auction>>(It.Is<IRestRequest>(r => r.Resource == EXPECTED_API_URL
                && r.Parameters.Count > 0 && r.Parameters[0].Name == "currentBid_lte" && (string)r.Parameters[0].Value == "0"), Method.GET), Times.Once());
            actualAuctions.Should().BeEquivalentTo(new List<Auction>());
        }
    }
}
