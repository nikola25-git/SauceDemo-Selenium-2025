package tests;

import base.BaseTest;
import base.ExcelReader;
import base.RetryAnalyzer;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProductsPage;
import pages.LoginPage;

import java.io.IOException;
import java.time.Duration;

public class LoginTest extends BaseTest {

    @BeforeClass
    public void pageSetUp() throws IOException {

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-password-manager-reauthentication");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetectionEnabled");
        options.addArguments("--password-store=basic");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);

//        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");



        loginPage = new LoginPage();
        excelReader = new ExcelReader("C:\\Users\\mikee\\Desktop\\saucedemoLoginDetails.xlsx");
        productsPage = new ProductsPage();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void userLoginAndLogOut() {

        for (int i = 1; i < excelReader.getLastRow("Sheet1"); i++) {

            String username = excelReader.getStringData("Sheet1", i, 0);
            String password = excelReader.getStringData("Sheet1", i, 1);

            loginPage.usernameField.clear();
            loginPage.inputUsername(username);
            loginPage.passwordField.clear();
            loginPage.inputPassword(password);

            loginPage.clickOnLoginButton();

            try {
                Assert.assertTrue(productsPage.hamburgerMenu.isDisplayed());
                productsPage.clickOnHamburgerMenu();
//                wait.until(ExpectedConditions.elementToBeClickable(productsPage.logoutButton)); //visibilityOfElementLocated(By.id("logout_sidebar_link")));
                Thread.sleep(500);
                Assert.assertTrue(productsPage.logoutButton.isDisplayed());
                productsPage.clickOnLogoutButton();

                wait.until(ExpectedConditions.elementToBeClickable(loginPage.usernameField));
//                driver.get("https://www.saucedemo.com/")

            } catch (Exception e) {
//                System.out.println("Unsuccessful login attempt for: " + username);
            }

        }
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
