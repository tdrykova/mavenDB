package com.database.Person;

public class Admin extends AbstractPerson {

    private String password;

    @Override
    public void addGoods() {

    }

    @Override
    public void deleteGoods() {

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
