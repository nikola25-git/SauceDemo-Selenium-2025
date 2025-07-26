package pages;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Double.valueOf;

public class CheckoutOverviewPage extends BaseTest {
    public CheckoutOverviewPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "title")
    public WebElement checkoutOverviewHeader;

    @FindBy(id = "finish")
    public WebElement finishButton;

    @FindBy(className = "summary_subtotal_label")
    public WebElement priceSubTotal;

    @FindBy(css = ".error-message-container.error")
    public WebElement errorMessage;


    //-------------------------------------------

    public String getCheckoutOverviewHeaderText() {
        return checkoutOverviewHeader.getText();
    }

    public void clickOnFinishButton() {
        finishButton.click();
    }

    public double getPriceSubTotal() {
        String stringSubTotal = priceSubTotal.getText();
        String numericSubTotal = stringSubTotal.substring(13);
        return valueOf(numericSubTotal);
    }


}
