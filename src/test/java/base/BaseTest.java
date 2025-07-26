package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import pages.*;

public class BaseTest {

public static WebDriver driver;
public WebDriverWait wait;
public ExcelReader excelReader;
public LoginPage loginPage;
public ProductsPage productsPage;
public YourCartPage yourCartPage;
public Cookie cookie;
public CheckoutYourInformationPage checkoutYourInformationPage;
public CheckoutOverviewPage checkoutOverviewPage;
public CheckoutCompletePage checkoutCompletePage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }
}
