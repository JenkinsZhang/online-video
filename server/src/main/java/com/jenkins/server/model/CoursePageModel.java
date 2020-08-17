package com.jenkins.server.model;

/**
 * @author JenkinsZhang
 * @date 2020/8/17
 */
public class CoursePageModel extends PageModel {
    private String status;

    private String categoryId;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoursePageModel{");
        sb.append("status='").append(status).append('\'');
        sb.append(", categoryId='").append(categoryId).append('\'');
        sb.append(", page=").append(page);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
