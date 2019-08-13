package com.springmvc.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 13967
 * @date 2019/8/13 12:05
 */
public class Bank implements Serializable {

    private String bankName;
    private List<Account> list;
    private Map<Integer, Account> map;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<Account> getList() {
        return list;
    }

    public void setList(List<Account> list) {
        this.list = list;
    }

    public Map<Integer, Account> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Account> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankName='" + bankName + '\'' +
                ", list=" + list +
                ", map=" + map +
                '}';
    }
}
