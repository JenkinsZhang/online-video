package com.jenkins.server.model;



/**
 * @author JenkinsZhang
 * @date 2020/7/29
 */
public class SectionPageModel extends PageModel {

    private String courseId;
    private String chapterId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SectionPageModel{");
        sb.append("courseId='").append(courseId).append('\'');
        sb.append(", chapterId='").append(chapterId).append('\'');
        sb.append(", page=").append(page);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", total=").append(total);
        sb.append(", list=").append(list);
        sb.append('}');
        return sb.toString();
    }
}
