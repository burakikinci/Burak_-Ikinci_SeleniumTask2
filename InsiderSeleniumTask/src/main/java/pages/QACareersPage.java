package pages;
import org.openqa.selenium.*;
import util.ElementHelper;
public class QACareersPage {
    private final WebDriver   driver;
    private final ElementHelper helper;

    /********************************************Locators***********************************************/
    By seeAllQaJobsBtn = By.cssSelector(
            ".btn.btn-outline-secondary.rounded.text-medium.mt-2.py-3.px-lg-5.w-100.w-md-50");
    By acceptCookieBtn =By.cssSelector("#wt-cli-accept-all-btn");
    By filterByLocationBtn = By.cssSelector("#select2-filter-by-location-container");
    By istanbulOption = By.xpath("//li[contains(text(),'Istanbul, Turkiye')]");
    /*******************************************************************************************************************/

    public QACareersPage(WebDriver driver) {
        this.driver  = driver;
        this.helper  = new ElementHelper(driver);
    }

    public void open() {
        driver.get(util.ConfigReader.get("qaCareersUrl"));
    }

    public void clickSeeAllQaJobs() {
        helper.click(acceptCookieBtn);
        helper.click(seeAllQaJobsBtn);
    }
    public void openLocationFilter() {
        helper.waitForVisibility(filterByLocationBtn, 10);
        try {
            Thread.sleep(8000);  //It takes a while for the drop-down menu to appear, so it's a long wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        helper.clickMultipleTimes(filterByLocationBtn, 3, 3);
    }

    public void selectLocationIstanbul() {
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        WebElement istanbulOption = wait.until(
                org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//li[contains(text(), 'Istanbul, Turkiye')]")
                )
        );
        istanbulOption.click();

    }

    public void verifyAllJobsAreQualityAssuranceInIstanbul() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500);");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        java.util.List<org.openqa.selenium.WebElement> jobCards = driver.findElements(By.cssSelector("#jobs-list > div"));
        for (org.openqa.selenium.WebElement card : jobCards) {
            String cardText = card.getText();
            if (!cardText.contains("Quality Assurance")) {
                throw new AssertionError("There is no 'Quality Assurance' on the card: " + cardText);
            }
            if (!cardText.contains("Istanbul, Turkiye")) {
                throw new AssertionError("There is 'Quality Assurance' on the card: " + cardText);
            }
        }
    }

    public void clickFirstJobAndViewRole() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        org.openqa.selenium.WebElement firstJob = driver.findElement(By.cssSelector("#jobs-list > div:nth-child(1)"));
        firstJob.click();
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        org.openqa.selenium.WebElement viewRoleBtn = wait.until(
            org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(
                By.cssSelector(".btn.btn-navy.rounded.pt-2.pr-5.pb-2.pl-5[href='https://jobs.lever.co/useinsider/78ddbec0-16bf-4eab-b5a6-04facb993ddc']")
            )
        );
        viewRoleBtn.click();
    }

    public void verifyNewTabOpened() {
        java.util.Set<String> windowHandles = driver.getWindowHandles();
        if (windowHandles.size() != 2) {
            throw new AssertionError("No new tabs opened or the number of tabs is different than expected: " + windowHandles.size());
        }
        System.out.println(windowHandles.size());
        String originalWindow = driver.getWindowHandle();
        for (String handle : windowHandles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

}