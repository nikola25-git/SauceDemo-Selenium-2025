package tests;

import base.BaseTest;
import base.RetryAnalyzer;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.ProductsPage;
import pages.LoginPage;

import java.io.IOException;
import java.time.Duration;


public class StandardUserLogIn extends BaseTest {

    @BeforeMethod
    public void pageSetUp() throws IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");

        loginPage = new LoginPage();
        productsPage = new ProductsPage();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void userCanLogIn() {
        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();

        Assert.assertTrue(productsPage.hamburgerMenu.isDisplayed());
        Assert.assertTrue(productsPage.shoppingCartButton.isDisplayed());
        Assert.assertTrue(productsPage.nameMenu.isDisplayed());

    }

    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void userCanLogOut() {
        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();

        productsPage.clickOnHamburgerMenu();
        productsPage.clickOnLogoutButton();

        try {
            Assert.assertFalse(productsPage.logoutButton.isDisplayed());
        } catch (Exception e) {

        }
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
        Assert.assertTrue(loginPage.usernameField.isDisplayed());
        Assert.assertTrue(loginPage.passwordField.isDisplayed());
        Assert.assertTrue(loginPage.loginButton.isDisplayed());

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
