package com.company.entities;

import java.io.Serializable;

public class Messages implements Serializable {
    private int id;
    private String login;
    private String message;

    public Messages(int id, String login, String message) {
        this.id = id;
        this.login = login;
        this.message = message;
    }

    public Messages(String login, String message) {
        this.login = login;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TourismMessages{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
