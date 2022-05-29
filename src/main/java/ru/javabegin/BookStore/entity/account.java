package ru.javabegin.BookStore.entity;
import java.util.List;

public class account {

    private int money;
    private List<books> books;

    public account(int money, List<ru.javabegin.BookStore.entity.books> books) {
        this.money = money;
        this.books = books;
    }

    public List<books> getBooks() {
        return books;
    }

    public int check(int id) {
        int num = -1;
        for(var book:books) {
            num++;
            if (book.getId() == id) return num;
        }
        return -1;
    }
    public void save(books book1) {
        books book2 = new books();
        book2.setAmount(book1.getAmount());
        book2.setAuthor(book1.getAuthor());
        book2.setId(book1.getId());
        book2.setName(book1.getName());
        book2.setPrice(book1.getPrice());
        this.books.add(book2);
    }
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}