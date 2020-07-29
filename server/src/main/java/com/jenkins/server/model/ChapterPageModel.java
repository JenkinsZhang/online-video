package com.jenkins.server.model;

/**
 * @author JenkinsZhang
 * @date 2020/7/28
 */
public class ChapterPageModel extends PageModel {
    private String courseId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChapterPageModel{");
        sb.append("courseId='").append(courseId).append('\'');
        sb.append(", page=").append(page);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
