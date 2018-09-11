package com.airdrop.dto;

import org.springframework.data.domain.Page;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 响应类
 * @date 2018/7/17 14:49
 */
public class QueryDto extends _ResultDto {

    private long total = 0;

    private int pageNum = 1;

    private int pageSize = 20;

    private int pages;

    private Object data = "";

    /**
     * 设置返回结果
     *
     * @param page 分页查询返回的结果
     */
    public void setPage(Page<?> page) {
        this.setPages(page.getTotalPages());
        this.setPageNum(page.getNumber() + 1);
        this.setPageSize(page.getSize());
        this.setTotal(page.getTotalElements());
        this.setData(page.getContent());
    }

    public QueryDto(Page<?> page) {
        this.setPage(page);
    }

    /**
     * 构造方法
     */
    public QueryDto() {

    }

    /**
     * 构造方法
     */
    public QueryDto(Object data) {
        this.setData(data);
    }

    /**
     * 单独设置参数
     *
     * @param list
     * @param page
     * @param size
     */
    public void setData(Object list, int page, int size, long total) {
        this.setData(list);
        this.setPageNum(page + 1);
        this.setPageSize(size);
        this.setTotal(total);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
