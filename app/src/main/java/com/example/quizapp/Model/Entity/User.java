package com.example.quizapp.Model.Entity;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User {
    public ArrayList<String> friendIDs;
    public String name;
    public String pass;
    public int prev_score;
    public int score;
    public String username;

    public User(ArrayList<String> friendIDs, String username, String pass, String name, int prev_score, int score) {
        this.friendIDs = friendIDs;
        this.username = username;
        this.pass = pass;
        this.name = name;
        this.prev_score = prev_score;
        this.score = score;
    }
    public User(@NonNull String username, String pass){
        this.friendIDs = new ArrayList<>();
        this.username = username;
        this.pass = pass;
        this.name = username.replace("@gmail.com", "");
        this.prev_score = 0;
        this.score = 0;

    }

    public User(){

    }
    public ArrayList<String> getFriendIDs() {
        return friendIDs;
    }

    public void setFriendIDs(ArrayList<String> friendIDs) {
        this.friendIDs = friendIDs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrev_score() {
        return prev_score;
    }

    public void setPrev_score(int prev_score) {
        this.prev_score = prev_score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
