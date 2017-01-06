package com.huanke.sshshell.bean;

import java.util.List;

public class Pager<T> {

    private int pageSize;
    private int pageNo;
    private long recordsTotal;
    private List<T> data;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public long getTotalPage() {
        return recordsTotal == 0 ? 0 : (recordsTotal - 1) / pageSize + 1;
    }

    public void setRecordsTotal(long totalCount) {
        this.recordsTotal = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Pager [pageSize=" + pageSize + ", pageNo=" + pageNo
                + ", totalCount=" + recordsTotal + ", data=" + data + "]";
    }

}
