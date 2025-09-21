package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
    }

    protected void waitAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.debug("Element tıklandı: " + element.toString());
        } catch (Exception e) {
            logger.error("Element tıklanamadı: " + element.toString(), e);
            throw e;
        }
    }

    protected void waitAndSendKeys(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            logger.debug("Text gönderildi: " + text + " - Element: " + element.toString());
        } catch (Exception e) {
            logger.error("Text gönderilemedi: " + text, e);
            throw e;
        }
    }

    protected String waitAndGetText(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String text = element.getText();
            logger.debug("Text alındı: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Text alınamadı: " + element.toString(), e);
            throw e;
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void scrollAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            scrollToElement(element);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.debug("Scroll edilip tıklanan element: " + element.toString());
        } catch (Exception e) {
            logger.error("Scroll edilip tıklama başarısız oldu: " + element.toString(), e);
            throw e;
        }
    }

    protected void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void clickIfVisible(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.info("Element bulundu ve tıklandı: " + element);
        } catch (TimeoutException e) {
            logger.warn("Element zamanında tıklanabilir hale gelmedi, geçiliyor: " + element);
        } catch (Exception e) {
            logger.warn("Element tıklanamadı, ama test devam ediyor: " + element, e);
        }
    }
}