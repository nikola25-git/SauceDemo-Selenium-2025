package tests;

import base.BaseTest;
import base.ExcelReader;
import base.RetryAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;
import java.time.Duration;

import static java.lang.Double.valueOf;

public class CheckOutPagePriceTest extends BaseTest {

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

        driver.manage().window().maximize();
        driver.navigate().to("https://www.saucedemo.com/");
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        yourCartPage = new YourCartPage();
        checkoutYourInformationPage = new CheckoutYourInformationPage();
        checkoutOverviewPage = new CheckoutOverviewPage();
        checkoutCompletePage = new CheckoutCompletePage();

        excelReader = new ExcelReader("C:\\Users\\mikee\\Desktop\\standard_userAvailableItems.xlsx");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void priceSubTotalAndGrandTotalAreCorrect() {
        loginPage.usernameField.clear();
        loginPage.inputUsername("standard_user");
        loginPage.passwordField.clear();
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();

        int count = 0;
        double priceTotal = 0;

        for (int i = 0; i <= excelReader.getLastRow("Sheet1"); i++) {
            String item = excelReader.getStringData("Sheet1", i, 0).trim();
            boolean found = false;

            for (int j = 0; j < productsPage.itemDetails.size(); j++) {
                if (item.equalsIgnoreCase(productsPage.itemDetails.get(j).findElement(By.className("inventory_item_name")).getText().trim())) {
                    try {
                        productsPage.itemDetails.get(j).findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).click();
                        Assert.assertTrue(productsPage.removeButton.isDisplayed());
                        found = true;
                        count++;
                        break;
                    } catch (Exception e) {
                    }

                }
            }
            if (!found) {
                System.out.println("Not found: " + item);
            }
        }
        Assert.assertNotEquals(productsPage.getCartItemCounterText(), "0");
        Assert.assertEquals(String.valueOf(count), productsPage.getCartItemCounterText());

        productsPage.clickOnShoppingCartButton();


//        productsPage.clickOnShoppingCartButton();
        for (int i = 0; i < yourCartPage.cartItemsWithPrice.size(); i++) {
            String priceString = yourCartPage.cartItemsWithPrice.get(i).findElement(By.className("inventory_item_price")).getText();
            String numericPrice = priceString.substring(1);
            priceTotal += valueOf(numericPrice);
        }
        System.out.println(priceTotal);

        yourCartPage.clickOnCheckoutButton();
        System.out.println(checkoutYourInformationPage.getCheckoutHeaderText());


        Assert.assertEquals(checkoutYourInformationPage.getCheckoutHeaderText().trim(), "Checkout: Your Information");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-one.html");
        Assert.assertTrue(checkoutYourInformationPage.firstNameField.isDisplayed());
        Assert.assertTrue(checkoutYourInformationPage.lastNameField.isDisplayed());
        Assert.assertTrue(checkoutYourInformationPage.postalCodeField.isDisplayed());


        checkoutYourInformationPage.firstNameField.clear();
        checkoutYourInformationPage.inputFirstName("Pistol");
        checkoutYourInformationPage.lastNameField.clear();
        checkoutYourInformationPage.inputLastName("Pete");
        checkoutYourInformationPage.postalCodeField.clear();
        checkoutYourInformationPage.inputPostalCode("30033");

        checkoutYourInformationPage.clickOnContinueButton();

        System.out.println("Checkout Overview Header: " + checkoutOverviewPage.getCheckoutOverviewHeaderText());

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-step-two.html");
        Assert.assertEquals(checkoutOverviewPage.getCheckoutOverviewHeaderText().trim(), "Checkout: Overview");

        Assert.assertEquals(checkoutOverviewPage.getPriceSubTotal(), priceTotal);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
