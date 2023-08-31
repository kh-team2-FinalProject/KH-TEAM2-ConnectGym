package com.khteam2.connectgym.common;

import lombok.Getter;

/**
 * 페이지네이션
 */
@Getter
public class Pagination {
    /**
     * 총 항목
     */
    private int totalCount;
    /**
     * 현재 페이지
     */
    private int currentPage;
    /**
     * 보여지는 항목 수
     */
    private int postPerPage;
    /**
     * 보여지는 페이지 수
     */
    private int rangeSize;
    /**
     * 전체 페이지 수
     */
    private int totalPage;
    /**
     * 이전 페이지
     */
    private int prevPage;
    /**
     * 다음 페이지
     */
    private int nextPage;
    /**
     * 이전 페이지 존재 여부
     */
    private boolean prev;
    /**
     * 다음 페이지 존재 여부
     */
    private boolean next;
    /**
     * 첫 페이지
     */
    private int startPage;
    /**
     * 마지막 페이지
     */
    private int endPage;

    public Pagination(int totalCount, int currentPage, int postPerPage, int rangeSize) {
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.postPerPage = postPerPage;
        this.rangeSize = rangeSize;
        this.calculate();
    }

    private void calculate() {
        this.totalPage = ((this.totalCount - 1) / this.postPerPage) + 1;
        this.startPage = ((this.currentPage - 1) / this.rangeSize) * this.rangeSize + 1;
        if (this.startPage < 1) {
            this.startPage = 1;
        }
        this.endPage = this.startPage + this.rangeSize - 1;
        if (this.endPage > this.totalPage) {
            this.endPage = this.totalPage;
        }

        this.prevPage = this.currentPage - 1;
        this.prev = this.prevPage >= 1;
        if (!this.prev) {
            this.prevPage = 1;
        }

        this.nextPage = this.currentPage + 1;
        this.next = this.totalPage >= this.nextPage;
        if (!this.next) {
            this.nextPage = this.totalPage;
        }
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.calculate();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.calculate();
    }

    public void setPostPerPage(int postPerPage) {
        this.postPerPage = postPerPage;
        this.calculate();
    }

    public void setRangeSize(int rangeSize) {
        this.rangeSize = rangeSize;
        this.calculate();
    }
}
