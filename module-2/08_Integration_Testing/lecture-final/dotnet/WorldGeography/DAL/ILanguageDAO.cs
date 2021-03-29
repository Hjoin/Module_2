using System.Collections.Generic;
using WorldGeography.Models;

namespace WorldGeography.DAL
{
    public interface ILanguageDAO
    {
        /// <summary>
        /// Gets all languages for a given country.
        /// </summary>
        /// <param name="countryCode">The country code to filter by.</param>
        /// <returns></returns>
        IList<Language> GetLanguages(string countryCode);

        /// <summary>
        /// Inserts a new language.
        /// </summary>
        /// <param name="newLanguage"></param>
        /// <returns></returns>
        bool AddNewLanguage(Language newLanguage);

        /// <summary>
        /// Removes an existing language.
        /// </summary>
        /// <param name="deadLanguage"></param>
        /// <returns></returns>
        bool RemoveLanguage(Language deadLanguage);
    }
}
