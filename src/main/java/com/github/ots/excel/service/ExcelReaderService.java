package com.github.ots.excel.service;

import com.github.ots.common.util.StringConverter;
import com.github.ots.excel.dto.ExcelColConfigDto;
import com.github.ots.excel.dto.ExcelTableConfigDto;
import com.github.ots.excel.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelReaderService {

    public static Map<String, ArrayList<Object>> readExcel(File file) throws IOException {

        InputStream fis = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);
        int firstRow = sheet.getFirstRowNum();
        Integer firstCell = null;
        Map<String, ArrayList<Object>> dataMap = new HashMap<>();
        Map<Integer, String> headersIndexMap = new HashMap<>();
        int colIndex;
        row:
        for (Row row : sheet) {
            if (row.getRowNum() < firstRow) {
                continue;
            }
            if (firstCell == null) {
                firstCell = (int) row.getFirstCellNum();
            }
            colIndex = 0;
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (i < firstCell) {
                    continue;
                }
                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Object cellData = ExcelUtil.getCellData(cell);
                if (row.getRowNum() == firstRow) {
                    firstCell = (int) row.getFirstCellNum();
                    ArrayList<Object> list = new ArrayList<>();
                    String header = String.valueOf(cellData);
                    dataMap.put(header, list);
                    headersIndexMap.put(colIndex, header);
                } else {
                    var colList = dataMap.get(headersIndexMap.get(colIndex));
                    colList.add(cellData);
                }
                colIndex++;
            }
        }

        fis.close();
        return dataMap;
    }

    public List<Map<String, Object>> readExcelTable(ExcelTableConfigDto excelTableConfigDto) throws IOException {
        try (InputStream fis = excelTableConfigDto.getInputStream()) {

            Workbook workbook = new HSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(excelTableConfigDto.getSheetNum());
            int firstRow = excelTableConfigDto.getRowStart();
            int firstCell = excelTableConfigDto.getColStart();
            int lastCell = excelTableConfigDto.getColEnd();
            Map<Integer, ExcelColConfigDto> colConfigMap = excelTableConfigDto.getColConfigMap().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue));
            List<Map<String, Object>> dataList = new ArrayList<>();
            int lastRow = excelTableConfigDto.getRowEnd() != 0 ? excelTableConfigDto.getRowEnd() - 1 : sheet.getLastRowNum();
            row:
            for (int j = firstRow + 1; j <= lastRow; j++) {
                Map<String, Object> rowDataMap = new HashMap<>(colConfigMap.size());
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                int colIndex = 0;
                int lastCellEffective = lastCell > 0 ? lastCell : row.getLastCellNum();
                for (int i = firstCell; i < lastCellEffective; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    colIndex = i;
                    Object cellData = ExcelUtil.getCellData(cell);
                    var colConfig = colConfigMap.get(colIndex);
                    String headerName = colConfigMap.get(colIndex).getColAlias();
                    if(colConfig.getColType()!=null && cellData instanceof String){
                        cellData = StringConverter.convert((String)cellData,colConfig.getColType(),colConfig.getListDelimiter());
                    }
                    if (headerName == null) {
                        throw new IllegalArgumentException("Header alias for column index " + colIndex + " not found!");
                    }
                    rowDataMap.put(headerName, cellData);
                }

                dataList.add(rowDataMap);
            }
            return dataList;
        }
    }

}
