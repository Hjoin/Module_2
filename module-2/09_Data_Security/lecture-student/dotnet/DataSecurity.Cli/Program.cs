using DataSecurity.Cli.Model;
using DataSecurity.Cli.Security;
using Microsoft.Extensions.Configuration;

namespace DataSecurity.Cli
{
    public class Program
    {
        static void Main(string[] args)
        {
            IConfiguration config = new ConfigurationBuilder()
                .AddJsonFile("appsettings.json")
                .Build();

            string connectionString = config.GetConnectionString("UserManagerConnection");

            IPasswordHasher passwordHasher = new PasswordHasher();
            IUserDao userDao = new AdoUserDao(connectionString, passwordHasher);

            UserManagerCli application = new UserManagerCli(userDao);

            application.Run();
        }
    }
}
