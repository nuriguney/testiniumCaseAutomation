package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ZaraLoginPage extends BasePage{
    private static final Logger logger = LogManager.getLogger(ZaraLoginPage.class);

    @FindBy(xpath = "//input[@id='zds-:r5:']")
    private WebElement email;

    @FindBy(xpath = "//input[@id='zds-:r8:']")
    private WebElement password;

    @FindBy(css = "button[role='button']")
    private WebElement loginButton;

    @FindBy(css = "div[class='layout-header-logo layout-header-std__logo layout-customer-base-header__logo'] a[aria-label='Ana sayfaya git'] svg path")
    private WebElement homePageHeader;

    public ZaraLoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void succesLogin(){
        logger.info("Eposta adresi yazılıyor...");
        waitAndSendKeys(email, ConfigReader.getProperty("zara.email"));
        logger.info("Password yazılıyor...");
        waitAndSendKeys(password,ConfigReader.getProperty("zara.password"));
        logger.info("Giriş yap tıklanıyor...");
        waitAndClick(loginButton);
        logger.info("Anasayfaya dönülüyor...");
        waitAndClick(homePageHeader);
    }
}
