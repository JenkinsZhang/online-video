package com.jenkins.server.model;


import java.util.HashSet;
import java.util.List;

public class LoginModel {

    /**
    * id
    */
    private String id;

    /**
    * login Name
    */
    private String loginName;

    /**
    * name
    */
    private String name;

    /**
     * login token
     */
    private String token;

    private List<ResourceModel> resources;

    private HashSet<String> requests;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResourceModel> getResources() {
        return resources;
    }

    public void setResources(List<ResourceModel> resources) {
        this.resources = resources;
    }

    public HashSet<String> getRequests() {
        return requests;
    }

    public void setRequests(HashSet<String> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", loginName='").append(loginName).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", resources=").append(resources);
        sb.append(", requests=").append(requests);
        sb.append('}');
        return sb.toString();
    }
}