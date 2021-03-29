-- SELECT ... FROM
-- Selecting the names for all countries
SELECT name
FROM country;

-- Selecting the name and population of all countries
SELECT name, population
FROM country;


-- Selecting all columns from the city table
SELECT *
FROM city;


-- SELECT ... FROM ... WHERE
-- Selecting the cities in Ohio
SELECT name, district
FROM city
WHERE district = 'Ohio';


-- Selecting countries that gained independence in the year 1776
SELECT name, indepyear
FROM country
WHERE indepyear = 1776;

-- Selecting countries that gained independence in the year 1776 OR 1901
SELECT name, indepyear
FROM country
WHERE indepyear IN (1776, 1901);

SELECT name, indepyear
FROM country
WHERE indepyear = 1776 OR indepyear = 1901;

-- Selecting countries that gained independence between 1776 and 1901
SELECT name, indepyear
FROM country
WHERE indepyear BETWEEN 1776 AND 1901;

-- Selecting countries not in Asia
SELECT name, continent
FROM country
WHERE continent != 'Asia';

-- Selecting countries that DO NOT have an independence year
SELECT name, indepyear
FROM country
WHERE indepyear IS NULL;

-- Selecting countries that DO have an independence year
SELECT name, indepyear
FROM country
WHERE indepyear IS NOT NULL;


-- Selecting countries that have a population greater than 5 million
SELECT name, population
FROM country
WHERE population > 5000000;


-- SELECT ... FROM ... WHERE ... AND/OR
-- Selecting cities in Ohio and Population greater than 400,000
SELECT name, district, population
FROM city
WHERE district = 'Ohio' AND population > 400000;

-- Selecting country names on the continent North America or South America
SELECT name, continent
FROM country
WHERE continent LIKE '%America';



-- SELECTING DATA w/arithmetic
-- Selecting the population, life expectancy, and population per area
--	note the use of the 'as' keyword
SELECT name, population, lifeexpectancy, surfacearea, population/surfacearea AS "Pop Per Area"
FROM country;

-- How many different districts are in my stuff
SELECT DISTINCT district
FROM city;
