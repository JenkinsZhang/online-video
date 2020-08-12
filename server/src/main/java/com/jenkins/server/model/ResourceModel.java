package com.jenkins.server.model;


import java.util.List;

public class ResourceModel {

    /**
    * id
    */
    private String id;

    /**
    * name|menu or button
    */
    private String name;

    /**
    * page|route
    */
    private String page;

    /**
    * request|interface
    */
    private String request;

    /**
    * parent id
    */
    private String parent;

    private List<ResourceModel> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<ResourceModel> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceModel> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResourceModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", page='").append(page).append('\'');
        sb.append(", request='").append(request).append('\'');
        sb.append(", parent='").append(parent).append('\'');
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }

}