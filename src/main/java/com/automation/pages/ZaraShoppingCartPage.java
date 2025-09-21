package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ZaraShoppingCartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ZaraShoppingCartPage.class);

    @FindBy(css = "span[class='order-totals-table-total__amount'] span[class='money-amount__main']")
    private WebElement cartItemPrice;

    @FindBy(css = ".price")
    private WebElement getCartItemPriceFallback;

    @FindBy(className = "zds-quantity-selector__increase")
    private WebElement increaseQuantityButton;

    @FindBy(className = "shop-cart-item-quantity")
    private WebElement quantityInput;

    @FindBy(css = ".quantity")
    private WebElement quantityInputFallback;

    @FindBy(className = "zds-quantity-selector__decrease")
    private WebElement removeButton;

    @FindBy(css = "div[class='zds-empty-state__title'] span")
    private WebElement emptyCartMessage;

    @FindBy(css = "button[role='button']")
    private WebElement contCard;


    public ZaraShoppingCartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getProductPriceInCart() {
        logger.info("Sepetteki ürün fiyatı alınıyor...");
        try {
            return waitAndGetText(cartItemPrice);
        } catch (Exception e) {
            logger.warn("Sepet fiyatı alınamadı, alternatif selector deneniyor...");
            try {
                return waitAndGetText(getCartItemPriceFallback);
            } catch (Exception ex) {
                return "Fiyat bilgisi alınamadı";
            }
        }
    }

    public void contCard(){
        waitAndClick(contCard);
    }

    public void increaseQuantity() throws InterruptedException {
        logger.info("Ürün adedi arttırılıyor...");
        scrollAndClick(increaseQuantityButton);
        Thread.sleep(3000);
    }

    public int getQuantity() {
        logger.info("Ürün adedi kontrol ediliyor...");
        try {
            String quantityText = quantityInput.getAttribute("value");
            return Integer.parseInt(quantityText);
        } catch (Exception e) {
            logger.warn("Quantity input'tan okunamadı, alternatif selector deneniyor...");
            try {
                String fallbackQuantity = quantityInputFallback.getAttribute("value");
                return Integer.parseInt(fallbackQuantity);
            } catch (Exception ex) {
                throw new RuntimeException("Ürün adedi alınamadı", e);
            }
        }
    }

    public void removeProduct() {
        logger.info("Ürün sepetten siliniyor...");
        scrollAndClick(removeButton);
        scrollAndClick(removeButton);

    }

    public boolean isCartEmpty() {
        logger.info("Sepet boş mu kontrol ediliyor...");
        return isElementDisplayed(emptyCartMessage);
    }
}