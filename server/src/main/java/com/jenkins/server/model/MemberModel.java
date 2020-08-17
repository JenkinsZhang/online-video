package com.jenkins.server.model;

        import java.util.Date;
        import com.fasterxml.jackson.annotation.JsonFormat;

public class MemberModel {

    /**
    * id
    */
    private String id;

    /**
    * phone
    */
    private String mobile;

    /**
    * password
    */
    private String password;

    /**
    * nickname
    */
    private String name;

    /**
    * avatar url
    */
    private String photo;

    /**
    * registration time
    */
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date registerTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
            sb.append(", id=").append(id);
            sb.append(", mobile=").append(mobile);
            sb.append(", password=").append(password);
            sb.append(", name=").append(name);
            sb.append(", photo=").append(photo);
            sb.append(", registerTime=").append(registerTime);
        sb.append("]");
        return sb.toString();
    }

}