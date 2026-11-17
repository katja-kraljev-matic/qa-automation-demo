package com.katja.qa.tests;

import com.katja.qa.base.BaseTest;
import com.katja.qa.pages.InventoryPage;
import com.katja.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "A valid user can log in and sees the product list")
    public void validLogin_showsProducts() {
        InventoryPage inventory = new LoginPage(driver)
                .open()
                .loginAs("standard_user", "secret_sauce");

        Assert.assertEquals(inventory.pageTitle(), "Products");
        Assert.assertEquals(inventory.productCount(), 6, "Number of products");
    }

    @DataProvider(name = "invalidLogins")
    public Object[][] invalidLogins() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"standard_user", "wrong_password", "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"}
        };
    }

    @Test(dataProvider = "invalidLogins", description = "Invalid logins show the right error message")
    public void invalidLogin_showsError(String username, String password, String expectedError) {
        String actual = new LoginPage(driver)
                .open()
                .loginExpectingError(username, password)
                .errorMessage();

        Assert.assertEquals(actual, expectedError);
    }
}
