namespace WorldGeography.Models
{
    public class Country
    {
        public string Code { get; set; }
        public string Name { get; set; }
        public string Continent { get; set; }
        public string Region { get; set; }
        public double SurfaceArea { get; set; }
        public int IndependenceYear { get; set; }
        public int Population { get; set; }
        public double? LifeExpectancy { get; set; } //<-- nullable property, it can have a value that is a double or NULL if there is no value
        public decimal? GNP { get; set; } //<-- nullable property, it can have a value that is a decimal or NULL if there is no value
        public string LocalName { get; set; }
        public string GovernmentForm { get; set; }
        public string HeadOfState { get; set; }
        public int? CapitalId { get; set; } //<-- nullable property, it can have a value that is a int or NULL if there is no value
        public string Code2 { get; set; }

        public override string ToString()
        {
            return Code.PadRight(5) + Name.PadRight(20) + Continent.PadRight(30) + SurfaceArea.ToString("N2").PadRight(10) + Population.ToString("N0").PadRight(15) + GovernmentForm.PadRight(30);
        }
    }
}
