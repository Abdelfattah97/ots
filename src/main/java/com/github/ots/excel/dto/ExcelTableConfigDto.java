package com.github.ots.excel.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class ExcelTableConfigDto implements ExcelSheetConfiguration {

    private int colStart;
    private int colEnd;
    private int rowStart;
    private int rowEnd;
    @Builder.Default
    private boolean isHeaderFirstRow = true;
    private InputStream inputStream;
    private int sheetNum;
    private List<ExcelColConfigDto> colConfigList;

    public Map<Integer, ExcelColConfigDto> getColConfigMap() {
        return this.colConfigList.stream()
                .collect(Collectors.toMap(ExcelColConfigDto::getColIndex, dto -> dto));
    }
}
