package com.database.Person;

public class Admin extends AbstractPerson {

    private String password;

    public Admin() {
    }

    public Admin(String name, String password) {
        super(name);
        this.password = password;
    }

    @Override
    public void setName() {

    }

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
