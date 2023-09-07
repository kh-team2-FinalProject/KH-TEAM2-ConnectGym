package com.khteam2.connectgym.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

/**
 * 페이지네이션
 */
@Getter
@ToString
public class Pagination {
    /**
     * 총 항목
     */
    private long totalCount;
    /**
     * 현재 페이지
     */
    private int currentPage;
    /**
     * 보여지는 항목 수
     */
    private int recordPerPage;
    /**
     * 보여지는 페이지 수
     */
    private int pageSize;
    /**
     * 전체 페이지 수
     */
    private int totalPage;
    /**
     * 페이지 리스트 첫 페이지
     */
    private int firstPage;
    /**
     * 페이지 리스트 마지막 페이지
     */
    private int endPage;
    /**
     * 페이지 리스트 첫 페이지 존재 여부
     */
    private boolean prev;
    /**
     * 페이지 리스트 마지막 페이지 존재 여부
     */
    private boolean next;

    public Pagination(long totalCount, int currentPage, int recordPerPage, int pageSize) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.recordPerPage = recordPerPage;
        this.pageSize = pageSize;
        this.calculate();
    }

    public Pagination(Page<?> page, int pageSize) {
        this.totalCount = page.getTotalElements();
        this.currentPage = page.getNumber() + 1;
        this.recordPerPage = page.getSize();
        this.pageSize = pageSize;
        this.calculate();
    }

    private void calculate() {
        // ArithmeticException 방지
        if (this.recordPerPage == 0) {
            this.recordPerPage = 5;
        }

        if (this.pageSize == 0) {
            this.pageSize = 3;
        }

        this.totalPage = (int) ((((this.totalCount) - 1) / this.recordPerPage) + 1);

        this.firstPage = ((this.currentPage - 1) / this.pageSize) * this.pageSize + 1;
        if (this.firstPage < 1) {
            this.firstPage = 1;
        }
        this.prev = this.firstPage > 1;

        this.endPage = this.firstPage + this.pageSize - 1;

        if (this.endPage > this.totalPage) {
            this.endPage = this.totalPage;
        }
        this.next = this.endPage < this.totalPage;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        this.calculate();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.calculate();
    }

    public void setRecordPerPage(int recordPerPage) {
        this.recordPerPage = recordPerPage;
        this.calculate();
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.calculate();
    }
}
