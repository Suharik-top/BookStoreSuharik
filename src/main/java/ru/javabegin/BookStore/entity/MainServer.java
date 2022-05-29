package ru.javabegin.BookStore.entity;
import java.util.List;

public class MainServer {

    private List<books> books;

    private account account;

    public List<books> getBooks() {
        return books;
    }

    public void setBooks(List<books> books) {
        this.books = books;
    }

    public int check(int id) {
        int num = -1;
        for(var book:books) {
            num++;
            if (book.getId() == id) return num;
        }
        return -1;
    }

    public account getAccount() {
        return account;
    }

    public void setAccount(account account) {
        this.account = account;
    }
}
