package org.example.bingsearch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        String input = "selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        String css0fElementsNotAdvertising = ":not(.b_adurl) > cite";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(css0fElementsNotAdvertising), input),
                ExpectedConditions.elementToBeClickable(By.cssSelector(css0fElementsNotAdvertising))
        ));
        List<WebElement> results = driver.findElements(By.cssSelector(css0fElementsNotAdvertising));
        clickElement(results, 0);
        goToSecondPageIfExists(driver);
        String url = driver.getCurrentUrl();
        assertEquals("https://www.selenium.dev/", url, "Открыт сайт не про Slenium");
    }

    public void clickElement(List<WebElement> results, int num) {
        results.get(num).click();
        System.out.println("Клик по номеру 0 поиска");
    }

    public static void goToSecondPageIfExists(WebDriver driver) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() == 2) {
            driver.switchTo().window(tabs.get(1));
            System.out.println("Переход на вторую вкладку браузера");
        }
    }
}