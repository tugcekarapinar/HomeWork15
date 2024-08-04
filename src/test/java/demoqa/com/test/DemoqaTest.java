package demoqa.com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoqaTest {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://demoqa.com/buttons");
    }

    @Test(priority = 0)
    public void testDoubleClickButton() {
        WebElement btnDoubleClick = driver.findElement(By.xpath("//button[text()=\"Double Click Me\"]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btnDoubleClick);

        Actions actions = new Actions(driver);
        actions.doubleClick(btnDoubleClick).perform();

        WebElement result = driver.findElement(By.xpath("//p[@id='doubleClickMessage']"));
        Assert.assertEquals(result.getText(), "You have done a double click");
    }

    @Test(priority = 1)
    public void testClickButton() {
        WebElement btnClick = driver.findElement(By.xpath("//button[text()=\"Click Me\"]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btnClick);

        btnClick.click();

        WebElement result = driver.findElement(By.xpath("//p[@id='dynamicClickMessage']"));
        Assert.assertEquals(result.getText(), "You have done a dynamic click");
    }
    @Test (priority =2)
    public void testAddRecord() {
        driver.get("https://demoqa.com/webtables");
        WebElement btnAdd = driver.findElement(By.xpath("//button[text()=\"Add\"]"));
        btnAdd.click();

        WebElement textFirstName = driver.findElement(By.xpath("//*[@id='firstName']"));
        WebElement textLastName = driver.findElement(By.xpath("//*[@id='lastName']"));
        WebElement textEmail = driver.findElement(By.xpath("//*[@id='userEmail']"));
        WebElement textAge = driver.findElement(By.xpath("//*[@id='age']"));
        WebElement textSalary = driver.findElement(By.xpath("//*[@id='salary']"));
        WebElement textDepartment = driver.findElement(By.xpath("//*[@id='department']"));

        textFirstName.sendKeys("Demir Ege");
        textLastName.sendKeys("Karapinar");
        textEmail.sendKeys("demirege@gmail.com");
        textAge.sendKeys("0");
        textSalary.sendKeys("20000");
        textDepartment.sendKeys("Bebek");

        WebElement btnSubmit = driver.findElement(By.xpath("//*[@id='submit']"));
        btnSubmit.click();

        WebElement btnEdit = driver.findElement(By.xpath("//div[contains(text(), 'Demir Ege')]//..//div[@class='action-buttons']//span[contains(@title, 'Edit')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btnEdit);
        btnEdit.click();

        textSalary.findElement(By.xpath("//*[@id='salary']"));
        textSalary.clear();
        textSalary.sendKeys("25000");

        WebElement salaryCell = driver.findElement(By.xpath("//div[contains(text(), 'Demir Ege')]//following-sibling::div[4]"));
        Assert.assertEquals(salaryCell.getText(), "25000");
    }
    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}