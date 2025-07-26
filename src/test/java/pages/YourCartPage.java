package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static java.lang.Double.valueOf;

public class YourCartPage extends BaseTest {
    public YourCartPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "title")
    public WebElement yourCart;

    @FindBy(id = "checkout")
    public WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    public WebElement continueShoppingButton;

    @FindBy(css = "button[class = '.btn.btn_secondary.btn_small.cart_button']")
    public WebElement removeButton;

    @FindBy(className = "cart_item")
    public List<WebElement> fullCartItems;

    @FindBy(className = "cart_item_label")
    public List<WebElement> cartItemsWithPrice;


    //-------------------------------

    public double itemPrice() {
        double price = 0;
        for (int i = 0; i < cartItemsWithPrice.size(); i++) {
            String priceString = cartItemsWithPrice.get(i).findElement(By.className("inventory_item_price")).getText();
            String numericPrice = priceString.substring(1);
            price = valueOf(numericPrice);
        }
        return price;
    }





    public void removeCartItemByName(String item) {
        for (int i = 0; i < fullCartItems.size(); i++) {
            if (fullCartItems.get(i).findElement(By.cssSelector(".inventory_item_name")).getText().equalsIgnoreCase(item)) {
                try {
                    fullCartItems.get(i).findElement(By.cssSelector(".btn_small.cart_button")).click();
                } catch (Exception e) {

                }
            }
        }
    }

    public String yourCartText() {
        return yourCart.getText();
    }

    public void clickOnCheckoutButton() {
        checkoutButton.click();
    }

    public void clickOnContinueShoppingButton() {
        continueShoppingButton.click();
    }

    public void clickOnRemoveButton() {
        removeButton.click();
    }


}
