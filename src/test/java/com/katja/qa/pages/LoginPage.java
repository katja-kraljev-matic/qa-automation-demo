package com.katja.qa.pages;

import com.katja.qa.utils.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Page Object for the login page. */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public LoginPage open() {
        driver.get(Config.baseUrl());
        return this;
    }

    /** Logs in and returns the next page (use when you expect success). */
    public InventoryPage loginAs(String username, String password) {
        submit(username, password);
        return new InventoryPage(driver);
    }

    /** Tries to log in and stays on this page (use when you expect an error). */
    public LoginPage loginExpectingError(String username, String password) {
        submit(username, password);
        return this;
    }

    public String errorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    private void submit(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}
