package org.example.bingsearch.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "#sb_form_q")
    private WebElement searchField;

    @FindBy(css = "h2 > a[href]")
    private List<WebElement> results;

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void clickElement(int num) {
        wait.until(ExpectedConditions.elementToBeClickable(results.get(num)));
        results.get(num).click();
        System.out.println("Нажатие на результат под номером " + num);
    }

    public String getTextFromSearchField() {
        wait.until(ExpectedConditions.visibilityOf(searchField));
        String val = searchField.getAttribute("value");
        System.out.println("В строке поиска текст: " + val);
        return val;
    }

    public boolean hasResults() {
        wait.until(ExpectedConditions.visibilityOfAllElements(results));
        boolean hasResults = !results.isEmpty();
        System.out.println("Результаты поиска найдены: " + hasResults);
        return hasResults;
    }

    public void waitForResultWithText(String text) {
        String ccsSelector = "h2 > a[href]";
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(ccsSelector), text),
                ExpectedConditions.elementToBeClickable(By.cssSelector(ccsSelector))
        ));
        System.out.println("Ожидание завершено: элемент с текстом '" + text + "' найден элемент с текстом");
    }

    public void goToSearchPageIfExists() {
        String secondPageSelector = "a.sb_pagN";
        List<WebElement> paginationLinks = driver.findElements(By.cssSelector(secondPageSelector));
        if (!paginationLinks.isEmpty()) {
            paginationLinks.get(0).click();
            System.out.println("Переход на вторую вкладку браузера");
        } else {
            System.out.println("Вторая вкладка результата нет");
        }
    }
}
