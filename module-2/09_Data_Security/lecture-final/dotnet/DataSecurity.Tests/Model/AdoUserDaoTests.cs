using System.Collections.Generic;
using System.Data.SqlClient;
using System.Transactions;
using Microsoft.Extensions.Configuration;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using DataSecurity.Cli.Model;
using DataSecurity.Cli.Security;

namespace DataSecurity.Tests.Model
{
    [TestClass]
    public class AdoUserDaoTests
    {
        private string _connectionString;
        private AdoUserDao _subject;
        private TransactionScope _transaction;

        [TestInitialize]
        public virtual void Before()
        {
            IConfiguration config = new ConfigurationBuilder()
                .AddJsonFile("appsettings.json")
                .Build();

            _connectionString = config.GetConnectionString("UserManagerConnection");

            _transaction = new TransactionScope();
            _subject = new AdoUserDao(_connectionString, new PasswordHasher());

            using (SqlConnection connection = new SqlConnection(_connectionString))
            {
                connection.Open();

                SqlCommand command = connection.CreateCommand();
                command.CommandText = @"DELETE FROM users";

                command.ExecuteNonQuery();
            }
        }

        [TestCleanup]
        public void After()
        {
            _transaction.Dispose();
        }

        [TestClass]
        public class GetAllUsers : AdoUserDaoTests
        {
            [TestMethod]
            public void It_returns_all_Users_in_the_table()
            {
                using (SqlConnection connection = new SqlConnection(_connectionString))
                {
                    connection.Open();

                    SqlCommand command = connection.CreateCommand();
                    command.CommandText = @"
                        INSERT INTO users (Username, Password, Salt) VALUES ('TestAccount1', 'Password1', 'Salt1');
                        INSERT INTO users (Username, Password, Salt) VALUES ('TestAccount2', 'Password2', 'Salt2');
                        INSERT INTO users (Username, Password, Salt) VALUES ('TestAccount3', 'Password3', 'Salt3')";

                    int results = command.ExecuteNonQuery();
                    Assert.AreEqual(3, results);
                }

                IList<User> allUsers = _subject.GetAllUsers();

                Assert.AreEqual(3, allUsers.Count);
            }

            [TestMethod]
            public void It_returns_an_empty_list_when_the_table_is_empty()
            {
                IList<User> allUsers = _subject.GetAllUsers();

                Assert.AreEqual(0, allUsers.Count);
            }
        }

        [TestClass]
        public class IsUsernameAndPasswordValid : AdoUserDaoTests
        {
            private const string USERNAME = "TESTUSER1";
            private const string PASSWORD = "TESTPASSWORD1";

            [TestInitialize]
            public override void Before()
            {
                base.Before();
                _subject.SaveUser(USERNAME, PASSWORD);
            }

            [TestMethod]
            public void It_returns_false_if_the_user_does_not_exist()
            {
                bool result = _subject.IsUsernameAndPasswordValid($"{USERNAME}-WRONG", PASSWORD);
                Assert.IsFalse(result);
            }

            [TestMethod]
            public void It_returns_false_if_the_password_is_not_correct()
            {
                bool result = _subject.IsUsernameAndPasswordValid(USERNAME, $"{PASSWORD}-WRONG");
                Assert.IsFalse(result);
            }

            [TestMethod]
            public void It_returns_true_if_the_password_is_correct_for_the_username()
            {
                bool result = _subject.IsUsernameAndPasswordValid(USERNAME, PASSWORD);
                Assert.IsTrue(result);
            }

            [TestMethod]
            public void It_returns_true_if_the_password_is_correct_regardless_of_the_case_of_the_username()
            {
                bool result = _subject.IsUsernameAndPasswordValid(USERNAME.ToLower(), PASSWORD);
                Assert.IsTrue(result);
            }
        }

        [TestClass]
        public class SaveUser : AdoUserDaoTests
        {
            [TestMethod]
            public void It_saves_the_user_to_the_table()
            {
                User user = _subject.SaveUser("TestUser1", "TestPassword1");

                using (SqlConnection connection = new SqlConnection(_connectionString))
                {
                    connection.Open();

                    SqlCommand command = connection.CreateCommand();
                    command.CommandText = "SELECT username, password FROM users WHERE Id = @Id";
                    command.Parameters.AddWithValue("@Id", user.Id);

                    SqlDataReader reader = command.ExecuteReader();

                    if (!reader.Read())
                    {
                        Assert.Fail("The user was not added to the table!");
                    }

                    Assert.AreEqual("TestUser1", (string)reader["username"]);
                }
            }

            [TestMethod]
            public void It_does_not_store_the_password_in_plain_text()
            {
                User user = _subject.SaveUser("TestUser1", "TestPassword1");

                using (SqlConnection connection = new SqlConnection(_connectionString))
                {
                    connection.Open();

                    SqlCommand command = connection.CreateCommand();
                    command.CommandText = "SELECT username, password FROM users WHERE Id = @Id";
                    command.Parameters.AddWithValue("@Id", user.Id);

                    SqlDataReader reader = command.ExecuteReader();

                    if (!reader.Read())
                    {
                        Assert.Fail("The user was not added to the table!");
                    }

                    Assert.AreEqual("TestUser1", (string)reader["username"]);
                    Assert.AreNotEqual("TestPassword1", (string)reader["password"]);
                }
            }
        }
    }
}