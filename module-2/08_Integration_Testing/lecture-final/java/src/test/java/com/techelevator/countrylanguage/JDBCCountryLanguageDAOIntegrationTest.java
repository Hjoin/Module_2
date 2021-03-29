package com.techelevator.countrylanguage;

import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class JDBCCountryLanguageDAOIntegrationTest {

    private static final String TEST_COUNTRY = "XYZ";

    /* Using this particular implementation of DataSource so that
     * every database interaction is part of the same database
     * session and hence the same database transaction */
    private static SingleConnectionDataSource dataSource;
    private JDBCCountryLanguageDAO dao;

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
        dao = new JDBCCountryLanguageDAO(dataSource);
    }

    /* After each test, we rollback any changes that were made to the database so that
     * everything is clean for the next test */
    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void test_get_languages_for_country() {
        //arrange
        //add a country language to the database
        addLanguageForCountryToDatabase(TEST_COUNTRY,5.5,"Klingon",true);
        CountryLanguage expected = new CountryLanguage(TEST_COUNTRY,true,"Klingon",5.5);

        //act
        List<CountryLanguage> list = dao.getLanguagesForCountry(TEST_COUNTRY);

        //assert
        assertEquals(1,list.size());
        assertLanguagesAreEqual(expected,list.get(0));
    }

    private void addLanguageForCountryToDatabase(String countryCode, double percent, String name, boolean isOfficial){
        String sql = "INSERT INTO countrylanguage(countrycode,percentage,isofficial,language) VALUES(?,?,?,?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(sql, countryCode,percent,isOfficial,name);
    }

    private void assertLanguagesAreEqual(CountryLanguage a, CountryLanguage b){
        assertEquals(a.getCountryCode(),b.getCountryCode());
        assertEquals(a.getLanguage(),b.getLanguage());
        assertEquals(a.isOfficial(),b.isOfficial());
        assertEquals(a.getPercentage(),b.getPercentage(),.01);
    }
}
