using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using DataSecurity.Cli.Security;

namespace DataSecurity.Cli.Model
{
    public class AdoUserDao : IUserDao
    {
        private readonly string _connectionString;
        private readonly IPasswordHasher _passwordHasher;

        /// <summary>
        /// Create a new user dao with the supplied data source and the password hasher
        /// that will salt and hash all the passwords for users.
        /// </summary>
        /// <param name="connectionString">database connection string</param>
        /// <param name="passwordHasher">an object to salt and hash passwords</param>
        public AdoUserDao(string connectionString, IPasswordHasher passwordHasher)
        {
            _connectionString = connectionString;
            _passwordHasher = passwordHasher;
        }

        public IList<User> GetAllUsers()
        {
            IList<User> users = new List<User>();

            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();

                SqlCommand command = connection.CreateCommand();
                command.CommandText = "SELECT id, username FROM users";

                SqlDataReader reader = command.ExecuteReader();
                while (reader.Read())
                {
                    users.Add(new User
                    {
                        Id = (int)reader["id"],
                        Username = (string)reader["username"]
                    });
                }
            }

            return users;
        }

        public bool IsUsernameAndPasswordValid(string username, string password)
        {
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();

                SqlCommand command = connection.CreateCommand();
                command.CommandText = "SELECT password, salt FROM users WHERE username = '" + username + "'";

                SqlDataReader reader = command.ExecuteReader();

                if (reader.Read())
                {
                    string storedPassword = (string)reader["password"];
                    string storedSalt = (string)reader["salt"];
                    string computedHash = _passwordHasher.ComputeHash(password, Convert.FromBase64String(storedSalt));

                    return computedHash.Equals(storedPassword);
                }

                return false;
            }
        }

        public User SaveUser(string username, string password)
        {
            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                byte[] salt = _passwordHasher.GenerateRandomSalt();
                string hashedPassword = _passwordHasher.ComputeHash(password, salt);

                connection.Open();

                SqlCommand command = connection.CreateCommand();
                command.CommandText = @"INSERT INTO users (username, password, salt)
                                        VALUES (@username, @password, @salt);
                                        SELECT SCOPE_IDENTITY();";

                command.Parameters.AddWithValue("@username", username);
                command.Parameters.AddWithValue("@password", hashedPassword);
                command.Parameters.AddWithValue("@salt", Convert.ToBase64String(salt));

                int id = Convert.ToInt32(command.ExecuteScalar());

                return new User
                {
                    Id = id,
                    Username = username
                };
            }
        }
    }
}