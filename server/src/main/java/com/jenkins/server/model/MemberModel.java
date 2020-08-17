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

    private String imageCode;

    private String imageToken;


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

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getImageToken() {
        return imageToken;
    }

    public void setImageToken(String imageToken) {
        this.imageToken = imageToken;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemberModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", registerTime=").append(registerTime);
        sb.append(", imageCode='").append(imageCode).append('\'');
        sb.append(", imageToken='").append(imageToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}