using System;
using System.Collections.Generic;
using DataSecurity.Cli.Model;

namespace DataSecurity.Cli
{
    internal class UserManagerCli
    {
        private readonly IUserDao _userDao;

        public UserManagerCli(IUserDao userDao)
        {
            _userDao = userDao;
        }

        private User LoggedInUser { get; set; }

        /**
        * Add a new user to the system. Anyone can register a new account like
        * this. We will call save user on the DAO in order for it to save however
        * it needs to.
        */
        private void AddNewUser()
        {
            Console.WriteLine("Enter the following information for a new user: ");
            Console.Write("Username: ");
            string username = Console.ReadLine();

            Console.Write("Password: ");
            string password = Console.ReadLine();

            User user = _userDao.SaveUser(username, password);
            Console.WriteLine($"User {user.Username} added with id {user.Id}!");
            Console.WriteLine();
        }

        private string AskPrompt()
        {
            // Get the username for the logged in user unless LoggedInUser
            // is null, or the Username is null
            string name = LoggedInUser?.Username ??
                "Unauthenticated User";

            Console.WriteLine($"Welcome {name}!");
            Console.Write("What would you like to do today? ");

            return Console.ReadLine();
        }

        /**
        * Take a username and password from the user and check it against
        * the DAO. We don't know what's wrong about the log in, just that it
        * failed. We don't want to give an attacker any information about
        * what they got right or what they got wrong when trying this. Information
        * like that is gold to an attacker because then they know what they're
        * getting right and what they're getting wrong.
        */
        private void LoginUser()
        {
            Console.WriteLine("Log into the system");
            Console.Write("Username: ");

            String username = Console.ReadLine();
            Console.Write("Password: ");

            String password = Console.ReadLine();

            if (_userDao.IsUsernameAndPasswordValid(username, password))
            {
                LoggedInUser = new User();
                LoggedInUser.Username = username;
                Console.WriteLine($"Welcome {username}!");
            }
            else
            {
                Console.WriteLine("That log in is not valid, please try again.");
            }

            Console.WriteLine();
        }

        private void PrintGreeting()
        {
            Console.WriteLine("Welcome to the User Manager Application!");
            Console.WriteLine();
        }

        private void PrintMenu()
        {
            Console.WriteLine("(A)dd a new User");
            Console.WriteLine("(S)how all users");
            Console.WriteLine("(L)og in");
            Console.WriteLine("(Q)uit");
            Console.WriteLine();
        }

        /**
        * The main run loop.
        */
        public void Run()
        {
            PrintGreeting();

            while (true)
            {
                PrintMenu();
                string option = AskPrompt().ToLower();

                if (option == "a")
                {
                    AddNewUser();
                }
                else if (option == "s")
                {
                    ShowUsers();
                }
                else if (option == "l")
                {
                    LoginUser();
                }
                else if (option == "q")
                {
                    Console.WriteLine("Thanks for using the User Manager!");
                    break;
                }
                else
                {
                    Console.WriteLine($"{option} is not a valid option. Try again!");
                }
            }
        }

        /**
        * Show all the users that are in the database. We can't show passwords
        * because we don't have them! Passwords in the database are hashed and
        * you can see that by opening SQL Server Management Studio and looking
        * at what is stored in the `users` table.
        *
        * We only allow access to this to logged in users. If a user isn't logged
        * in, we give them a message and leave. Having an `if` statement like this
        * at the top of the method is a common way of handling authorization checks.
        */
        private void ShowUsers()
        {
            if (LoggedInUser == null)
            {
                Console.WriteLine("Sorry. Only logged in users can see other users.");
                Console.WriteLine("Hit enter to continue...");

                Console.Read();
                return;
            }

            IList<User> users = _userDao.GetAllUsers();
            Console.WriteLine("Users currently in the system are: ");
            foreach (User user in users)
            {
                Console.WriteLine($"{user.Id}. {user.Username}");
            }

            Console.WriteLine();
            Console.WriteLine("Hit enter to continue...");
            Console.Read();
            Console.WriteLine();
        }
    }
}
