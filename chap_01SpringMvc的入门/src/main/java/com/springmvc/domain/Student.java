package com.springmvc.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 13967
 * @date 2019/8/13 12:24
 */
public class Student implements Serializable {

    private String stuName;
    private Date stuBirthday;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Date getStuBirthday() {
        return stuBirthday;
    }

    public void setStuBirthday(Date stuBirthday) {
        this.stuBirthday = stuBirthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuName='" + stuName + '\'' +
                ", stuBirthday=" + stuBirthday +
                '}';
    }
}
