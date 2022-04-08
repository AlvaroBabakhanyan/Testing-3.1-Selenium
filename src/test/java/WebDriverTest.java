import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WebDriverTest {
    public static WebDriver driver;
    public static String baseURL = "https://ourworldindata.org/";
    public static SoftAssert softAssert;

    @BeforeClass
    public static void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        softAssert = new SoftAssert();
    }

    @BeforeMethod
    public void openTest() {
        driver.get(baseURL);
    }

    @Test
    public void oxfordLogoTest() {
        WebElement oxfordLogoButton = driver.findElement(By.className("oxford-logo"));
        oxfordLogoButton.click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://www.oxfordmartin.ox.ac.uk/global-development");
    }

    @Test
    public void homepageButtonTest() {
        WebElement logoHomepageButton1 = driver.
                findElement(By.cssSelector("body.FrontPage:nth-child(2) header.site-header:nth-child(1) div.wrapper.site-navigation-bar > div.site-logo"));
        WebElement logoHomepageButton2 = driver.findElement(By.className("site-logo"));
        softAssert.assertEquals(logoHomepageButton2.getText(), logoHomepageButton1.getText()); // both should locate homepage button

        logoHomepageButton1.click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://ourworldindata.org/");
    }

    @Test
    public void aboutButtonTest() {
        WebElement aboutButton = driver.
                findElement(By.xpath("//body[1]/header[1]/div[1]/nav[1]/div[2]/div[1]/ul[1]/li[2]"));
        aboutButton.click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://ourworldindata.org/about");
        //click about button when already on about page
        aboutButton = driver.findElement(By.xpath("//body[1]/header[1]/div[1]/nav[1]/div[2]/div[1]/ul[1]/li[2]"));
        aboutButton.click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://ourworldindata.org/about");
    }

    @Test
    public void allChartsButtonTest() {
        SoftAssert softAssert = new SoftAssert();

        WebElement allChartsButton1 = driver.
                findElement(By.linkText("See all posts"));
        WebElement allChartsButton2 = driver.
                findElement(By.partialLinkText("charts"));
        softAssert.assertEquals(allChartsButton1.getText(), allChartsButton2.getText()); //both should locate the same button in this case
        allChartsButton1.click();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://ourworldindata.org/charts");
    }

    @Test
    public void searchTest() {
        driver.get("https://ourworldindata.org/search?q=");
        WebElement searchBar = driver.
                findElement(By.className("inputWrapper")).findElement(By.name("q"));
        searchBar.sendKeys("covid");

        WebElement searchButton = driver.
                findElement(By.xpath("//body/main[1]/form[1]")).findElement(By.tagName("button"));
        searchButton.click();

        softAssert.assertEquals(driver.getCurrentUrl(), "https://ourworldindata.org/search?q=covid");
    }

    @AfterClass
    public void close() {
        driver.close();
    }
}
