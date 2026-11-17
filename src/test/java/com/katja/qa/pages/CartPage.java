package com.katja.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/** Page Object for the shopping cart. */
public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By title = By.cssSelector(".title");
    private final By items = By.cssSelector(".cart_item");
    private final By itemNames = By.cssSelector(".inventory_item_name");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int itemCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
        return driver.findElements(items).size();
    }

    public List<String> itemNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
        return driver.findElements(itemNames).stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }
}
