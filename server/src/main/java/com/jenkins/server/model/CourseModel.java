package com.jenkins.server.model;

        import java.math.BigDecimal;
        import java.util.Date;
        import java.util.List;

        import com.fasterxml.jackson.annotation.JsonFormat;
        import com.jenkins.server.entity.Category;

public class CourseModel {

    /**
    * id
    */
    private String id;

    /**
    * name
    */
    private String name;

    /**
    * summary
    */
    private String summary;

    /**
    * time length| seconds
    */
    private Integer time;

    /**
    * price（yuan）
    */
    private BigDecimal price;

    /**
    * cover
    */
    private String image;

    /**
    * level|enums[CourseLevelEnum]：ONE("1", "Junior"),TWO("2", "Medium"),THREE("3", "Senior")
    */
    private String level;

    /**
    * charge|enums[CourseChargeEnum]：CHARGE("C", "Charge"),FREE("F", "Free")
    */
    private String charge;

    /**
    * status|enums[CourseStatusEnum]：PUBLISH("P", "Publish"),DRAFT("D", "Draft")
    */
    private String status;

    /**
    * registration number
    */
    private Integer enroll;

    /**
    * sort
    */
    private Integer sort;

    /**
    * created time
    */
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;

    /**
    * updated time
    */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedAt;

    private List<CategoryModel> categorys;

    public List<CategoryModel> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CategoryModel> categorys) {
        this.categorys = categorys;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CourseModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", time=").append(time);
        sb.append(", price=").append(price);
        sb.append(", image='").append(image).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", charge='").append(charge).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", enroll=").append(enroll);
        sb.append(", sort=").append(sort);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", categorys=").append(categorys);
        sb.append('}');
        return sb.toString();
    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEnroll() {
        return enroll;
    }

    public void setEnroll(Integer enroll) {
        this.enroll = enroll;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


}