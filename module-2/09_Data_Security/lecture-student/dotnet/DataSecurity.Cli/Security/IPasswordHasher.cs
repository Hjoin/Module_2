namespace DataSecurity.Cli.Security
{
    public interface IPasswordHasher
    {
        /// <summary>
        /// Given a clear text password and a salt, hash the password and return
        /// the computed hash.
        /// </summary>
        /// <returns>the hashed password</returns>
        /// <param name="clearTextPassword">the password as given by the user</param>
        /// <param name="salt">a salt to add to the password during hashing</param>
        string ComputeHash(string clearTextPassword, byte[] salt);

        /// <summary>Generate a new random salt.</summary>
        /// <returns>a new random array of bytes to be used as a salt</returns>
        byte[] GenerateRandomSalt();
    }
}