package ru.javabegin.BookStore.entity;

public class Deal {
    private int id;
    private int amount;

    public Deal(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {return id;}
    public int getAmount() {
        return amount;
    }
}
