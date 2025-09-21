package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileWriterUtil {

    private static final Logger logger = LogManager.getLogger(FileWriterUtil.class);

    public static void writeProductInfoToFile(String productInfo, String price, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            writer.write("=== ÜRÜN BİLGİSİ - " + timestamp + " ===\n");
            writer.write("Ürün Bilgisi: " + productInfo + "\n");
            writer.write("Fiyat: " + price + "\n");
            writer.write("================================\n\n");

            writer.close();
            logger.info("Ürün bilgileri dosyaya yazıldı: " + fileName);
        } catch (IOException e) {
            logger.error("Dosyaya yazma hatası: " + fileName, e);
            throw new RuntimeException("Dosyaya yazma hatası", e);
        }
    }

    public static void writeToFile(String content, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            writer.write("=== LOG - " + timestamp + " ===\n");
            writer.write(content + "\n");
            writer.write("================================\n\n");

            writer.close();
            logger.info("İçerik dosyaya yazıldı: " + fileName);
        } catch (IOException e) {
            logger.error("Dosyaya yazma hatası: " + fileName, e);
            throw new RuntimeException("Dosyaya yazma hatası", e);
        }
    }

    public static void createNewFile(String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName, false);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = LocalDateTime.now().format(formatter);

            writer.write("=== TEST RAPORU - " + timestamp + " ===\n\n");
            writer.close();
            logger.info("Yeni dosya oluşturuldu: " + fileName);
        } catch (IOException e) {
            logger.error("Dosya oluşturma hatası: " + fileName, e);
            throw new RuntimeException("Dosya oluşturma hatası", e);
        }
    }
}