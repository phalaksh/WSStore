package webAutomationTest;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchPage extends BasePage{
    String searchKeyword = "stainless work table";
    String keywordText;
    public static String textToValidate;
    int pageCount;
    WebElement searchInput;
    List<WebElement> keywordsList;
    WebElement searchButton;
    List<WebElement> itemTitles;
    WebElement lastItemTitle;
    WebElement headerDescription;
    String itemDescriptionText;
    List<WebElement> paginations;
    WebElement eachPage;
    WebElement addToCartButton;
    WebElement addedToCartMessage;
    WebElement cartCount;
    public static String cartCountText;
    WebElement cartIcon;
    WebElement emptyCartButton;
    WebElement emptyCartAlert;
    WebElement emptyCartAlertBox;
    WebElement emptyCartConfirmation;

    public void enterSearchInput() {
        searchInput = driver.findElement(By.xpath("//*[contains(@class, 'justify-between')]//*[@id='searchval']"));
        searchInput.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        searchInput.sendKeys(searchKeyword);
        extentTest.log(LogStatus.INFO, "Entered " + searchKeyword.toUpperCase() + " into search box");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void keywordSuggestions(){
        keywordsList = driver.findElements(By.xpath("//*[@id='awesomplete_list_1']//span[@class='result']"));
        if(!keywordsList.isEmpty()){
            for(WebElement keyword : keywordsList){
                keywordText = keyword.getText();
            }
            extentTest.log(LogStatus.INFO, "Keywords list contains the expected keyword "+ searchKeyword);
        }
        else{extentTest.log(LogStatus.INFO, "Keyword suggestions not displayed");}
    }

    public void clickSearchButton() throws InterruptedException {
        searchButton = driver.findElement(By.xpath("//*[contains(@class, 'justify-between')]//button[@value='Search']"));
        searchButton.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String expectedTitle = driver.getTitle();
        Assert.assertTrue(!homepageTitle.equals(expectedTitle));
        extentTest.log(LogStatus.INFO, "Titles changed after clicking the search button");
    }

    public int getItemsListSize() {
        itemTitles = driver.findElements(By.xpath("//*[@data-testid='itemDescription']"));
        int itemsCount = itemTitles.size();
        System.out.println("No.of items in the list : "+itemsCount);
        return itemsCount;
    }
    public void validateTextInItemsListDescription(){
        getItemsListSize();
        for(WebElement itemDescription : itemTitles){
            itemDescriptionText = itemDescription.getText();
            textToValidate = "Table";
        }
        if (itemDescriptionText.contains(textToValidate)) {
            extentTest.log(LogStatus.INFO, "Item description contains : " + textToValidate);
        } else {
            extentTest.log(LogStatus.INFO, "Item description doesn't contain : " + textToValidate);
        }
    }

    public void selectLastItem(){
        itemTitles = driver.findElements(By.xpath("//*[@data-testid='itemDescription']"));
        lastItemTitle = itemTitles.get(itemTitles.size()-1);
        String lastItemDescription = lastItemTitle.getText();
        extentTest.log(LogStatus.INFO, "Last item description in the list : "+lastItemDescription);

        lastItemTitle.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        extentTest.log(LogStatus.INFO, "Clicked on last item in the list : "+lastItemDescription);
        headerDescription = driver.findElement(By.id("page-header-description"));
        String headerDescriptionText = headerDescription.getText();
        Assert.assertTrue(headerDescriptionText.equals(lastItemDescription));
    }

    public String getCartCountText(){
        cartCount = driver.findElement(By.id("cartItemCountSpan"));
        cartCountText = cartCount.getText();
        return cartCountText;
    }
    public void clickAddToCartButton(){
        getCartCountText();
        addToCartButton = driver.findElement(By.xpath("//*[@id='buyButton']"));
        String attributeValue = addToCartButton.getAttribute("value").toString();
        addToCartButton.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        extentTest.log(LogStatus.INFO, "Clicked on "+attributeValue+" button");

        addedToCartMessage = driver.findElement(By.className("amount"));
        Assert.assertTrue(addedToCartMessage.isDisplayed());
        extentTest.log(LogStatus.INFO, "Message is displayed after clicking Add to Cart button : "+addedToCartMessage.getText());
    }

    public void clickCartIcon(){
        cartIcon = driver.findElement(By.xpath("//*[@href='/cart/']"));
        cartIcon.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        extentTest.log(LogStatus.INFO, "Clicked on cart icon");

        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Cart']")).isDisplayed());
        extentTest.log(LogStatus.INFO," Checkout page is displayed");
    }

    public void clickEmptyCartButton(){
        emptyCartButton = driver.findElement(By.xpath("//*[starts-with(@class, 'emptyCartButton btn')]"));
        String emptyCartBtnText = emptyCartButton.getText();
        emptyCartButton.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        extentTest.log(LogStatus.INFO, "Clicked on "+emptyCartBtnText+" button on checkout page");

        extentTest.log(LogStatus.INFO, emptyCartButton.getText()+" button is displayed on checkout page");

        emptyCartAlert = driver.findElement(By.id("empty-cart-body"));
        Assert.assertTrue(emptyCartAlert.isDisplayed());
        extentTest.log(LogStatus.INFO, emptyCartAlert.getText()+" confirmation is displayed");

        emptyCartAlertBox = driver.findElement(By.xpath("//button[text()='Empty']"));
        emptyCartAlertBox.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        extentTest.log(LogStatus.INFO, "Clicked on "+emptyCartBtnText+" button on Alert dialog box");

        emptyCartConfirmation = driver.findElement(By.xpath("//*[text()='Your cart is empty.']"));
        String emptyCartConfirmationText = emptyCartConfirmation.getText();
        Assert.assertTrue(emptyCartConfirmation.isDisplayed());
        extentTest.log(LogStatus.INFO,emptyCartConfirmationText+" confirmation is displayed on Cart page");
    }

    public int getPaginationSize() throws InterruptedException {
        paginations = driver.findElements(By.xpath("//*[@id='paging']//li"));
        pageCount = paginations.size();
        System.out.println("Number of pages : " + pageCount);
        extentTest.log(LogStatus.INFO, "Number of pages : " + pageCount);
        return pageCount;
    }

    public void clickPaginations() throws InterruptedException {
        paginations = driver.findElements(By.xpath("//*[@id='paging']//li"));
        for(int i=2; i<(paginations.size()-1); i++){
            eachPage = driver.findElement(By.xpath("//*[@id='paging']//li//a[@aria-label='page "+i+"']"));
            eachPage.click();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            System.out.println("clicked on page : " +i);
            extentTest.log(LogStatus.INFO, "clicked on page : " +i);
        }
    }
}