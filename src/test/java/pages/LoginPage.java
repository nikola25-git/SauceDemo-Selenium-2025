package pages;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseTest {

    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    public WebElement usernameField;

    @FindBy(id = "password")
    public WebElement passwordField;

    @FindBy(id = "login-button")
    public WebElement loginButton;

    //------------------------------------

    public void inputUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void inputPassword(String pass) {
        passwordField.sendKeys(pass);
    }

    public void clickOnLoginButton() {
        loginButton.click();
    }

}
