using System.Collections.Generic;

namespace DataSecurity.Cli.Model
{
    internal interface IUserDao
    {
        /// <summary>
        /// Look for a user with the given username and password. Since we don't
        /// know the password, we will have to get the user's salt from the database,
        /// hash the password, and compare that against the hash in the database.
        /// </summary>
        /// <returns>true if the user is found and their password matches.</returns>
        /// <param name="username">The username of the user we are checking.</param>
        /// <param name="password">The password of the user we are checking.</param>
        bool IsUsernameAndPasswordValid(string username, string password);

        /// <summary>
        /// Save a new user to the database. The password that is passed in will be
        /// salted and hashed before being saved. The original password is never
        /// stored in the system. We will never have any idea what it is!
        /// </summary>
        /// <returns>The new user.</returns>
        /// <param name="username">The username to give the new user.</param>
        /// <param name="password">The user's password.</param>
        User SaveUser(string username, string password);

        /// <summary>Get all of the users from the database.</summary>
        /// <returns>A List of user objects.</returns>
        IList<User> GetAllUsers();
    }
}