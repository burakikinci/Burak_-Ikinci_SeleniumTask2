package pages;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import util.ElementHelper;
import java.time.Duration;
import java.util.List;
import static org.testng.Assert.*;


public class HomePage {
    private final WebDriver driver;
    private final ElementHelper helper;
    private final Actions actions;
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.helper = new ElementHelper(driver);
        this.actions = new Actions(driver);
    }
    /******************************Locators**************************************************/
    By companyMenuItems = By.cssSelector("#navbarDropdownMenuLink");
    By careersLink = By.cssSelector(
            "#navbarNavDropdown > ul:nth-child(1) > li.nav-item.dropdown.show > div > "
                    + "div.new-menu-dropdown-layout-6-mid-container > a:nth-child(2)");

    By locationsTitle = By.cssSelector(".category-title-media.ml-0");
    By locationItems = By.cssSelector("#location-slider > div > ul");
     By seeAllTeamsBtn = By.cssSelector(
            ".btn.btn-outline-secondary.rounded.text-medium.mt-5.mx-auto.py-3.loadmore");

     By teamsContainer = By.cssSelector(
            "#career-find-our-calling > div > div > "
                    + "div.col-12.d-flex.flex-wrap.p-0.career-load-more");

    By lifeAtInsiderTitle = By.cssSelector(
            "div.elementor-element.elementor-element-21cea83.elementor-widget.elementor-widget-heading "
                    + "h2.elementor-heading-title.elementor-size-default");
    By lifeGallery = By.cssSelector(
            "body > div.elementor.elementor-22610 > section.elementor-section.elementor-top-section."
                    + "elementor-element.elementor-element-a8e7b90.elementor-section-full_width."
                    + "elementor-section-height-default.elementor-section-height-default "
                    + "div.elementor-element.elementor-element-c06d1ec.elementor-skin-carousel."
                    + "elementor-widget.elementor-widget-media-carousel.e-widget-swiper > div > div > div");
    /****************************************************************************************************/
    public void open() {
        driver.get(util.ConfigReader.get("url"));
    }
    public void assertHomePageLoaded() {
        String expectedTitle = "#1 Leader in Individualized, Cross-Channel CX — Insider";
        String actualTitle = driver.getTitle();
        System.out.println("Page title: " + actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle, "Page title does not match!");

    }
    public void hoverOverCompanyMenu() {
        List<WebElement> items = driver.findElements(companyMenuItems);
        WebElement company = items.get(4);
        actions.moveToElement(company)
                .pause(Duration.ofSeconds(5))
                .perform();
    }

    public void clickCareers() {
        helper.click(careersLink);
    }
    public void assertCareersPageLoaded() {
        String expectedTitleCareersPage = "Ready to disrupt? | Insider Careers";
        String actualTitleCareersPage = driver.getTitle();
        System.out.println("Careers Page title: " + actualTitleCareersPage);
        Assert.assertEquals(actualTitleCareersPage, expectedTitleCareersPage, "Page title does not match!");
    }
    public void scrollToLocationsSmooth() {
        WebElement el = driver.findElement(locationsTitle);
        String js  = "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})";
        ((JavascriptExecutor) driver).executeScript(js, el);
        helper.waitForVisibility(locationsTitle, 10);
    }
    public void checkLocationsSection() {
        scrollToLocationsSmooth();
        assertTrue(driver.findElement(locationsTitle).isDisplayed(), "Locations title not visible");
        assertAllVisibleLocationBoxes();
    }
    public void assertAllVisibleLocationBoxes() {
        helper.waitUntilVisibleList(locationItems, 10);

        List<WebElement> boxes = driver.findElements(locationItems)
                .stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());

        assertTrue(boxes.size() > 0, "No visible location boxes!");
        for (WebElement box : boxes) {
            assertTrue(box.isDisplayed(), "A visible location box is not displayed!");
        }
    }
    public void scrollToSeeAllTeamsButton() {
        WebElement btn = driver.findElement(seeAllTeamsBtn);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", btn);
        helper.waitForVisibility(seeAllTeamsBtn, 10);
    }

    public void clickSeeAllTeams() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        helper.click(seeAllTeamsBtn);
    }

    public void assertTeamsBlockVisible() {
        helper.waitForVisibility(teamsContainer, 10);
        assertTrue(driver.findElement(teamsContainer).isDisplayed(),
                "Teams block is not visible after clicking 'See all teams'!");
    }
    public void scrollToLifeAtInsider() {
        WebElement title = driver.findElement(lifeAtInsiderTitle);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'})", title);
        helper.waitForVisibility(lifeAtInsiderTitle, 10);
    }

    public void assertLifeAtInsiderBlockVisible() {
        assertTrue(driver.findElement(lifeAtInsiderTitle).isDisplayed(),
                "'Life at Insider' title is not visible!");

        helper.waitForVisibility(lifeGallery, 10);
        assertTrue(driver.findElement(lifeGallery).isDisplayed(),
                "Life-at-Insider gallery is not visible!");
    }

}
