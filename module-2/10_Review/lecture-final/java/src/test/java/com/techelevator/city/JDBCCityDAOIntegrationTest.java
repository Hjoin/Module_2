package com.techelevator.city;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCCityDAOIntegrationTest {

	private static final String TEST_COUNTRY = "XYZ";

	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	private JDBCCityDAO dao;

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/world");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		String sqlInsertCountry = "INSERT INTO country (code, name, continent, region, surfacearea, indepyear, population, lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate, capital, code2) " +
				" VALUES (?, 'Afghanistan', 'Asia', 'Southern and Central Asia', 652090, 1919, 22720000, 45.9000015, 5976.00, NULL, 'Afganistan/Afqanestan', 'Islamic Emirate', 'Mohammad Omar', 1, 'AF')";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertCountry, TEST_COUNTRY);
		dao = new JDBCCityDAO(dataSource);
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void i_need_sql_in_my_test(){
		//arrange - get the current count of cities
		String sql = "SELECT COUNT(*) as count_city FROM city;";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
		int countResults = 0;
		if (result.next()){
			countResults = result.getInt("count_city");
		}

		//act calling the method from the DAO

		//assert check to make sure expected = actual
	}

	@Test
	public void save_new_city_and_read_it_back()  {
		City theCity = makeLocalCityObject("SQL Station", "South Dakota", TEST_COUNTRY, 65535);

		dao.create(theCity);
		City savedCity = dao.findCityById(theCity.getId());

		//assertTrue(theCity.getId()!=null); //another way to write below
		assertNotEquals(null, theCity.getId());
		assertCitiesAreEqual(theCity, savedCity);

		/*sometimes our create will return a City that represents what was saved
		  so City savedCity = dao.create();
		  and then the comparison assertCitiesAreEqual(theCity, savedCity);
		  this is bad don't do it. Make sure you have teh second step of ACTUALLY READING FROM THE DATABASE
		  to ensure what you're testing is what you mean to test
		 */

	}

	@Test
	public void returns_cities_by_country_code() {
		City theCity = makeLocalCityObject("SQL Station", "South Dakota", TEST_COUNTRY, 65535);

		dao.create(theCity); //we're trying to test findCityByCountryCode, we're assuming this worked
		List<City> results = dao.findCityByCountryCode(TEST_COUNTRY);

		assertNotNull(results);
		assertEquals(1, results.size());
		City savedCity = results.get(0);
		assertCitiesAreEqual(theCity, savedCity);

		//make sure that the list contains our city if it might contain multiple cities
		City foundById = null;
		for(City c : results){ //loop through the list and find the city by the primary key
			if (c.getId().equals(theCity.getId())){
				foundById = c;
				break;//this makes my loop a bit faster
			}
		}

		assertNotNull(foundById);//make sure that i found the city based on the pk
		assertCitiesAreEqual(theCity,foundById); //make sure that the city i found is as expected
	}

	@Test
	public void returns_multiple_cities_by_country_code() {

		dao.create(makeLocalCityObject("SQL Station", "South Dakota", TEST_COUNTRY, 65535));
		dao.create(makeLocalCityObject("Postgres Point", "North Dakota", TEST_COUNTRY, 65535));

		List<City> results = dao.findCityByCountryCode(TEST_COUNTRY);

		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void returns_cities_by_district() {
		String testDistrict = "Tech Elevator";
		City theCity = makeLocalCityObject("SQL Station", testDistrict, TEST_COUNTRY, 65535);
		dao.create(theCity);

		List<City> results = dao.findCityByDistrict(testDistrict);

		assertNotNull(results);
		assertEquals(1, results.size());
		City savedCity = results.get(0);
		assertCitiesAreEqual(theCity, savedCity);
	}

	@Test
	public void update_city_bad_test_always_passes() {
		City theCity = makeLocalCityObject("SQL Station", "South Dakota", TEST_COUNTRY, 65535);

		//add the city to the database
		dao.create(theCity);

		//update the local object
		theCity.setPopulation(1);
		theCity.setDistrict("Disneyland");

		//save to the database
		City updatedCity = dao.update(theCity);

		//check that they are the same
		assertCitiesAreEqual(theCity,updatedCity);
	}

	@Test
	public void update_city_good_test() {
		City theCity = makeLocalCityObject("SQL Station", "South Dakota", TEST_COUNTRY, 65535);

		//add the city to the database
		dao.create(theCity);

		//update the local object
		theCity.setPopulation(1);
		theCity.setDistrict("Disneyland");

		//save to the database
		City updateResult = dao.update(theCity);

		//re-read the city from the database
		City updatedCity = dao.findCityById(theCity.getId());

		//check that they are the same
		assertCitiesAreEqual(theCity,updatedCity);
	}

	@Test
	public void test_delete() {
		City theCity = makeLocalCityObject("SQL Station", "South Dakota", TEST_COUNTRY, 65535);

		//add the city to the database
		dao.create(theCity);

		//get the list of citites for the country
		List<City> cities = dao.findCityByCountryCode(TEST_COUNTRY);
		//make sure that it's in there
		assertEquals(1,cities.size());
		//act  - delete the city
		dao.delete(theCity.getId());

		List<City> newResults = dao.findCityByCountryCode(TEST_COUNTRY);
		assertEquals(0,newResults.size());

	}

	private City makeLocalCityObject(String name, String district, String countryCode, int population) {
		City theCity = new City();
		theCity.setName(name);
		theCity.setDistrict(district);
		theCity.setCountryCode(countryCode);
		theCity.setPopulation(population);
		return theCity;
	}

	private void assertCitiesAreEqual(City expected, City actual) {
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDistrict(), actual.getDistrict());
		assertEquals(expected.getCountryCode(), actual.getCountryCode());
		assertEquals(expected.getPopulation(), actual.getPopulation());
	}
}
