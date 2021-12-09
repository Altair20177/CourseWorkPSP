package com.company.entities;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private int role;
    private String FIO;
    private String post;
    private String training;
    private String masterClass;
    private String situations;

    public User(){};

    public User(String login, String password, int role, String FIO, String post){
        this.login = login;
        this.password = password;
        this.role = role;
        this.FIO = FIO;
        this.post = post;
    };

    public User(int id, String login, int role, String FIO, String post) {
        this.id = id;
        this.login = login;
        this.role = role;
        this.FIO = FIO;
        this.post = post;
    }

    public User(int id, String login, String password, int role){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    };

    public User(int id, String login, String password, int role, String FIO, String post, String training, String masterClass, String situations) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.FIO = FIO;
        this.post = post;
        this.training = training;
        this.masterClass = masterClass;
        this.situations = situations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getMasterClass() {
        return masterClass;
    }

    public void setMasterClass(String masterClass) {
        this.masterClass = masterClass;
    }

    public String getSituations() {
        return situations;
    }

    public void setSituations(String situations) {
        this.situations = situations;
    }

    public String getLogin() {
        return login;
    }
    public int getRole() {
        return role;
    }
    public String getPassword() {
        return password;
    }
    public String getFIO() {
        return FIO;
    }
    public String getPost() {
        return post;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public void setFIO(String FIO) {
        this.FIO = FIO;
    }
    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return  "id: " + id +
                ", login=" + login +
                ", password=" + password +
                ", role: " + role +
                ", FIO=" + FIO +
                ", post=" + post;
    }
}
