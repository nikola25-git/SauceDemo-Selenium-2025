package tests;

import base.BaseTest;
import base.ExcelReader;
import base.RetryAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.YourCartPage;
import pages.ProductsPage;
import pages.LoginPage;

import java.io.IOException;
import java.time.Duration;

public class AddToCartTest extends BaseTest {

    @BeforeMethod
    public void pageSetUp() throws IOException {
//        driver = new ChromeDriver();

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

        excelReader = new ExcelReader("C:\\Users\\mikee\\Desktop\\standard_userAvailableItems.xlsx");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    @Test(priority = 10, retryAnalyzer = RetryAnalyzer.class)
    public void cartIsEmpty() {
        loginPage.usernameField.clear();
        loginPage.inputUsername("standard_user");
        loginPage.passwordField.clear();
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();

        try {
            Assert.assertTrue(productsPage.getCartItemCounterText().isBlank());
        } catch (Exception e) {

        }

        productsPage.clickOnShoppingCartButton();
        try {
            Assert.assertFalse(yourCartPage.removeButton.isDisplayed());
        } catch (Exception e) {

        }
    }

    // Napravi logiku za pronalazenje naziva proizvoda iz excel tabele i automatsko dodavanje u korpu
    // Mozes prethodno proci petljom kroz sve elemente i odstampati tekst naziva proizvoda
    // Napravi i logiku za uklanjanje proizvoda iz korpe (try-catch: ovaj blok ubaciti i za dodavanje proizvoda)
    // Napisi asertacije za sve gorenavedeno + sredi asertacije za logovanje

    @Test(priority = 20, retryAnalyzer = RetryAnalyzer.class)
    public void collectItemNames() {
        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();
        productsPage.collectItemNames();

    }

    @Test(priority = 21, retryAnalyzer = RetryAnalyzer.class)
    public void itemNameGetText() {    // ovo RADI (za dodavanje svih item-a iz liste; napravi metodu za dodavanje po nazivu
        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();
        System.out.println(productsPage.getItemNameText());
        productsPage.clickOnItemAddToCartButton();
    }

    @Test(priority = 30, retryAnalyzer = RetryAnalyzer.class)
    public void addAllItemsToCart() {
        loginPage.usernameField.clear();
        loginPage.inputUsername("standard_user");
        loginPage.passwordField.clear();
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();

        // Dodavanje item-a po nazivu iz Excel file-a

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

        yourCartPage.removeCartItemByName("sauce labs bike light");

    }


    @Test(priority = 31, retryAnalyzer = RetryAnalyzer.class)
    public void userCanAddItemsToCart() {
        loginPage.inputUsername("standard_user");
        loginPage.inputPassword("secret_sauce");
        loginPage.clickOnLoginButton();

        System.out.println(productsPage.getAddToCartButtonText());
        System.out.println(productsPage.cartButtonID);

        System.out.println(productsPage.cartButtonID.getAttribute("id"));
        if (productsPage.cartButtonID.getAttribute("id").contains("sauce-labs-backpack")) {
            System.out.println("First available item added.");
        }
        else System.out.println("Something went wrong.");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
