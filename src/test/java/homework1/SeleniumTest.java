package homework1;

import homework1.enums.ProductCategories;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeleniumTest {
    private WebDriver driver;
    Actions action;

    private final By productsLocator = By.xpath("//div[@id='products']//article");
    private final By actualPriceLocator = By.xpath("//span[@itemprop=\"price\"]/span[1]");
    private final By actualNameLocator = By.xpath("//span[@itemprop='brand']/following-sibling::span");
    private final By addToBagButton = By.xpath("//button[contains(text(), 'Add to Shopping Bag')]");
    private final By removeButton = By.xpath("//button[@aria-label=\"Remove Item\"]");
    private final By closeButton = By.xpath("//button[@aria-label=\"Close\"]");
    private final By cartIcon = By.xpath("//a[@href=\"/cart\"]");
    private final By emptyCartMessage = By.xpath("//p[contains(text(),\"Nothing to see here yet!\")]");
    private final By expectedNameLocator = By.xpath(".//dt[text()='Product Name']/following-sibling::dd[1]");
    private final By expectedPriceLocator = By.xpath(".//dt[text()='Price']/following-sibling::dd/span[1]");
    private final By clothingTab = By.xpath("//a[contains(@href , '/c/clothing')]");
    private final By T_shirtSelector = By.xpath("(//a[contains(text(), 'T-Shirts')])[1]");

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.6pm.com/");
        driver.manage().window().maximize();
    }

    @AfterAll
    public void quiteDriver() {
        driver.quit();
    }

    public void hoverTab(ProductCategories tabName) throws InterruptedException {
        By tabLocator = By.xpath("//a[contains(text(), '" + tabName.getXpathValue() + "')]");
        WebElement tabElement = driver.findElement(tabLocator);
        new Actions(driver).moveToElement(tabElement).perform();
        Thread.sleep(3000);
    }

    public void selectProductByName(String productName) throws InterruptedException {
        By optionLocator = By.xpath("//a[contains(text(), '" + productName + "')]");

        driver.findElement(optionLocator).click();
        Thread.sleep(5000);
    }


    public List<SingleProduct> getAllItemsInCategory() {
        List<SingleProduct> productList = new ArrayList<>();
        List<WebElement> products = driver.findElements(productsLocator);

        for (WebElement product : products) {
            String name = product.findElement(By.xpath(".//dt[text()='Product Name']/following-sibling::dd[1]")).getText();
            String price = product.findElement(By.xpath(".//dt[text()='Price']/following-sibling::dd/span[1]")).getText();
            productList.add(new SingleProduct(name, price));
        }
        return productList;
    }

    public void selectAsideTab(String asideBarOption, String category) throws InterruptedException {
        WebElement asideOption = driver.findElement(By.xpath("//button[@data-test-id-facet-head-name='" + asideBarOption + "']"));
        Thread.sleep(3000);
        asideOption.click();
        Thread.sleep(5000);
        WebElement brandElement = driver.findElement(By.xpath("//aside[@id='searchFilters']//a//span[text()='" + category + "']"));
        Thread.sleep(5000);
        brandElement.click();
    }

    @Test
    @Tag("FirstTest" )
    public void testProductSelectionAndCart() throws Exception {

        hoverTab(ProductCategories.BAGS);
        selectProductByName("Luggage");
        List<SingleProduct> allProducts = getAllItemsInCategory();
        allProducts.forEach(product -> System.out.println(product.name + "  " + product.price));
        List<WebElement> products = driver.findElements(productsLocator);
        Assertions.assertFalse(products.isEmpty(), "No products found!");

        Random random = new Random();
        WebElement randomProduct = products.get(random.nextInt(products.size()));

        Thread.sleep(5000);
        String expectedProductName = randomProduct.findElement(expectedNameLocator).getText();
        String expectedProductPrice = randomProduct.findElement(expectedPriceLocator).getText();
        randomProduct.click();
        Thread.sleep(5000);
        String actualProductPrice = driver.findElement(actualPriceLocator).getText();
        String actualProductName = driver.findElement(actualNameLocator).getText();

        Assertions.assertNotNull(actualProductPrice, "Product price should be visible");
        Thread.sleep(5000);
        Assertions.assertNotNull(actualProductName, "Product name should be visible");

        Assertions.assertEquals(actualProductName, expectedProductName, "Product name should match the expected name");
        Assertions.assertEquals(actualProductPrice, expectedProductPrice, "Product price should match the expected price");

        driver.findElement(addToBagButton).click();
        Thread.sleep(3000);
        WebElement removeElement = driver.findElement(removeButton);
        Thread.sleep(3000);
        removeElement.click();
        Thread.sleep(3000);
        driver.findElement(closeButton).click();
        Thread.sleep(3000);
        driver.findElement(cartIcon).click();
        Thread.sleep(3000);
        WebElement emptyBagMessage = driver.findElement(emptyCartMessage);
        Thread.sleep(3000);
        Assertions.assertNotNull(emptyBagMessage, "My Bag should be empty after removing product!");
        WebElement close = driver.findElement(closeButton);
        Thread.sleep(3000);
        close.click();
    }

    @Test
    @Tag("SecondTest")
    public void testProductSelection() throws Exception {
        action = new Actions(driver);
        action.moveToElement(driver.findElement(clothingTab)).perform();
        Thread.sleep(3000);
        WebElement t_shirt = driver.findElement(T_shirtSelector);
        Thread.sleep(3000);
        t_shirt.click();
        Thread.sleep(3000);
        selectAsideTab("Color", "Brown");
        Thread.sleep(3000);
        String expectedCount = driver.findElement(By.xpath("//a//span[text()='Brown']/following-sibling::span")).getText().replaceAll("[^0-9.]", "");
        Thread.sleep(3000);
        String actualCount = driver.findElement(By.xpath("//span[contains(text(),\"items found\")]")).getText().replaceAll("[^0-9.]", "");
        Thread.sleep(3000);
        Assertions.assertEquals(expectedCount, actualCount, "Expected count must be equal to actual count");
        Thread.sleep(3000);
        WebElement brownRemove = driver.findElement(By.xpath("//a[@aria-label=\"Remove Brown filter\"]"));
        Thread.sleep(3000);
        brownRemove.click();
        Thread.sleep(5000);
        boolean isElementRemoved = driver.findElements(By.xpath("//ul[@id=\"searchSelectedFilters\"]//a[text()='Brown']")).isEmpty();
        Thread.sleep(5000);
        Assertions.assertTrue(isElementRemoved, "'Brown' filter should be removed after closing!");
        WebElement brownCheckbox = driver.findElement(By.xpath("//ul[contains(@aria-labelledby , 'color')]//a//span[text()='Brown']"));
        Assertions.assertFalse(brownCheckbox.isSelected(), "Brown must be Unselected");
    }
}
