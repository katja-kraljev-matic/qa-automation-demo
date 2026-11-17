package com.katja.qa.tests;

import com.katja.qa.base.BaseTest;
import com.katja.qa.pages.CartPage;
import com.katja.qa.pages.InventoryPage;
import com.katja.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartTests extends BaseTest {

    private InventoryPage inventory;

    // TestNG runs the @BeforeMethod of BaseTest first, so the driver already exists here
    @BeforeMethod
    public void logIn() {
        inventory = new LoginPage(driver).open().loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "Adding one product updates the cart badge")
    public void addOneProduct_badgeShowsOne() {
        inventory.addToCart("sauce-labs-backpack");

        Assert.assertEquals(inventory.cartBadgeCount(), 1);
    }

    @Test(description = "Added products appear in the cart")
    public void addTwoProducts_cartContainsBoth() {
        inventory.addToCart("sauce-labs-backpack").addToCart("sauce-labs-bike-light");

        CartPage cart = inventory.openCart();

        Assert.assertEquals(cart.itemCount(), 2);
        Assert.assertEquals(cart.itemNames(),
                List.of("Sauce Labs Backpack", "Sauce Labs Bike Light"));
    }

    @Test(description = "Removing a product clears the cart badge")
    public void removeProduct_badgeDisappears() {
        inventory.addToCart("sauce-labs-backpack").removeFromCart("sauce-labs-backpack");

        Assert.assertEquals(inventory.cartBadgeCount(), 0);
    }
}
