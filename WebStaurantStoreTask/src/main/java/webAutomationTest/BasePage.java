package webAutomationTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BasePage {
    public static WebDriver driver;
    String webstaurantstoreUrl = "https://www.webstaurantstore.com";
    public static ExtentReports reports;
    public static ExtentTest extentTest;
    public static String homepageTitle;

    public static FileReader fileReader;
    public static Properties properties;

    @BeforeClass
    public static void startTest(){
       reports = new ExtentReports("testoutput/ExtentreportResults.html");
       extentTest = reports.startTest("BasePage");
    }
    @BeforeTest
    public void driverSetup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    public void navigateToWebstaurantUrl() throws InterruptedException {
        driver.get(webstaurantstoreUrl);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        homepageTitle = driver.getTitle();
        Assert.assertTrue(homepageTitle.contains("Restaurant"));
        extentTest.log(LogStatus.INFO, "Navigated to : "+webstaurantstoreUrl);
    }

    public void propertyFileReader() throws IOException {
        fileReader = new FileReader("webElements.properties");
        properties = new Properties();
        properties.load(fileReader);
    }

    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }

    @AfterClass
    public static void endTest(){
        reports.endTest(extentTest);
        reports.flush();
    }
}
