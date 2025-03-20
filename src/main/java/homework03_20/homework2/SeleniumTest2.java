package homework03_20.homework2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

public class SeleniumTest2 {
    static WebDriver driver;
    static List<WebElement> products;

    public static void extractProductInfo(String tabName, String optionName) throws InterruptedException {

        By tabLocator = By.xpath("//a[contains(text(), '" + tabName + "')]");
        WebElement tabElement = driver.findElement(tabLocator);
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).perform();
        Thread.sleep(3000);

        By optionLocator = By.xpath("//a[contains(text(), '" + optionName + "')]");
        WebElement optionElement = driver.findElement(optionLocator);
        optionElement.click();
        Thread.sleep(5000);

        products = driver.findElements(By.xpath("//div[@id='products']/article"));
        for (WebElement product : products) {
            String name = product.findElement(By.xpath("./div[2]/a/dl/dd[2]")).getText();
            String price = product.findElement(By.xpath("./div[2]/a/dl/dd[4]/span[1]")).getText();
            System.out.println(name + " : " + price);
        }
    }

    public static void main(String[] args) throws Exception {
        driver = new ChromeDriver();
        driver.get("https://www.6pm.com/");
        driver.manage().window().maximize();
        extractProductInfo("Bags", "Luggage");
        Random random = new Random();
        WebElement randomProduct = products.get(random.nextInt(products.size()));
        randomProduct.click();
        Thread.sleep(5000);

        By priceLink = By.xpath("//span[@itemprop=\"price\"]/span[1]");
        WebElement productPrice = driver.findElement(priceLink);
        By nameLink = By.xpath("(//div[contains(@class,\"flex\")])[3]//span[2]");
        WebElement productName = driver.findElement(nameLink); // Update the class name

        System.out.println("Selected Product: " + productName + " : " + productPrice);

        WebElement addToBagButton = driver.findElement(By.xpath("//button[contains(text(), 'Add to Shopping Bag')]"));
        addToBagButton.click();
        Thread.sleep(3000);

        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(), 'Remove')]"));
        removeButton.click();
        Thread.sleep(3000);

        WebElement signIn = driver.findElement(By.xpath("(//a[contains(@href,\"/zap/preAuth/signin\")])[2]"));
        System.out.println(signIn.getAttribute("href"));
        Thread.sleep(4000);

        driver.quit();
    }
}

