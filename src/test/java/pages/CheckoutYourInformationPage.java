package pages;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Double.valueOf;

public class CheckoutYourInformationPage extends BaseTest {
    public CheckoutYourInformationPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "title")
    public WebElement checkoutHeader;

    @FindBy(id = "first-name")
    public WebElement firstNameField;

    @FindBy(id = "last-name")
    public WebElement lastNameField;

    @FindBy(id = "postal-code")
    public WebElement postalCodeField;

    @FindBy(id = "continue")
    public WebElement continueButton;




    //----------------------------------------------

    public String getCheckoutHeaderText() {
        return checkoutHeader.getText();
    }

    public void clickOnFirstNameField() {
        firstNameField.click();
    }

    public void clickOnLastNameField() {
        lastNameField.click();
    }

    public void clickOnPostalCodeField() {
        postalCodeField.click();
    }

    public void inputFirstName(String first) {
        firstNameField.sendKeys(first);
    }

    public void inputLastName(String last) {
        lastNameField.sendKeys(last);
    }

    public void inputPostalCode(String zip) {
        postalCodeField.sendKeys(zip);
    }

    public void clickOnContinueButton() {
        continueButton.click();
    }




}
