package com.jenkins.server.model;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/7/13
 */
public class PageModel<T> {

    protected int page;

    protected int pageSize;

    protected long total;

    protected List<T> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
