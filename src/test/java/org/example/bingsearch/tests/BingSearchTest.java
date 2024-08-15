package org.example.bingsearch.tests;

import org.example.bingsearch.pages.MainPage;
import org.example.bingsearch.pages.ResultsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BingSearchTest {
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
    public void searchResultsTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.sendText(input);

        ResultsPage rp = new ResultsPage(driver);
        rp.clickElement(0);

        assertEquals("https://www.selenium.dev/", driver.getCurrentUrl(), "Открылсья не верная ссылка");

    }

    @Test
    public void SearchFieldTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        mp.sendText(input);
        ResultsPage rp = new ResultsPage(driver);
        assertEquals(input, rp.gerTextFromSearchField(), "Текст не совпал");
    }
}