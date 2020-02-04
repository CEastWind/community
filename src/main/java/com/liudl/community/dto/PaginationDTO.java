package com.liudl.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TwistedFate on 2020/1/24 13:06
 */
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    private Integer page;//前端页面当前页
    private List<Integer> pages = new ArrayList<>();//前端页面导航条包含的页码
    private Integer totalPage;

    public void setPagination(Integer totalcount, Integer page, Integer size) {

        if (totalcount % size == 0) {
            totalPage = totalcount / size;
        }else {
            totalPage = totalcount / size + 1;
        }
        //page值不在范围内的处理
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        this.page = page;
        //页面导航条展示的页码
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        }else {
            showNext = true;
        }
        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否展示最后一页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
