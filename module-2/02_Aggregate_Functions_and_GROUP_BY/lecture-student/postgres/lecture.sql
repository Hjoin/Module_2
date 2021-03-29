-- REVIEW FROM INTRO M2 01
-- LIKE, ILIKE, %

-- all cities that start with letter i
SELECT name
FROM city
WHERE name LIKE 'I%';

--all cities that are two characters that start with i
SELECT name
FROM city
WHERE name LIKE 'I_';

--all cities that start with the letter i
SELECT name
FROM city
WHERE name ILIKE 'i%';

--all cities that contain the letter z
SELECT name
FROM city
WHERE name ILIKE '%z%';

--all cities that end in z
SELECT name
FROM city
WHERE name ILIKE '%z';

-- ORDERING RESULTS

-- Populations of all countries in descending order
SELECT name, population
FROM country
ORDER BY population DESC;

--Names of countries and continents in ascending order
SELECT name, continent
FROM country
ORDER BY continent ASC, name DESC;

-- LIMITING RESULTS
-- The name and average life expectancy of the countries with the 10-20 highest life expectancies.
SELECT name, lifeexpectancy --the columns in my results
FROM country --the table I'm getting data from
WHERE lifeexpectancy IS NOT NULL --the lifeexpectancy must be set - limit results based on data
ORDER BY lifeexpectancy DESC --how to sort/order results
LIMIT 10 --how many rows to return
OFFSET 10; --start at row 10 for my results

-- What is the biggest city by population?
SELECT name, population
FROM city
WHERE population IS NOT NULL
ORDER BY population DESC
LIMIT 1;

-- CONCATENATING OUTPUTS

-- The name & state of all cities in California, Oregon, or Washington.
-- "city, state", sorted by state then city
SELECT name||', '||substring(district FOR 3) AS city_state
FROM city
WHERE district IN ('California','Oregon','Washington')
ORDER BY district, name; --default is ASC

-- AGGREGATE FUNCTIONS - COUNT, SUM, AVG, MIN, MAX
-- Average Life Expectancy in the World
SELECT AVG(lifeexpectancy) AS avg_life_expectancy
FROM country
WHERE lifeexpectancy IS NOT NULL;

-- Total population of all cities in Ohio
SELECT SUM(population) AS ohio_sum_pop
FROM city
WHERE district = 'Ohio';

-- The surface area of the smallest country in the world
SELECT MIN(surfacearea)
FROM country;

SELECT surfacearea
FROM country
ORDER BY surfacearea ASC
LIMIT 1;

-- The 10 largest countries in the world (population) (surface area)
SELECT name, population
FROM country
WHERE population IS NOT NULL
ORDER BY population DESC
LIMIT 10;

SELECT name, surfacearea
FROM country
WHERE surfacearea IS NOT NULL
ORDER BY surfacearea DESC
LIMIT 10;

-- The number of countries who declared independence in 1991
SELECT name, indepyear
FROM country
WHERE indepyear = 1991;

SELECT count(*)
FROM country
WHERE indepyear = 1991;

-- GROUP BY
-- Count the number of countries where each language is spoken, ordered from most countries to least
SELECT language, COUNT(*) AS count_countries
FROM countrylanguage
GROUP BY language
ORDER BY count_countries DESC;

SELECT *
FROM countrylanguage
ORDER BY language;

-- Average life expectancy of each continent ordered from highest to lowest
SELECT continent, AVG(lifeexpectancy)
FROM country
GROUP BY continent
ORDER BY AVG(lifeexpectancy) DESC;

-- Exclude Antarctica from consideration for average life expectancy
SELECT continent, AVG(lifeexpectancy)
FROM country
WHERE lifeexpectancy IS NOT NULL
GROUP BY continent
ORDER BY AVG(lifeexpectancy) DESC;

-- Sum of the population of cities in each state in the USA ordered by state name
SELECT district, SUM(population) AS state_pop
FROM city
WHERE population IS NOT NULL AND countrycode = 'USA'
GROUP BY district
ORDER BY district;

-- The average population of cities in each state in the USA ordered by state name (Average population > 1000000) (Rounded to 2 decimal places)
SELECT district, round(AVG(population), 2) AS avg_city_pop_per_state
FROM city
WHERE population IS NOT NULL AND countrycode = 'USA'
GROUP BY district
HAVING AVG(population) > 1000000
ORDER BY district;

-- how many unique districts (in USA)
SELECT COUNT(DISTINCT district)
FROM city
WHERE countrycode = 'USA';

-- SUBQUERIES
-- Find the names of cities under a given government leader, George W Bush
SELECT name, countrycode
FROM city
WHERE countrycode IN (

        SELECT code --, name, headofstate
        FROM country
        WHERE headofstate LIKE '%Bush%');

-- Find the names of cities whose country they belong to has not declared independence yet
SELECT name, countrycode
FROM city
WHERE countrycode IN (
        SELECT code
        FROM country
        WHERE indepyear IS NULL);

-- Additional samples
-- You may alias column and table names to be more descriptive
SELECT name, countrycode AS cc
FROM city c
WHERE countrycode IN (
        SELECT code
        FROM country
        WHERE indepyear IS NULL);

-- Alias can also be used to create shorthand references, such as "c" for city and "co" for country.

-- Ordering allows columns to be displayed in ascending order, or descending order (Look at Arlington)

-- Limiting results allows rows to be returned in 'limited' clusters,where LIMIT says how many, and OFFSET (optional) specifies the number of rows to skip

-- Most database platforms provide string functions that can be useful for working with string data. In addition to string functions, string concatenation is also usually supported, which allows us to build complete sentences if necessary.

-- Aggregate functions provide the ability to COUNT, SUM, and AVG, as well as determine MIN and MAX. Only returns a single row of value(s) unless used with GROUP BY.

-- Counts the number of rows in the city table
SELECT COUNT(*) FROM city;

-- Also counts the number of rows in the city table
SELECT COUNT(name) FROM city;

-- Gets the SUM of the population field in the city table, as well as
-- the AVG population, and a COUNT of the total number of rows.
SELECT SUM(population) AS sum_pop, AVG(population) as avg_pop, COUNT(*)
FROM city;

-- Let's round this!
SELECT SUM(population) AS sum_pop, round(AVG(population),2) as avg_pop, COUNT(*) AS total_rows
FROM city;

-- Gets the MIN population and the MAX population from the city table. (for each state)
SELECT district, MIN(population) as min_pop, MAX(population) as max_pop
FROM city
GROUP BY district;

-- Using a GROUP BY with aggregate functions allows us to summarize information for a specific column. For instance, we are able to determine the MIN and MAX population for each countrycode in the city table.
SELECT countrycode, MIN(population) AS min_pop, MAX(population) AS max_pop
FROM city
GROUP BY countrycode;
