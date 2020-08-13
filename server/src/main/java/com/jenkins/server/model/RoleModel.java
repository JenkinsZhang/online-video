package com.jenkins.server.model;


import java.util.List;

public class RoleModel {

    /**
    * id
    */
    private String id;

    /**
    * role
    */
    private String name;

    /**
    * describe
    */
    private String desc;

    private List<String> resources;

    private List<String> users;

    public String getId() {
        return id;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoleModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", resources=").append(resources);
        sb.append(", users=").append(users);
        sb.append('}');
        return sb.toString();
    }
}