package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static java.lang.Double.valueOf;

public class ProductsPage extends BaseTest {
    public ProductsPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "react-burger-menu-btn")
    public WebElement hamburgerMenu;

    @FindBy(id = "logout_sidebar_link")
    public WebElement logoutButton;

    @FindBy(id = "shopping_cart_container")
    public WebElement shoppingCartButton;

    @FindBy(className = "product_sort_container")
    public WebElement nameMenu;

    @FindBy(css = ".btn.btn_primary.btn_small.btn_inventory")
    public WebElement addToCartButton;

    @FindBy(css = ".inventory_item")
    public WebElement fullItemDetails;

    @FindBy(css = ".inventory_item")
    public List<WebElement> itemDetails;


    @FindBy(className = "shopping_cart_badge")
    public WebElement cartItemCounter;

//    @FindBy(className = "inventory_item")
//    List<WebElement> addToCartButton;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    public WebElement cartButtonID;

    @FindBy(css = ".btn.btn_secondary.btn_small.btn_inventory")
    public WebElement removeButton;

    @FindBy(className = "inventory_item_price")
    public WebElement itemPrice;


    //--------------------------------------


    public double price() {
        String priceString = itemPrice.getText();
        String numericPrice = priceString.substring(1);
        return valueOf(numericPrice);
    }


    public void clickOnItemAddToCartButton() {
        for (int i = 0; i < itemDetails.size(); i++) {
            itemDetails.get(i).findElement(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory")).click();  //findElement(By.className("inventory_item_name")).getText();
        }
    }

    public void clickOnAllRemoveItemButtons() {
        for (int i = 0; i < itemDetails.size(); i++) {
            itemDetails.get(i).findElement(By.cssSelector(".btn.btn_secondary.btn_small.btn_inventory")).click();
        }
    }

    public void clickOnAddButton() {
        addToCartButton.click();
    }

    public void collectItemNames() {
        for (int i = 0; i < itemDetails.size(); i++) {
            System.out.println(itemDetails.get(i).findElement(By.className("inventory_item_name")).getText());
        }
    }

    public String getItemNameText() {
        return fullItemDetails.findElement(By.className("inventory_item_name")).getText();
    }



    public void clickOnHamburgerMenu() {
        hamburgerMenu.click();
    }

    public void clickOnLogoutButton() {
        logoutButton.click();
    }

    public void clickOnShoppingCartButton() {
        shoppingCartButton.click();
    }

    public void clickOnNameMenu() {
        nameMenu.click();
    }


    public String getAddToCartButtonText() {
        return addToCartButton.getText();
    }


    public String getCartItemCounterText() {
        return cartItemCounter.getText();
    }



}
