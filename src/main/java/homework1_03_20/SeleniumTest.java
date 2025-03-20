package homework1_03_20;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;

public class SeleniumTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.6pm.com/");

        Thread.sleep(2000);
        By elementToHover = By.xpath("//a[contains(@href,\"/bags\") and text()='Bags']");
        WebElement bagsTab = driver.findElement(elementToHover);
        Actions actions = new Actions(driver);
        actions.moveToElement(bagsTab).perform();
        Thread.sleep(5000);
        By luggage = By.xpath("(//a[text()=\"Luggage\"])[1]");
        WebElement luggageLink = driver.findElement(luggage);
        luggageLink.click();
        Thread.sleep(7000);

        List<WebElement> products = driver.findElements(By.xpath("//div[@id=\"products\"]/article"));
        for (WebElement product : products) {
            String name = product.findElement(By.xpath("./div[2]/a/dl/dd[2]")).getText();
            String price = product.findElement(By.xpath("./div[2]/a/dl/dd[4]/span[1]")).getText();
            System.out.println(name + " : " + price);
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(products.size());
        products.get(randomIndex).click();
        Thread.sleep(3000);

        By priceLink = By.xpath("//span[@itemprop=\"price\"]/span[1]");
        WebElement productPrice = driver.findElement(priceLink);
        By nameLink = By.xpath("(//div[contains(@class,\"flex\")])[3]//span[2]");
        WebElement productName = driver.findElement(nameLink); // Update the class name
        System.out.println("Selected Product: " + productName.getText() + ", Price: " + productPrice.getText());
        Thread.sleep(4000);
        By addToCArtButton = By.xpath("//button[@id=\"add-to-cart-button\"]");
        driver.findElement(addToCArtButton).click();
        Thread.sleep(4000);
        By removeButton = By.xpath("//button[@aria-label=\"Remove Item\"]");
        driver.findElement(removeButton).click();
        Thread.sleep(4000);
        WebElement signIn = driver.findElement(By.xpath("(//a[contains(@href,\"/zap/preAuth/signin\")])[2]"));
        System.out.println(signIn.getAttribute("href"));
        Thread.sleep(4000);
        driver.quit();
    }
}


