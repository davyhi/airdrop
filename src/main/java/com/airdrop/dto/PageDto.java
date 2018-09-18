package com.airdrop.dto;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 对分页进行处理
 * @date 2018/7/19 17:20
 */
public class PageDto extends _RequestDto {

    private int page = 0;

    private int rows = 20;

    private String sort;

    public int getPage() {
        return page > 0 ? page - 1 : 0;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows > 2000 ? 2000 : rows;
    }

    public Sort getSort() {
        if (StringUtils.isEmpty(this.sort)) {
            return new Sort(Sort.Direction.DESC, "id");  // 默认id降序
        }
        String[] sorts = this.sort.split(",");
        return new Sort(sorts[1].toUpperCase().equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sorts[0]);
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}

