package com.jenkins.server.model;


public class CourseContentModel {

    /**
    * course.id
    */
    private String id;

    /**
    * course content
    */
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
            sb.append(", id=").append(id);
            sb.append(", content=").append(content);
        sb.append("]");
        return sb.toString();
    }

}