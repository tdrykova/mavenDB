package com.database.Person;

public class User extends AbstractPerson {

    private int id;
    private String phoneNumber;

    public User() {
    }

    public User(String name, String phoneNumber) {
        super(name);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
