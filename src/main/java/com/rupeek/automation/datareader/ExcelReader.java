package com.rupeek.automation.datareader;

import com.rupeek.automation.constants.Constants;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by nandita.dubey on 15/07/20.
 */
public class ExcelReader {

    public static FileInputStream fis;
    public static XSSFWorkbook wb;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;

    public Object[][] getExcelData(String sheettest) throws IOException {
        Object[][] data;
        FileInputStream fis = new FileInputStream(Constants.excelloc);
        wb = new XSSFWorkbook(fis);
        sheet = wb.getSheet(sheettest);
        row = sheet.getRow(0);
        boolean isCellBlank = false;
        int totalRows = sheet.getLastRowNum();
        int totalColumns = row.getLastCellNum();
        data = new Object[totalRows][totalColumns];

        for (int i = 1; i <= totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                try {
                    cell = sheet.getRow(i).getCell(j);
                } catch (NullPointerException e) {
                    isCellBlank = true;
                    data[i-1][j] = null;
                }

                if (!isCellBlank)
                    if (cell.getCellType() == CellType.STRING) {
                        data[i-1][j] =cell.getStringCellValue() ;
                        System.out.print(cell.getStringCellValue() + "--");
                    }
                    else if (cell.getCellType() == CellType.NUMERIC) {
                        data[i - 1][j] = (int) cell.getNumericCellValue();
                        System.out.print((int) cell.getNumericCellValue() + "--");
                    }
            }
        }

        return data;
    }
}
