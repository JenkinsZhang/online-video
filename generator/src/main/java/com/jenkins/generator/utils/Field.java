package com.jenkins.generator.utils;

/**
 * @author JenkinsZhang
 * @date 2020/7/15
 */

public class Field {

    private String javaType;
    private String upperCamelName;
    private String lowerCamelName;
    private boolean nullable;
    private String comment;
    private int length;

    @Override
    public String toString() {
        return "Field{" +
                "javaType='" + javaType + '\'' +
                ", upperCamelName='" + upperCamelName + '\'' +
                ", lowerCamelName='" + lowerCamelName + '\'' +
                ", nullable=" + nullable +
                ", comment='" + comment + '\'' +
                ", length=" + length +
                '}';
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getUpperCamelName() {
        return upperCamelName;
    }

    public void setUpperCamelName(String upperCamelName) {
        this.upperCamelName = upperCamelName;
    }

    public String getLowerCamelName() {
        return lowerCamelName;
    }

    public void setLowerCamelName(String lowerCamelName) {
        this.lowerCamelName = lowerCamelName;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

}
