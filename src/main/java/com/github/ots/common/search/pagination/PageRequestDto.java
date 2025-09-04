package com.github.ots.common.search.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Valid
public class PageRequestDto {

    @Schema(example = "0")
    private int pageNumber = 0;
    @Schema(example = "10")
    private int pageSize = 10;

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber <= 0) pageNumber = 0;
        this.pageNumber = pageNumber;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) pageSize = 10;
        this.pageSize = pageSize;
    }

    public static PageRequestDto of(Integer pageNumber, Integer pageSize) {
        var page = new PageRequestDto();
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }

    public Pageable toPageable() {
        return PageRequest.of(pageNumber, pageSize);
    }

}
