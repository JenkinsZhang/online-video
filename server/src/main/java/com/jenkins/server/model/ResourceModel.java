package com.jenkins.server.model;


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


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
            sb.append(", id=").append(id);
            sb.append(", name=").append(name);
            sb.append(", page=").append(page);
            sb.append(", request=").append(request);
            sb.append(", parent=").append(parent);
        sb.append("]");
        return sb.toString();
    }

}