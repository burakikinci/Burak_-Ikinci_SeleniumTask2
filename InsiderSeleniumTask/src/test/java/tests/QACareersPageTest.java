package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.QACareersPage;
import util.DriverFactory;


public class QACareersPageTest {

    private WebDriver driver;
    private QACareersPage  qaPage;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
        driver  = DriverFactory.getDriver();
        qaPage  = new QACareersPage(driver);
    }

    @Test
    public void shouldOpenQACareersAndListAllJobs() {
        qaPage.open();
        qaPage.clickSeeAllQaJobs();
        qaPage.openLocationFilter();
        qaPage.selectLocationIstanbul();
    }

    @Test
    public void verifyAllJobsAreQualityAssuranceInIstanbul() {
        qaPage.open();
        qaPage.clickSeeAllQaJobs();
        qaPage.openLocationFilter();
        qaPage.selectLocationIstanbul();
        qaPage.verifyAllJobsAreQualityAssuranceInIstanbul();
    }

    @Test
    public void shouldClickFirstJobAndViewRole() {
        qaPage.open();
        qaPage.clickSeeAllQaJobs();
        qaPage.openLocationFilter();
        qaPage.selectLocationIstanbul();
        qaPage.verifyAllJobsAreQualityAssuranceInIstanbul();
        qaPage.clickFirstJobAndViewRole();
        qaPage.verifyNewTabOpened();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
