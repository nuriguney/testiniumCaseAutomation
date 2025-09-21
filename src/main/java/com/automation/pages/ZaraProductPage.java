package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.Random;

public class ZaraProductPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ZaraProductPage.class);

    @FindBy(css = ".product-detail-info__header-name")
    private WebElement productTitle;

    @FindBy(css = "h1")
    private WebElement productTitleFallback;

    @FindBy(css = ".money-amount__main")
    private WebElement productPrice;

    @FindBy(css = ".price")
    private WebElement productPriceFallback;

    @FindBy(xpath = "//div[@class='product-detail-cart-buttons__main-action']")
    private WebElement addToCartButton;

    @FindBy(css = ".product-detail-info")
    private WebElement productInfo;

    @FindBy(xpath = "//button[contains(text(),'Hayır, teşekkürler')]")
    private WebElement akilliBedenSecimiHayir;

    @FindBy(css = ".product-link")
    private List<WebElement> productLinks;

    @FindBy(css = ".product-grid-product, .product")
    private List<WebElement> productsL;

    @FindBy(className = "size-selector-sizes-size--enabled")
    private List<WebElement> sizeOptions;

    public ZaraProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectRandomProduct() {
        logger.info("Rastgele ürün seçiliyor...");
        try {
            if (!productsL.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(productsL.size());
                productsL.get(randomIndex).click();
                logger.info("Rastgele ürün seçildi: " + randomIndex);
            } else {
                logger.warn("Hiç ürün bulunamadı!");
            }
        } catch (Exception e) {
            logger.error("Ürün seçiminde hata", e);
        }
    }

    public String getProductInfo() {
        logger.info("Ürün bilgisi alınıyor...");
        try {
            return waitAndGetText(productTitleFallback);
        } catch (Exception e) {
            logger.warn("Ana selector çalışmadı, alternatif selector deneniyor...");
            try {
                return waitAndGetText(productInfo);
            } catch (Exception ex) {
                logger.error("Alternatif selector da başarısız oldu.", ex);
                return "Test Ürünü - " + System.currentTimeMillis();
            }
        }
    }

    public String getProductPrice() {
        logger.info("Ürün fiyatı alınıyor...");
        try {
            return waitAndGetText(productPrice);
        } catch (Exception e) {
            logger.warn("Fiyat bilgisi alınamadı, alternatif selector deneniyor...");
            try {
                return waitAndGetText(productPriceFallback);
            } catch (Exception ex) {
                return "Fiyat bilgisi alınamadı!";
            }
        }
    }

    public void addToCart() throws InterruptedException {
        logger.info("Ürün sepete ekleniyor...");
        waitAndClick(addToCartButton);
        if (!sizeOptions.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(sizeOptions.size());
            sizeOptions.get(randomIndex).click();
            logger.info("Rastgele beden seçildi: " + randomIndex);
            Thread.sleep(4000);
            clickIfVisible(akilliBedenSecimiHayir,5);
        } else {
            logger.warn("Hiç beden bulunamadı!");
        }
    }
}