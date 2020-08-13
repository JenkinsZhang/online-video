package com.jenkins.server.model;


public class RoleResourceModel {

    /**
    * id
    */
    private String id;

    /**
    * role|id
    */
    private String roleId;

    /**
    * resouce|id
    */
    private String resourceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
            sb.append(", id=").append(id);
            sb.append(", roleId=").append(roleId);
            sb.append(", resourceId=").append(resourceId);
        sb.append("]");
        return sb.toString();
    }

}