package app.model.journeys;

import app.model.users.Guide;

import java.util.Date;

public class Journey {
    private int id;
    private String name;
    private int price;
    private int loggedUsers;
    private int capacity;
    private Guide author;
    private String date;
    private String description;




    // ====== GETTERS AND SETTERS =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLoggedUsers() {
        return loggedUsers;
    }

    public void setLoggedUsers(int loggedUsers) {
        this.loggedUsers = loggedUsers;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Guide getAuthor() {
        return author;
    }

    public void setAuthor(Guide author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
