package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private static final Logger logger = LogManager.getLogger(ExcelReader.class);
    private Workbook workbook;
    private Sheet sheet;
    private final DataFormatter formatter = new DataFormatter();

    public ExcelReader(String filePath, String sheetName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet bulunamadı: " + sheetName);
            }
            logger.info("Excel dosyası başarıyla açıldı: " + filePath + " | Sheet: " + sheetName);
        } catch (IOException e) {
            logger.error("Excel dosyası açılamadı: " + filePath, e);
            throw new RuntimeException("Excel dosyası açılamadı", e);
        }
    }

    public String getCellData(int row, int column) {
        try {
            Row dataRow = sheet.getRow(row);
            if (dataRow == null) {
                logger.warn("Satır bulunamadı: " + row);
                return "";
            }

            Cell cell = dataRow.getCell(column, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell == null) {
                logger.warn("Hücre bulunamadı: Row=" + row + ", Column=" + column);
                return "";
            }

            String cellValue = formatter.formatCellValue(cell);
            logger.debug("Hücre verisi okundu: Row=" + row + ", Column=" + column + ", Value=" + cellValue);
            return cellValue.trim();

        } catch (Exception e) {
            logger.error("Hücre verisi okunamadı: Row=" + row + ", Column=" + column, e);
            return "";
        }
    }

    public int getRowCount() {
        return sheet.getLastRowNum() + 1;
    }

    public int getColumnCount() {
        if (sheet.getRow(0) != null) {
            return sheet.getRow(0).getLastCellNum();
        }
        return 0;
    }

    public void closeWorkbook() {
        try {
            if (workbook != null) {
                workbook.close();
                logger.info("Excel dosyası kapatıldı");
            }
        } catch (IOException e) {
            logger.error("Excel dosyası kapatılamadı", e);
        }
    }

}

