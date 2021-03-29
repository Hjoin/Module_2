-- The following queries utilize the "dvdstore" database.

-- 1. All of the films that Nick Stallone has appeared in
-- (30 rows)
SELECT f.title
FROM actor AS a
INNER JOIN film_actor AS fa
ON fa.actor_id = a.actor_id
INNER JOIN film AS f
ON f.film_id = fa.film_id
WHERE a.first_name = 'NICK' AND a.last_name = 'STALLONE';

-- 2. All of the films that Rita Reynolds has appeared in
-- (20 rows)
SELECT f.title
FROM actor AS a
INNER JOIN film_actor AS fa
ON fa.actor_id = a.actor_id
INNER JOIN film AS f
ON f.film_id = fa.film_id
WHERE a.first_name = 'RITA' AND a.last_name = 'REYNOLDS';

-- 3. All of the films that Judy Dean or River Dean have appeared in
-- (46 rows)
SELECT f.title
FROM actor AS a
INNER JOIN film_actor AS fa
ON fa.actor_id = a.actor_id
INNER JOIN film AS f
ON f.film_id = fa.film_id
WHERE (a.first_name = 'JUDY' OR a.first_name = 'RIVER') AND a.last_name = 'DEAN';

-- 4. All of the the ‘Documentary’ films
-- (68 rows)
SELECT f.title
FROM film AS f
INNER JOIN film_category AS fc
ON f.film_id = fc.film_id
INNER JOIN category AS c
ON fc.category_id = c.category_id
WHERE c.name LIKE 'Documentary';

-- 5. All of the ‘Comedy’ films
-- (58 rows)
SELECT f.title
FROM film AS f
INNER JOIN film_category AS fc
ON f.film_id = fc.film_id
INNER JOIN category AS c
ON fc.category_id = c.category_id
WHERE c.name LIKE 'Comedy';

-- 6. All of the ‘Children’ films that are rated ‘G’
-- (10 rows)
SELECT f.title
FROM film AS f
INNER JOIN film_category AS fc
ON f.film_id = fc.film_id
INNER JOIN category AS c
ON fc.category_id = c.category_id
WHERE c.name LIKE 'Children' AND f.rating = 'G';

-- 7. All of the ‘Family’ films that are rated ‘G’ and are less than 2 hours in length
-- (3 rows)
SELECT f.title
FROM film AS f
INNER JOIN film_category AS fc
ON f.film_id = fc.film_id
INNER JOIN category AS c
ON fc.category_id = c.category_id
WHERE c.name LIKE 'Family' AND f.rating = 'G' AND f.length < 120; -- movie time is in minutes

-- 8. All of the films featuring actor Matthew Leigh that are rated ‘G’
-- (9 rows)
SELECT f.title
FROM film AS f
INNER JOIN film_actor AS fa
ON fa.film_id = f.film_id
INNER JOIN actor AS a
ON a.actor_id = fa.actor_id
WHERE a.first_name ILIKE 'Matthew' AND a.last_name ILIKE 'Leigh' AND f.rating = 'G';

-- 9. All of the ‘Sci-Fi’ films released in 2006
-- (61 rows)
SELECT f.title
FROM film AS f
INNER JOIN film_category AS fc
ON f.film_id = fc.film_id
INNER JOIN category AS c
ON fc.category_id = c.category_id
WHERE c.name = 'Sci-Fi' AND f.release_year = '2006';

-- 10. All of the ‘Action’ films starring Nick Stallone
-- (2 rows)
SELECT f.title, c.name AS category, a.first_name, a.last_name
FROM film f
INNER JOIN film_category AS fc 
ON f.film_id = fc.film_id
INNER JOIN category AS c 
ON fc.category_id = c.category_id
INNER JOIN film_actor AS fa 
ON fa.film_id = f.film_id
INNER JOIN actor AS a 
ON a.actor_id = fa.actor_id
WHERE c.name = 'Action' AND a.first_name = 'NICK' AND a.last_name = 'STALLONE';

-- 11. The address of all stores, including street address, city, district, and country
-- (2 rows)
SELECT a.address, c.city, a.district, co.country
FROM store AS s
INNER JOIN address AS a
ON a.address_id = s.address_id
INNER JOIN city AS c
ON c.city_id = a.city_id
INNER JOIN country AS co
ON co.country_id = c.country_id;

-- 12. A list of all stores by ID, the store’s street address, and the name of the store’s manager
-- (2 rows)
SELECT s.store_id, a.address, staff.first_name, staff.last_name
FROM store AS s
INNER JOIN staff
ON staff.staff_id = s.manager_staff_id
INNER JOIN address AS a
ON a.address_id = s.address_id;

-- 13. The first and last name of the top ten customers ranked by number of rentals
-- (#1 should be “ELEANOR HUNT�? with 46 rentals, #10 should have 39 rentals)
SELECT c.first_name, c.last_name, COUNT(c.customer_id) AS num_of_rental
FROM customer AS c
INNER JOIN payment AS p
ON p.customer_id = c.customer_id
GROUP BY c.first_name, c.last_name
ORDER BY num_of_rental DESC
LIMIT 10;

-- 14. The first and last name of the top ten customers ranked by dollars spent
-- (#1 should be “KARL SEAL�? with 221.55 spent, #10 should be “ANA BRADLEY�? with 174.66 spent)
SELECT c.first_name, c.last_name, SUM(p.amount) AS total_money_spent
FROM customer AS c
INNER JOIN payment AS p
ON p.customer_id = c.customer_id
GROUP BY c.first_name, c.last_name
ORDER BY total_money_spent DESC
LIMIT 10;

-- 15. The store ID, street address, total number of rentals, total amount of sales (i.e. payments), and average sale of each store.
-- (NOTE: Keep in mind that while a customer has only one primary store, they may rent from either store.)
-- (Store 1 has 7928 total rentals and Store 2 has 8121 total rentals)
SELECT s.store_id, address, COUNT(*), SUM(amount), AVG(amount)
FROM store AS s
INNER JOIN inventory
ON s.store_id = inventory.store_id
INNER JOIN rental
ON inventory.inventory_id = rental.inventory_id
INNER JOIN payment
ON rental.rental_id = payment.rental_id
INNER JOIN address
ON s.address_id = address.address_id
GROUP BY s.store_id, address;

-- 16. The top ten film titles by number of rentals
-- (#1 should be “BUCKET BROTHERHOOD�? with 34 rentals and #10 should have 31 rentals)
SELECT f.title, COUNT(r.*) AS rentals_count
FROM film AS f
INNER JOIN inventory AS i
ON i.film_id = f.film_id
INNER JOIN rental AS r
ON r.inventory_id = i.inventory_id
GROUP BY f.title
ORDER BY rentals_count DESC
LIMIT 10;

-- 17. The top five film categories by number of rentals
-- (#1 should be “Sports�? with 1179 rentals and #5 should be “Family�? with 1096 rentals)
SELECT c.name, COUNT(r.*) AS rentals_count
FROM film AS f
INNER JOIN inventory AS i
ON i.film_id = f.film_id
INNER JOIN rental AS r
ON r.inventory_id = i.inventory_id
INNER JOIN film_category AS fc
ON fc.film_id = f.film_id
INNER JOIN category AS c
ON c.category_id = fc.category_id
GROUP BY c.name
ORDER BY rentals_count DESC
LIMIT 5;


-- 18. The top five Action film titles by number of rentals
-- (#1 should have 30 rentals and #5 should have 28 rentals)
SELECT f.title, COUNT(r.*) AS rentals_count
FROM film AS f
INNER JOIN inventory AS i
ON i.film_id = f.film_id
INNER JOIN rental AS r
ON r.inventory_id = i.inventory_id
INNER JOIN film_category AS fc
ON fc.film_id = f.film_id
INNER JOIN category AS c
ON c.category_id = fc.category_id
WHERE c.name = 'Action'
GROUP BY f.title
ORDER BY rentals_count DESC
LIMIT 5;

-- 19. The top 10 actors ranked by number of rentals of films starring that actor
-- (#1 should be “GINA DEGENERES�? with 753 rentals and #10 should be “SEAN GUINESS�? with 599 rentals)
SELECT a.first_name, a.last_name, COUNT(r.*) AS rentals_count
FROM actor AS a
INNER JOIN film_actor AS fa
ON fa.actor_id = a.actor_id

INNER JOIN film AS f
ON f.film_id = fa.film_id

INNER JOIN inventory AS i
ON i.film_id = f.film_id

INNER JOIN rental AS r
ON r.inventory_id = i.inventory_id
GROUP BY a.actor_id, a.first_name, a.last_name
ORDER BY rentals_count DESC
LIMIT 10;

-- 20. The top 5 “Comedy�? actors ranked by number of rentals of films in the “Comedy�? category starring that actor
-- (#1 should have 87 rentals and #5 should have 72 rentals)

SELECT CONCAT(actor.first_name, ' ' ,actor.last_name) AS actor, COUNT(*) AS rentals_count
FROM actor
JOIN film_actor ON actor.actor_id = film_actor.actor_id

JOIN film ON film_actor.film_id = film.film_id

JOIN film_category ON film.film_id = film_category.film_id

JOIN category ON film_category.category_id = category.category_id

JOIN inventory ON film.film_id = inventory.film_id

JOIN rental ON inventory.inventory_id = rental.inventory_id

WHERE category.name = 'Comedy'
GROUP BY actor.actor_id
ORDER BY rentals_count DESC
LIMIT 5;