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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebElement searchPageField = driver.findElement(By.cssSelector("#sb_form_q"));
        assertEquals(input, searchPageField.getAttribute("value"));
        List<WebElement> results = driver.findElements(By.cssSelector("h2 > a[href]"));
        clickElement(results, 0);
        driver.getCurrentUrl();

        ///assertEquals
    }

    public void clickElement(List<WebElement> results, int num) {
        results.get(num).click();
        System.out.println("Клик по номеру 0 поиска");
        By button = By.cssSelector("#Layer_1");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(button));
        WebElement disButton = driver.findElement(button);
        assertTrue(disButton.isEnabled(), "Кнопка нет после 5 сек");
    }
}