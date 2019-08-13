package com.springmvc.domain;

import java.io.Serializable;

/**
 * @author 13967
 * @date 2019/8/13 11:49
 */
public class Employee implements Serializable {

    private String employeeName;
    private Account account;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", account=" + account +
                '}';
    }
}
