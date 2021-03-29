using Microsoft.VisualStudio.TestTools.UnitTesting;
using DataSecurity.Cli.Security;

namespace DataSecurity.Tests.Security
{
    [TestClass]
    public class PasswordHasherTests
    {
        private PasswordHasher _subject;

        [TestInitialize]
        public void Before()
        {
            _subject = new PasswordHasher();
        }

        [TestClass]
        public class ComputeHash : PasswordHasherTests
        {
            [TestMethod]
            public void The_hashed_password_is_48_characters_long()
            {
                byte[] salt = _subject.GenerateRandomSalt();
                string hashedPassword = _subject.ComputeHash("TESTPassword123", salt);

                Assert.AreEqual(48, hashedPassword.Length);
            }

            [TestMethod]
            public void A_password_longer_than_48_characters_is_hashed()
            {
                byte[] salt = _subject.GenerateRandomSalt();
                string hashedPassword = _subject.ComputeHash(new string('*', 49), salt);

                Assert.AreEqual(48, hashedPassword.Length);
            }

            [TestMethod]
            public void The_hash_is_calculated_the_same_for_a_given_password_and_salt()
            {
                byte[] salt = _subject.GenerateRandomSalt();
                string password = "A passphrase can sometimes be more secure...";
                string computedHash = _subject.ComputeHash(password, salt);

                string recomputedHash = _subject.ComputeHash(password, salt);

                Assert.AreEqual(computedHash, recomputedHash);
            }
        }
    }
}