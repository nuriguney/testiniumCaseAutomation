package org.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.ZaraHomePage;
import com.automation.pages.ZaraLoginPage;
import com.automation.pages.ZaraProductPage;
import com.automation.pages.ZaraShoppingCartPage;
import com.automation.utils.ExcelReader;
import com.automation.utils.FileWriterUtil;
import org.junit.Test;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

public class ZaraWebTest extends BaseTest {

    private ZaraHomePage homePage;
    private ZaraProductPage productPage;
    private ZaraShoppingCartPage cartPage;
    private ZaraLoginPage loginPage;
    private ExcelReader excelReader;

    private static final String EXCEL_FILE_PATH = "src/test/resources/test-data.xlsx";
    private static final String SHEET_NAME = "SearchTerms";
    private static final String OUTPUT_FILE = "product-info.txt";

    @Before
    public void setUpPages() {
        homePage = new ZaraHomePage(driver);
        productPage = new ZaraProductPage(driver);
        cartPage = new ZaraShoppingCartPage(driver);
        loginPage = new ZaraLoginPage(driver);
        excelReader = new ExcelReader(EXCEL_FILE_PATH, SHEET_NAME);
        FileWriterUtil.createNewFile(OUTPUT_FILE);
    }

    @Test
    public void testZaraEcommerceFlow() {
        try {
            //Zara sitesini aç
            homePage.navigateToZara();
            logger.info("Zara sitesi açıldı");
            //Çerezleri temizle
            homePage.rejectCookie();
            //Login ol
            homePage.clickLoginLink();
            loginPage.succesLogin();
            Thread.sleep(10000);
            //Menü -> Erkek -> Tümünü Gör
            homePage.clickMenu();
            Thread.sleep(2000);
            homePage.clickMenMenu();
            Thread.sleep(2000);
            homePage.clickViewAllButton();
            logger.info("Erkek kategorisine gidildi");
            Thread.sleep(10000);
            //Excel'den verileri oku "şort" kelimesini al ve ara
            String searchTerm1 = excelReader.getCellData(0, 0);
            String searchTerm2 = excelReader.getCellData(1, 0);
            logger.info("İlk arama terimi: " + searchTerm1);
            logger.info("İkinci arama terimi: " + searchTerm2);
            homePage.clickSearchButton();
            Thread.sleep(3000);
            homePage.searchProduct(searchTerm1);
            homePage.pressEnter();
            //Arama kutusunu temizle
            Thread.sleep(3000);
            homePage.clearSearchInput2();
            logger.info("Arama kutusu temizlendi");
            Thread.sleep(3000);
            //Excel'den "gömlek" kelimesini ara
            homePage.searchProduct2(searchTerm2);
            logger.info("İkinci arama terimi: " + searchTerm2);
            //Enter tuşuna bas
            Thread.sleep(3000);
            homePage.pressEnter2();
            Thread.sleep(3000);
            //Rastgele bir ürün seç
            productPage.selectRandomProduct();
            logger.info("Rastgele ürün seçildi");
            //Ürün bilgisi ve fiyatını al ve dosyaya yaz
            String productInfo = productPage.getProductInfo();
            String productPrice = productPage.getProductPrice();
            FileWriterUtil.writeProductInfoToFile(productInfo, productPrice, OUTPUT_FILE);
            logger.info("Ürün bilgileri dosyaya yazıldı");
            //Ürünü sepete ekle
            productPage.addToCart();
            logger.info("Ürün sepete eklendi");
            //Sepete git
            homePage.clickShoppingBag();
            Thread.sleep(2000);
            //Fiyat kontrolü
            String cartPrice = cartPage.getProductPriceInCart();
            assertEquals(productPrice, cartPrice, "Ürün sayfası ve sepetteki fiyatlar eşleşmiyor!");
            logger.info("Fiyat kontrolü başarılı");
            //Ürün adedini arttır ve doğrula
            cartPage.increaseQuantity();
            int currentQuantity = cartPage.getQuantity();
            assertEquals(2, currentQuantity, "Ürün adedi 2 olarak güncellenemedi!");
            logger.info("Ürün adedi 2'ye çıkarıldı");
            //Ürünü sepetten sil ve septin boş olduğunu kontrol et
            cartPage.removeProduct();
            boolean isCartEmpty = cartPage.isCartEmpty();
            assertTrue(isCartEmpty, "Sepet boş değil!");
            logger.info("Ürün sepetten silindi, sepet boş");
            logger.info("Tüm test adımları başarıyla tamamlandı!");

        } catch (Exception e) {
            logger.error("Test sırasında hata oluştu", e);
            fail("Test başarısız: " + e.getMessage());
        } finally {
            if (excelReader != null) {
                excelReader.closeWorkbook();
            }
        }
    }
}