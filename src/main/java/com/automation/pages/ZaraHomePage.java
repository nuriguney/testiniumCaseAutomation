package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;

public class ZaraHomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ZaraHomePage.class);

    @FindBy(xpath = "//a[contains(text(),'GİRİŞ YAP')]")
    private WebElement loginLink;

    @FindBy(css="#onetrust-reject-all-handler")
    private WebElement rejectCookie;

    @FindBy(xpath = "(//span[@class='layout-categories-category-name'][normalize-space()='ERKEK'])[1]")
    private WebElement menMenu;

    @FindBy(xpath = "//span[normalize-space()='TÜMÜNÜ GÖR']")
    private WebElement viewAllButton;

    @FindBy(xpath = "//span[@class='layout-header-action-search__content']")
    private WebElement searchInputClick;

    @FindBy(css = "#search-home-form-combo-input")
    private WebElement searchInput;

    @FindBy(css = "[data-testid='header-search-button']")
    private WebElement searchButton;

    @FindBy(css = "li[class='layout-header-action layout-header-action--type-text'] span:nth-child(1)")
    private WebElement shoppingBag;

    @FindBy(css = "button[aria-label='Menüyü aç']")
    private WebElement menuHamburger;

    @FindBy(css = ".search-products-form__input-clear-icon")
    private WebElement searchInput2clear;

    @FindBy(xpath = "//input[@id='search-products-form-combo-input']")
    private WebElement searchInput2;

    @FindBy(css = "#onetrust-banner-sdk")
    private WebElement bodyCheck;

    @FindBy(css = "button[aria-label='kapat']")
    private WebElement closeButton;

    public ZaraHomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToZara() {
        logger.info("Zara websitesine gidiliyor...");
        driver.get("https://www.zara.com/tr/");
        waitForPageLoad();
    }

    public void rejectCookie(){
        logger.info("Çerezler reddediliyor...");
        waitAndClick(rejectCookie);
    }

    public void clickLoginLink() {
        logger.info("Login linki tıklanıyor...");
        waitAndClick(loginLink);
    }

    public void clickMenu(){
        logger.info("Menu açılıyor...");
        waitAndClick(menuHamburger);
    }

    public void clickMenMenu() {
        logger.info("Erkek menüsü tıklanıyor...");
        waitAndClick(menMenu);
    }

    public void clickViewAllButton() {
        logger.info("Tümünü gör butonuna tıklanıyor...");
        waitAndClick(viewAllButton);
    }

    public void clickSearchButton(){
        waitAndClick(searchInputClick);
    }

    public void searchProduct(String searchTerm) {
        logger.info("Ürün aranıyor: " + searchTerm);
        searchInput.clear();
        searchInput.sendKeys(searchTerm);
    }

    public void searchProduct2(String searchTerm) {
        logger.info("Ürün aranıyor: " + searchTerm);
        searchInput2.clear();
        searchInput2.sendKeys(searchTerm);
    }

    public void clearSearchInput() {
        logger.info("Arama kutusu temizleniyor...");
        searchInput.clear();
    }

    public void clearSearchInput2() {
        logger.info("Arama kutusu 2 temizleniyor...");
        waitAndClick(searchInput2clear);
    }

    public void pressEnter() {
        logger.info("Enter tuşuna basılıyor...");
        searchInput.sendKeys(Keys.ENTER);
    }

    public void pressEnter2() {
        logger.info("Enter tuşuna basılıyor...");
        searchInput2.sendKeys(Keys.ENTER);
    }

    public void clickShoppingBag() throws InterruptedException {
        clickIfVisible(closeButton,3);
        logger.info("Alışveriş sepeti tıklanıyor...");
        waitAndClick(shoppingBag);
    }

    private void waitForPageLoad() {
        waitForElementToBeVisible(bodyCheck);
    }
}