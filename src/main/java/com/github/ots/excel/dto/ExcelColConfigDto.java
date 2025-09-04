package com.github.ots.excel.dto;

import com.github.ots.excel.util.ExcelUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExcelColConfigDto {
//    /**
//     * the type that the cell should be converted to
//     */
//    private Class<?> cellType;
    /**
     * Col Number in letters from the excel sheet as (A,AA,AAZ,etc...)
     */
    private String colAddress;
    /**
     * Header Name of the col or alias for this col as (employeeName,Age,etc...)
     */
    private String colAlias;

    private Class<?> colType;

    private String ListDelimiter;

    public int getColIndex(){
        return ExcelUtil.colToIndex(colAddress);
    }

}
