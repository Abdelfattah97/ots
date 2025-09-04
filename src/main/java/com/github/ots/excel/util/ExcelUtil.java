package com.github.ots.excel.util;

import com.github.ots.excel.exception.ExcelDataTypeMismatchException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.ZoneId;

public class ExcelUtil {
    public static int colToIndex(String col) {
        if (col == null || col.isEmpty()) throw new IllegalArgumentException("Empty column");
        int n = 0;
        for (int i = 0; i < col.length(); i++) {
            char ch = Character.toUpperCase(col.charAt(i));
            if (ch < 'A' || ch > 'Z') throw new IllegalArgumentException("Invalid char: " + ch);
            n = n * 26 + (ch - 'A' + 1); // bijective base-26 digit in [1..26]
        }
        return n - 1;
    }

    public static Object getCellData(Cell cell) {
        if (cell == null) {
            return "";
        }
        try {
            DataFormatter formatter = new DataFormatter();
            Object cellData;
            cellData = switch (cell.getCellType()) {
                case NUMERIC -> {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        yield cell.getDateCellValue()
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                    } else {
                        yield formatter.formatCellValue(cell); //
                    }
                }
                case STRING, FORMULA, BOOLEAN, BLANK -> formatter.formatCellValue(cell);
                default -> null;
            };
            if (cellData instanceof String && (cellData.equals("#N/A") || ((String) cellData).isBlank())) {
                cellData = null;
            }
            return cellData;
        } catch (Exception e) {
            throw new ExcelDataTypeMismatchException("Cell data type not matched", e);
        }
    }

}
