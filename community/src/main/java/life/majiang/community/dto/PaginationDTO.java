package life.majiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private Boolean showPrevious;//向前按钮
    private Boolean showFirstPage;//第一页按钮
    private Boolean showNext;//下一页按钮
    private Boolean showEndPage;//最后一页按钮
    private Integer page;//页码数
    private List<Integer> pages = new ArrayList<>();//展示的页码数列表
    private Integer totaPage;//最后一页页码

    public void setPagination(Integer totaPage, Integer page) {
        this.totaPage = totaPage;
        this.page = page;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);//头部插入
            }
            if (page + i <= totaPage) {
                pages.add(page + i);//尾部插入
            }
        }
        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totaPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示跳转第一页
        if (pages.contains(1)) {           //contains():判断pages数组是否包含1
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示跳转最后一页
        if (pages.contains(totaPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
