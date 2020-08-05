package com.jenkins.server.model;

/**
 * @author JenkinsZhang
 * @date 2020/8/3
 * used for Course
 */
public class SortModel {

    /**
     * your sorting course ID
     */
    private String id;

    /**
     * your course sort before sorting
     */
    private int oldSort;

    /**
     * your course sort after sorting
     */
    private int newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOldSort() {
        return oldSort;
    }

    public void setOldSort(int oldSort) {
        this.oldSort = oldSort;
    }

    public int getNewSort() {
        return newSort;
    }

    public void setNewSort(int newSort) {
        this.newSort = newSort;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SortModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", oldSort=").append(oldSort);
        sb.append(", newSort=").append(newSort);
        sb.append('}');
        return sb.toString();
    }
}
