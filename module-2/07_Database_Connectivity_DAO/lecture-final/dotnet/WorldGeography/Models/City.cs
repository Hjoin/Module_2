namespace WorldGeography.Models
{
    public class City
    {
        public int CityId { get; set; }
        public string Name { get; set; }
        public string District { get; set; }
        public string CountryCode { get; set; }
        public int Population { get; set; }

        public override string ToString()
        {
            return CityId.ToString().PadRight(6) + Name.PadRight(30) + District.PadRight(30) + Population.ToString("N0").PadRight(10);
        }
    }
}
