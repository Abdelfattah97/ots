package com.github.ots.excel.exception;

public class ExcelDataTypeMismatchException extends RuntimeException{
    public ExcelDataTypeMismatchException(String cellDataTypeNotMatched, Exception e) {
        super(cellDataTypeNotMatched, e);
    }
}
