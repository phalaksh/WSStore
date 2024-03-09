package tests;

import org.testng.annotations.Test;
import webAutomationTest.BasePage;
import webAutomationTest.SearchPage;

public class WebstaurantTests extends BasePage {
    BasePage basePage = new BasePage();
    SearchPage searchPage;

    @Test
    public void webstaurantTest() throws InterruptedException {
        // Navigate to the Webstaurant store website
        basePage.navigateToWebstaurantUrl();

        searchPage = new SearchPage();
        // enter "Stainless work table" in search box
        searchPage.enterSearchInput();

        // Click Search button
        searchPage.clickSearchButton();

        // Validate if "table" contains in the item description
        searchPage.validateTextInItemsListDescription();

        // Select last item in the list
        searchPage.selectLastItem();

        // Click Add to Cart button
        searchPage.clickAddToCartButton();

        // Navigate to Cart page
        searchPage.clickCartIcon();

        // Remove item from the cart
        searchPage.clickEmptyCartButton();
    }
}
