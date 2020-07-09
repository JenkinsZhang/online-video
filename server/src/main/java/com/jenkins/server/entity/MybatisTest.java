package com.jenkins.server.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author JenkinsZhang
 * @date 2020/7/8
 */

@Component
public class MybatisTest implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
