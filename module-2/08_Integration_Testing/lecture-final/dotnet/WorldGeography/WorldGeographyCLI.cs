using System;
using System.Collections.Generic;
using WorldGeography.DAL;
using WorldGeography.Models;

namespace WorldGeography
{
    public class WorldGeographyCLI
    {
        const string Command_GetCountries = "1";
        const string Command_CountriesInNorthAmerica = "2";
        const string Command_CitiesByCountryCode = "3";
        const string Command_LanguagesByCountryCode = "4";
        const string Command_AddNewLanguage = "5";
        const string Command_RemoveLanguage = "6";
        const string Command_AddCity = "7";
        const string Command_Quit = "q";

        private readonly ICityDAO cityDAO;
        private readonly ICountryDAO countryDAO;
        private readonly ILanguageDAO languageDAO;

        public WorldGeographyCLI(ICityDAO cityDAO, ICountryDAO countryDAO, ILanguageDAO languageDAO)
        {
            this.cityDAO = cityDAO;
            this.languageDAO = languageDAO;
            this.countryDAO = countryDAO;
        }

        public void RunCLI()
        {
            PrintHeader();
            PrintMenu();

            while (true)
            {
                string command = Console.ReadLine();

                Console.Clear();

                switch (command.ToLower())
                {
                    case Command_GetCountries:
                        GetCountries();
                        break;

                    case Command_CountriesInNorthAmerica:
                        GetCountriesInNorthAmerica();
                        break;

                    case Command_CitiesByCountryCode:
                        GetCitiesByCountryCode();
                        break;

                    case Command_LanguagesByCountryCode:
                        GetLanguagesForCountry();
                        break;

                    case Command_AddNewLanguage:
                        AddNewLanguage();
                        break;

                    case Command_RemoveLanguage:
                        RemoveLanguage();
                        break;

                    case Command_AddCity:
                        AddCity();
                        break;

                    case Command_Quit:
                        Console.WriteLine("Thank you for using the world geography cli app");
                        return;

                    default:
                        Console.WriteLine("The command provided was not a valid command, please try again.");
                        break;
                }

                PrintMenu();
            }
        }

        private void PrintHeader()
        {
            Console.WriteLine(@" _    _  _____ ______  _     ______     ______   ___   _____   ___  ______   ___   _____  _____ ");
            Console.WriteLine(@"| |  | ||  _  || ___ \| |    |  _  \    |  _  \ / _ \ |_   _| / _ \ | ___ \ / _ \ /  ___||  ___|");
            Console.WriteLine(@"| |  | || | | || |_/ /| |    | | | |    | | | |/ /_\ \  | |  / /_\ \| |_/ // /_\ \\ `--. | |__  ");
            Console.WriteLine(@"| |/\| || | | ||    / | |    | | | |    | | | ||  _  |  | |  |  _  || ___ \|  _  | `--. \|  __| ");
            Console.WriteLine(@"\  /\  /\ \_/ /| |\ \ | |____| |/ /     | |/ / | | | |  | |  | | | || |_/ /| | | |/\__/ /| |___ ");
            Console.WriteLine(@" \/  \/  \___/ \_| \_|\_____/|___/      |___/  \_| |_/  \_/  \_| |_/\____/ \_| |_/\____/ \____/ ");
        }

        private void PrintMenu()
        {
            Console.WriteLine();
            Console.WriteLine();
            Console.WriteLine("Main-Menu Type in a command");
            Console.WriteLine(" 1 - Get all of the countries");
            Console.WriteLine(" 2 - Get a list of the countries in North America");
            Console.WriteLine(" 3 - Get a list of the cities by country code");
            Console.WriteLine(" 4 - Get a list of the languages by country code");
            Console.WriteLine(" 5 - Add a new language");
            Console.WriteLine(" 6 - Remove language");
            Console.WriteLine(" 7 - Add a city");
            Console.WriteLine(" Q - Quit");
        }

        private void AddCity()
        {
            string name = CLIHelper.GetString("Name of the city:");
            string code = CLIHelper.GetString("Country code:");
            string district = CLIHelper.GetString($"District {name} is in:");
            int population = CLIHelper.GetInteger($"Population of {name}:");

            City city = new City
            {
                CountryCode = code,
                Name = name,
                District = district,
                Population = population
            };

            cityDAO.AddCity(city);

            Console.WriteLine("City added.");
        }

        private void GetCountries()
        {
            IList<Country> countries = countryDAO.GetCountries();

            Console.WriteLine();
            Console.WriteLine("Printing all of the countries");

            for (int index = 0; index < countries.Count; index++)
            {
                Console.WriteLine(index + " - " + countries[index]);
            }
        }

        private void GetCountriesInNorthAmerica()
        {
            string continent = CLIHelper.GetString("Continent to filter by:");

            IList<Country> northAmericanCountries = countryDAO.GetCountries(continent);

            Console.WriteLine();
            Console.WriteLine("All North American Countries");

            foreach (var country in northAmericanCountries)
            {
                Console.WriteLine(country);
            }
        }

        private void GetCitiesByCountryCode()
        {
            string countryCode = CLIHelper.GetString("Enter the country code that you want to retrieve:");

            IList<City> cities = cityDAO.GetCitiesByCountryCode(countryCode);

            Console.WriteLine();
            Console.WriteLine($"Printing {cities.Count} cities for {countryCode}");

            foreach (var city in cities)
            {
                Console.WriteLine(city);
            }
        }

        private void AddNewLanguage()
        {
            string countryCode = CLIHelper.GetString("Enter the country code the language is for:");
            bool officialOnly = CLIHelper.GetBool("Is it official only? True/False ");
            int percentage = CLIHelper.GetInteger("What percentage is it spoken by?");
            string name = CLIHelper.GetString("What is the name of the lanaguage?");

            Language lang = new Language
            {
                CountryCode = countryCode,
                IsOfficial = officialOnly,
                Percentage = percentage,
                Name = name
            };

            bool result = languageDAO.AddNewLanguage(lang);

            if (result)
            {
                Console.WriteLine("Success!");
            }
            else
            {
                Console.WriteLine("The new language was not inserted");
            }
        }

        private void RemoveLanguage()
        {
            string language = CLIHelper.GetString("Which language should be removed:");
            string countryCode = CLIHelper.GetString("For which country code:");

            Language lang = new Language
            {
                CountryCode = countryCode,
                Name = language
            };

            bool result = languageDAO.RemoveLanguage(lang);

            if (result)
            {
                Console.WriteLine("Success!");
            }
            else
            {
                Console.WriteLine("The language was not found or removed.");
            }
        }

        private void GetLanguagesForCountry()
        {
            string countryCode = CLIHelper.GetString("Enter the country code you want to retrieve:");

            IList<Language> languages = languageDAO.GetLanguages(countryCode);

            Console.WriteLine();
            Console.WriteLine($"Printing {languages.Count} languages for {countryCode}");

            foreach (var language in languages)
            {
                Console.WriteLine(language);
            }
        }
    }
}
