--M2 D2 Review

/*
SELECT
FROM
WHERE 
GROUP BY
HAVING 
ORDER BY
*/
--USA states that have more than one city in the database
SELECT  COUNT(*),district
FROM city
WHERE countrycode = 'USA'
GROUP BY district
HAVING COUNT(*) > 1
ORDER BY district;

--what state in the US has the most cities int he database
SELECT  COUNT(*),district
FROM city
WHERE countrycode = 'USA'
GROUP BY district
HAVING COUNT(*) > 1
ORDER BY COUNT(*) DESC
LIMIT 1;

--round(double precision, integer) doesn't exist. you can cast first
SELECT ROUND(CAST (ABS(population/surfacearea)  AS DEC(10,2)) ,2)
FROM country
LIMIT 50;


-- ********* INNER JOIN ***********

-- Let's find out who made payment 16666:
SELECT * 
FROM payment
WHERE payment_id = 16666;

-- Ok, that gives us a customer_id, but not the name. We can use the customer_id to get the name FROM the customer table
SELECT * 
FROM payment
INNER JOIN customer ON payment.customer_id = customer.customer_id
WHERE payment_id = 16666;

-- We can see that the * pulls back everything from both tables. We just want everything from payment and then the first and last name of the customer:
SELECT payment.*, customer.first_name, customer.last_name, customer.customer_id
FROM payment
INNER JOIN customer ON customer.customer_id = payment.customer_id
WHERE payment_id = 16666;

-- But when did they return the rental? Where would that data come from? From the rental table, so let’s join that.
SELECT payment.*, customer.first_name, customer.last_name, customer.customer_id, rental.return_date
FROM payment
INNER JOIN customer ON customer.customer_id = payment.customer_id
--INNER JOIN rental ON rental.customer_id = customer.customer_id This gives me ALL of Rosemary's rentals. I don't want that
INNER JOIN rental ON rental.rental_id = payment.rental_id
WHERE payment_id = 16666;


-- What did they rent? Film id can be gotten through inventory.
SELECT payment.*, customer.first_name, customer.last_name, customer.customer_id, rental.return_date, film.title, film.film_id
FROM payment
INNER JOIN customer ON customer.customer_id = payment.customer_id
--INNER JOIN rental ON rental.customer_id = customer.customer_id This gives me ALL of Rosemary's rentals. I don't want that
INNER JOIN rental ON rental.rental_id = payment.rental_id
INNER JOIN inventory ON inventory.inventory_id = rental.inventory_id
INNER JOIN film ON film.film_id = inventory.film_id
WHERE payment_id = 16666;


-- What if we wanted to know who acted in that film?
SELECT film.title, ARRAY_AGG(actor.first_name||' '||actor.last_name) as actors
FROM film
INNER JOIN film_actor ON film_actor.film_id = film.film_id
INNER JOIN actor ON actor.actor_id = film_actor.actor_id
WHERE film.film_id = 948
GROUP BY film.title;

-- What if we wanted a list of all the films and their categories ordered by film title
SELECT film.title, category.name
FROM film
INNER JOIN film_category ON film.film_id = film_category.film_id
INNER JOIN category ON category.category_id = film_category.category_id
ORDER BY film.title

-- Show all the 'Comedy' films ordered by film title
SELECT film.title, category.name
FROM film
INNER JOIN film_category ON film.film_id = film_category.film_id
INNER JOIN category ON category.category_id = film_category.category_id
WHERE category.name = 'Comedy'
ORDER BY film.title

-- Finally, let's count the number of films under each category
SELECT film_category.category_id, category.name, COUNT(*)
FROM film_category
INNER JOIN category ON category.category_id = film_category.category_id
GROUP BY film_category.category_id, category.name

-- ********* LEFT JOIN ***********

-- (There aren't any great examples of left joins in the "dvdstore" database, so the following queries are for the "world" database)

-- A Left join, selects all records from the "left" table and matches them with records from the "right" table if a matching record exists.

-- Let's display a list of all countries and their capitals, if they have some.
SELECT country.name, city.name
FROM country
INNER JOIN city ON country.capital = city.id;

-- Only 232 rows
-- But we’re missing entries:

-- There are 239 countries. So how do we show them all even if they don’t have a capital?
-- That’s because if the rows don’t exist in both tables, we won’t show any information for it. If we want to show data FROM the left side table everytime, we can use a different join:
SELECT country.name, city.name
FROM country
LEFT JOIN city ON country.capital = city.id
ORDER BY city.name;


-- *********** UNION *************

-- Back to the "dvdstore" database...

-- Gathers a list of all first names used by actors and customers
-- By default removes duplicates
SELECT first_name --200
FROM actor
UNION
SELECT first_name --599
FROM customer;

-- Gather the list, but this time note the source table with 'A' for actor and 'C' for customer
SELECT first_name, 'A' --200
FROM actor
UNION
SELECT first_name, 'C' --599
FROM customer;

SELECT 'HERE' ;

--Let's use a CTE to make this problem better

-- What did they rent? Film id can be gotten through inventory.
WITH find_the_film AS (
        SELECT payment.*, customer.first_name, customer.last_name, customer.customer_id, rental.return_date, film.title, film.film_id
        FROM payment
        INNER JOIN customer ON customer.customer_id = payment.customer_id
        --INNER JOIN rental ON rental.customer_id = customer.customer_id This gives me ALL of Rosemary's rentals. I don't want that
        INNER JOIN rental ON rental.rental_id = payment.rental_id
        INNER JOIN inventory ON inventory.inventory_id = rental.inventory_id
        INNER JOIN film ON film.film_id = inventory.film_id
        WHERE payment_id = 16666
)


-- What if we wanted to know who acted in that film? Let's also alias the tables
SELECT f.title, ARRAY_AGG(a.first_name||' '||a.last_name) as actors
FROM film f
INNER JOIN film_actor fa ON fa.film_id = f.film_id
INNER JOIN actor a ON a.actor_id = fa.actor_id
INNER JOIN find_the_film ftf ON f.film_id = ftf.film_id
GROUP BY f.title;