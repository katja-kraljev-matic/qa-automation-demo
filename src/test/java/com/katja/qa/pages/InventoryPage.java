package com.katja.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Page Object for the product list shown after login. */
public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.cssSelector(".title");
    private final By products = By.cssSelector(".inventory_item");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String pageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public int productCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        return driver.findElements(products).size();
    }

    /** @param productId for example "sauce-labs-backpack" */
    public InventoryPage addToCart(String productId) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-" + productId))).click();
        return this;
    }

    public InventoryPage removeFromCart(String productId) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("remove-" + productId))).click();
        return this;
    }

    /** Number shown on the cart icon, 0 if the badge is not displayed. */
    public int cartBadgeCount() {
        List<WebElement> badge = driver.findElements(cartBadge);
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText());
    }

    public CartPage openCart() {
        driver.findElement(cartLink).click();
        return new CartPage(driver);
    }
}
