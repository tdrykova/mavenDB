package com.database.Person;

public abstract class AbstractPerson implements PersonInterface {

    private String name;

    public AbstractPerson() {
    }

    public AbstractPerson(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addGoods() {

    }

    public void deleteGoods() {

    }

   // public abstract void setName(String text);
}
