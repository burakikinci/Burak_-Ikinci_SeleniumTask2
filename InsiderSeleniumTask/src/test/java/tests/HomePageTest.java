package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import util.DriverFactory;

public class HomePageTest {

    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        homePage = new HomePage(driver);
    }

    @Test
    public void insiderHomePageShouldOpenSuccessfully() {
        homePage.open();
        homePage.assertHomePageLoaded();
    }
    @Test
    public void careersPageShouldBeAccessibleFromCompanyMenu() {
        homePage.open();
        homePage.assertHomePageLoaded();
        homePage.hoverOverCompanyMenu();
        homePage.clickCareers();
        homePage.assertCareersPageLoaded();
        homePage.checkLocationsSection();
    }
    @Test
    public void seeAllTeamsShouldBeAccessibleFromCareersPage() {
        homePage.open();
        homePage.assertHomePageLoaded();
        homePage.hoverOverCompanyMenu();
        homePage.clickCareers();
        homePage.scrollToSeeAllTeamsButton();
        homePage.clickSeeAllTeams();
        homePage.assertTeamsBlockVisible();
    }
    @Test
    public void lifeAtInsiderShouldBeAccessibleFromCareersPage() {
        homePage.open();
        homePage.assertHomePageLoaded();
        homePage.hoverOverCompanyMenu();
        homePage.clickCareers();
        homePage.scrollToLifeAtInsider();
        homePage.assertLifeAtInsiderBlockVisible();

    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
