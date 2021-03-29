namespace AuctionApp.Models
{
    public class Auction
    {
        public int? Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string User { get; set; }
        public double CurrentBid { get; set; }

        public bool IsValid
        {
            get
            {
                return Title != null && Description != null && User != null && CurrentBid != 0;
            }
        }
    }
}
