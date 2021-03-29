using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using AuctionApp.Models;
using Microsoft.AspNetCore.TestHost;
using Microsoft.AspNetCore.Hosting;
using System.Net.Http;
using Newtonsoft.Json;
using System.Threading.Tasks;

namespace AuctionApp.Tests
{
    [TestClass]
    public class AuctionsControllerTests
    {
        protected HttpClient _client;

        [TestInitialize]
        public void Setup()
        {
            var builder = new WebHostBuilder().UseStartup<AuctionApp.Startup>();
            var server = new TestServer(builder);
            _client = server.CreateClient();
        }


        [TestMethod]
        public async Task GetAuctions_ExpectList()
        {
            var response = await _client.GetAsync("auctions");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsTrue(!string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count > 0);
        }

        [TestMethod]
        public async Task GetAuction_SpecificAuction_ExpectAuction()
        {
            var response = await _client.GetAsync("auctions/1");

            string responseContent = await response.Content.ReadAsStringAsync();
            Auction content = JsonConvert.DeserializeObject<Auction>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsTrue(!string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNotNull(content);
        }

        [TestMethod]
        public async Task GetAuction_NonExistentAuction_ExpectEmpty()
        {
            var response = await _client.GetAsync("auctions/23");

            string responseContent = await response.Content.ReadAsStringAsync();
            Auction content = JsonConvert.DeserializeObject<Auction>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode); //comes back as 204 in this implementation
            Assert.IsTrue(string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNull(content);
        }

        [TestMethod]
        public async Task CreateAuction_ExpectAuction()
        {
            Auction input = new Auction() { Id = null, Title = "Dragon Plush", Description = "Not a real dragon", User = "Bernice", CurrentBid = 219.50 };

            var response = await _client.PostAsJsonAsync("auctions", input);

            string responseContent = await response.Content.ReadAsStringAsync();
            Auction content = JsonConvert.DeserializeObject<Auction>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsTrue(!string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Id.HasValue);
        }

        [TestMethod]
        public async Task SearchByTitle_ExpectList()
        {
            var response = await _client.GetAsync("auctions?title_like=watch");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsTrue(!string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count == 2);
        }

        [TestMethod]
        public async Task SearchByTitle_ExpectNone()
        {
            string gibberish = "aergergvdasc";

            var response = await _client.GetAsync($"auctions?title_like={gibberish}");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count == 0);
        }

        [TestMethod]
        public async Task SearchByPrice_ExpectList()
        {
            var response = await _client.GetAsync("auctions?currentBid_lte=200");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsTrue(!string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count == 4);
        }

        [TestMethod]
        public async Task SearchByPrice_ExpectNone()
        {
            var response = await _client.GetAsync("auctions?currentBid_lte=0.01");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count == 0);
        }

        [TestMethod]
        public async Task SearchByTitleAndPrice_ExpectList()
        {
            var response = await _client.GetAsync("auctions?title_like=watch&currentBid_lte=200");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsTrue(!string.IsNullOrWhiteSpace(responseContent));
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count == 1);
        }

        [TestMethod]
        public async Task SearchByTitleAndPrice_ExpectNone()
        {
            var response = await _client.GetAsync($"auctions?title_like=watch&currentBid_lte=0.01");

            string responseContent = await response.Content.ReadAsStringAsync();
            List<Auction> content = JsonConvert.DeserializeObject<List<Auction>>(responseContent);

            Assert.IsTrue(response.IsSuccessStatusCode);
            Assert.IsNotNull(content);
            Assert.IsTrue(content.Count == 0);
        }
    }
}
