-- Write queries to return the following:
-- The following changes are applied to the "dvdstore" database.**

-- 1. Add actors, Hampton Avenue, and Lisa Byway to the actor table.
SELECT * FROM actor
ORDER BY last_name

INSERT into actor (first_name, last_name) VALUES ('Hampton', 'Avenue');
INSERT into actor (first_name, last_name) VALUES ('Lisa', 'Byway');

-- 2. Add "Euclidean PI", "The epic story of Euclid as a pizza delivery boy in
-- ancient Greece", to the film table. The movie was released in 2008 in English.
-- Since its an epic, the run length is 3hrs and 18mins. There are no special
-- features, the film speaks for itself, and doesn't need any gimmicks.

INSERT into film (title, description, release_year, language_id, length) values ('Euclidean PI', 'The epic story of Euclid as a pizza delivery boy in ancient Greece', 2008, 
(SELECT language_id FROM language WHERE name = 'English'), 198);

SELECT * FROM film WHERE title = 'Euclidean PI';

-- 3. Hampton Avenue plays Euclid, while Lisa Byway plays his slightly
-- overprotective mother, in the film, "Euclidean PI". Add them to the film.

BEGIN TRANSACTION

SELECT * FROM actor WHERE actor_id = 202;


INSERT INTO film_actor (actor_id, film_id) VALUES (201, 1001);
INSERT INTO film_actor (actor_id, film_id) VALUES (202, 1001);

select * from film_actor WHERE film_id = 1001;

ROLLBACK;

COMMIT;

-- 4. Add Mathmagical to the category table.
BEGIN TRANSACTION 

INSERT INTO category (category_id, name) VALUES (17, 'Mathmagical')

ROLLBACK;

COMMIT;

-- 5. Assign the Mathmagical category to the following films, "Euclidean PI",
-- "EGG IGBY", "KARATE MOON", "RANDOM GO", and "YOUNG LANGUAGE"

BEGIN TRANSACTION

UPDATE film_category 
SET category_id = 17 
WHERE film_id = 1001 OR film_id = 274 OR film_id = 494 OR film_id = 714 OR film_id = 996;

ROLLBACK;
COMMIT;

-- 6. Mathmagical films always have a "G" rating, adjust all Mathmagical films
-- accordingly.
-- (5 rows affected)

BEGIN TRANSACTION

UPDATE film
SET rating = 'G'
WHERE film_id = 1001 OR film_id = 274 OR film_id = 494 OR film_id = 714 OR film_id = 996;

ROLLBACK;

COMMIT;

-- 7. Add a copy of "Euclidean PI" to all the stores.
BEGIN TRANSACTION

INSERT INTO inventory(film_id,store_id)
VALUES(1001,1), (1001,2);

ROLLBACK;

COMMIT;

--INSERT INTO SELECT

INSERT INTO inventory(film_id,store_id)
SELECT (SELECT film_id
        FROM film
        WHERE title = 'Euclidean PI'), store_id
        FROM store;
        
-- 8. The Feds have stepped in and have impounded all copies of the pirated film,
-- "Euclidean PI". The film has been seized from all stores, and needs to be
-- deleted from the film table. Delete "Euclidean PI" from the film table.
-- (Did it succeed? Why?)
-- <YOUR ANSWER HERE>

BEGIN TRANSACTION

DELETE FROM film WHERE film_id = 1001;

ROLLBACK;

COMMIT;

-- It is unsuccessful, because it violates the foreign key constraint (Euclidean PI is associated in other tables).

-- 9. Delete Mathmagical from the category table.
-- (Did it succeed? Why?)
-- <YOUR ANSWER HERE>

BEGIN TRANSACTION

DELETE FROM category
WHERE category_id = 17;

ROLLBACK

-- It is unsuccessful, because it violates the foreign key constraint (Again, similar to question #8, Mathmagical is being associated in other tables, so it cannot be deleted).

-- 10. Delete all links to Mathmagical in the film_category tale.
-- (Did it succeed? Why?)
-- <YOUR ANSWER HERE>

BEGIN TRANSACTION

DELETE FROM film_category
WHERE category_id = 17;

ROLLBACK;

COMMIT;

-- This action is successful (deleting primary).

-- 11. Retry deleting Mathmagical from the category table, followed by retrying
-- to delete "Euclidean PI".
-- (Did either deletes succeed? Why?)
-- <YOUR ANSWER HERE>

BEGIN TRANSACTION

DELETE FROM category
WHERE category_id = 17;

ROLLBACK;

COMMIT;

-- The deletes were successful, because now that the primary key has been effectively deleted, the foreign keys may be removed without issue (no violation of foreign key constraint).

-- 12. Check database metadata to determine all constraints of the film id, and
-- describe any remaining adjustments needed before the film "Euclidean PI" can
-- be removed from the film table.

-- In order to remove "Euclidean PI", the film reference from the film_category and film_actor tables would have to be deleted.
-- This has to do with the fact that film_id is a primary key for both of the tables. Once these are deleted, there should be no issues/errors when trying to remove the movie.
