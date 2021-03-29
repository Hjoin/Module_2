namespace Locations
{
    public class Location
    {
        public int? Id { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string City { get; set; }
        public string State { get; set; }
        public string Zip { get; set; }

        public Location()
        {
            //must have parameterless constructor to use as a type parameter (i.e., client.Get<Location>())
        }

        public Location(string csv)
        {
            string[] parsed = csv.Split(",");
            if (parsed.Length == 5 || parsed.Length == 6) {
                Name = parsed[0].Trim();
                Address = parsed[1].Trim();
                City = parsed[2].Trim();
                State = parsed[3].Trim();
                Zip = parsed[4].Trim();
                if (parsed.Length == 6 && int.TryParse(parsed[5].Trim(), out int parsedId)) Id = parsedId;
            }
        }

        public bool IsValid
        {
            get
            {
                return Name != null && Address != null && City != null && State != null && Zip != null;
            }
        }
    }
}