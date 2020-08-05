package com.jenkins.server.enums;

/**
 * @author JenkinsZhang
 * @date 2020/8/4
 */
public enum FileUseEnum {
    //COURSE
    COURSE("C","Course"),
    //TEACHER
    TEACHER("T","Teacher");

    private String code;
    private String desc;

    FileUseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public static FileUseEnum getByCode(String code){
        for(FileUseEnum e: FileUseEnum.values()){
            if(code.equals(e.getCode())){
                return e;
            }
        }
        return  null;
    }

}
